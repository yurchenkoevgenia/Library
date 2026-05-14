public class ContextStrategy {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double execute(double a, double b) {
        return strategy.execute(a, b);
    }
}
