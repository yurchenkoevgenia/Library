public class StrategyDiv implements Strategy {
    @Override
    public double execute(double a, double b) {
        if (b == 0) {
            return 0;
        }
        return a / b;
    }
}
