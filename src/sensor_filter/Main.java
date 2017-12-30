package sensor_filter;

public class Main {

    public static void main(String[] args) {
        System.out.println("Simple average filter:");
        Filter f = new Filter();
        int counter = 0;
        while (counter < 30) {
            counter++;
            System.out.println(Double.toString(f.naive_avg(Filter.generate_reading())));
        }

        System.out.println(" ");

        System.out.println("Smarter filter:");
        f = new Filter();
        counter = 0;
        while (counter < 30) {
            counter++;
            System.out.println(f.smarter_avg(Filter.generate_reading()));
        }

    }
}
