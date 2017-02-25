package sensor_filter;

import java.util.Arrays;
import java.util.Random;

public class Filter {
    private static Random random = new Random();
    private static int naive_counter;
    private double naieve_reading[] = new double[10];
    private boolean naive_avg_10_ready = false;
    private boolean smarter_avg_ready = false;
    private static int smarter_counter;
    private double smarter_sorted_reading[] = new double[10];
    private double smarter_current_reading[] = new double[10];
    private int smarter_spacer = 2;

    public static double generate_reading() {
        double possible_readings[] = { 0.0, 9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7,
                9.8, 9.9, 10.0, 10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7, 10.8,
                10.9, 20.0 };
        return possible_readings[random.nextInt(20)];
    }

    public double naive_avg_10(double input) {
        naive_counter = naive_counter + 1;
        if (naive_counter >= naieve_reading.length) {
            naive_counter = 0;
            naive_avg_10_ready = true;
        }
        naieve_reading[naive_counter] = input;
        if (!naive_avg_10_ready) {
            return -1.0;
        } else {
            double sum = 0.0;
            for (int i = 0; i < naieve_reading.length; i = i + 1) {
                sum = sum + naieve_reading[i];
            }
            return sum / naieve_reading.length;
        }

    }

    public double smarter_avg(double input) {
        smarter_counter++;
        if (smarter_counter >= smarter_current_reading.length) {
            smarter_counter = 0;
            smarter_avg_ready = true;
        }
        smarter_current_reading[smarter_counter] = input;
        if (!smarter_avg_ready) {
            return -1.0;
        } else {
            smarter_sorted_reading = smarter_current_reading;
            Arrays.sort(smarter_sorted_reading);
            double sum = 0.0;
            for (int i = smarter_spacer; i < smarter_sorted_reading.length
                    - smarter_spacer; i++) {
                sum += smarter_sorted_reading[i];
            }
            return sum / (double) (smarter_sorted_reading.length
                    - (smarter_spacer * 2));
        }
    }
}
