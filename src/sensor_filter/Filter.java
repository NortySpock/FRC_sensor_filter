package sensor_filter;

import java.util.Arrays;
import java.util.Random;

public class Filter {
    private static Random random = new Random(); // lets us make random numbers

    private static final int buffer_size = 10;

    // private variables for the naive rolling average
    private static int naive_counter;
    private double naive_reading[] = new double[buffer_size];
    private boolean naive_avg_10_ready = false;

    // private variables for the filtered rolling average
    private boolean smarter_avg_ready = false;
    private static int smarter_counter;

    private double smarter_sorted_reading[] = new double[buffer_size];
    private double smarter_current_reading[] = new double[buffer_size];
    private static final int smarter_spacer = 2; // this indicates the number of
                                                 // values on
    // each end to drop from the average

    // generates a random "reading from the sensor". Assume this is a distance
    // ultrasonic sensor that is 10 inches from the target.
    public static double generate_reading() {
        // store a collection of possible distance readings. Notice that that
        // the values generally range from 9.0 to 11.0
        // The actual average is 10.0, with outliers of 0 and 20.
        // So we expect to see averages around 10,
        // and a rolling average reading of less than 9 or greater than 11
        // indicates our average is being heavily skewed by an outlier.
        double possible_readings[] = { 0.0, 9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7,
                9.8, 9.9, 10.0, 10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7, 10.8,
                10.9, 20.0 };
        // spits out a random reading from the possible readings above
        return possible_readings[random.nextInt(20)];
    }

    public double naive_avg(double input) {
        // load a value into the buffer
        naive_reading[naive_counter] = input;
        naive_counter = naive_counter + 1;

        // First we must fill the buffer and mark it as ready.
        // Once the buffer is full, we're ready to return valid values.
        if (naive_counter >= naive_reading.length) {
            naive_counter = 0;
            naive_avg_10_ready = true;
        }

        // Return an invalid distance if we're not ready yet.
        if (!naive_avg_10_ready) {
            return -1.0;
        } else {
            // get the average value in the buffer.
            double sum = 0.0;
            for (int i = 0; i < naive_reading.length; i = i + 1) {
                sum = sum + naive_reading[i];
            }
            return sum / naive_reading.length;
        }

    }

    public double smarter_avg(double input) {
        // load a value into the buffer
        smarter_current_reading[smarter_counter] = input;
        smarter_counter++;

        // First we must fill the buffer and mark it as ready.
        // Once the buffer is full, we're ready to return valid values.
        if (smarter_counter >= smarter_current_reading.length) {
            smarter_counter = 0;
            smarter_avg_ready = true;
        }

        // Return an invalid distance if we're not ready yet.
        if (!smarter_avg_ready) {
            return -1.0;
        } else {
            // Sort the buffer with an ordering
            smarter_sorted_reading = smarter_current_reading;
            Arrays.sort(smarter_sorted_reading);

            double sum = 0.0;
            // here, we're dropping the lowest and highest n values (where n is
            // the value of smarter_spacer) from the average. This will
            // generally leave outliers out of the average, leading to a more
            // stable average
            for (int i = smarter_spacer; i < smarter_sorted_reading.length
                    - smarter_spacer; i++) {
                sum += smarter_sorted_reading[i];
            }
            return sum / (double) (smarter_sorted_reading.length
                    - (smarter_spacer * 2));
        }
    }
}
