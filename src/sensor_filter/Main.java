package sensor_filter;

public class Main {

    public static void main(String[] args) {
        Filter f = new Filter();
        int counter = 0;
        while (counter < 50) {
            counter++;
            System.out.println(f.smarter_avg(Filter.generate_reading()));
        }

    }
}
