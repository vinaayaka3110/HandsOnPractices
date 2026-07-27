public class FinancialForecast {
    public static double futureValue(double value, double growthRate, int years) {
        // edge case... (when the years becomes zero..)
        if (years == 0) {
            return value;
        }
        // Recursion algorithm ...
        return futureValue(value, growthRate, years - 1) * (1 + growthRate); // decrementing the years value by per
                                                                             // itteration mean per year..
    }

    public static void main(String[] args) {
        double value = 10000; // the current value
        double growthRate = 0.10; // the growth 10%
        int years = 5; // lets calculate for 5 years..
        double result = futureValue(value, growthRate, years);
        System.out.println("Present Value: Rs " + value);
        System.out.println("Growth Rate: " + (growthRate * 100) + "%");
        System.out.println("Years: " + years);
        System.out.printf("Future Value: Rs %.2f%n", result);
    }
}