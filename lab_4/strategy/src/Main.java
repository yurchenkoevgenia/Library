public class Main {
    public static void main(String[] args) {
        // Вхідні дані для калькулятора.
        double a = 10.5;
        double b = 20.5;

        ContextStrategy contextStrategy = new ContextStrategy();
        String operation = "sub";

        // Вибір потрібної стратегії під час виконання програми.
        switch (operation) {
            case "add":
                contextStrategy.setStrategy(new StrategyAdd());
                break;
            case "sub":
                contextStrategy.setStrategy(new StrategySub());
                break;
            case "div":
                contextStrategy.setStrategy(new StrategyDiv());
                break;
            case "mul":
                contextStrategy.setStrategy(new StrategyMultiply());
                break;
        }

        double result = contextStrategy.execute(a, b);
        System.out.println(result);
    }
}
