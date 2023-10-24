package Function;

public abstract class AbstractFunction {
    public static int integrationDefinition = 100;

    public static int getIntegrationDefinition() {
        return integrationDefinition;
    }

    public static void setIntegrationDefinition(int integrationDefinition) {
        AbstractFunction.integrationDefinition = integrationDefinition;
    }

    public abstract double evaluateAt(double x);

    public abstract double integrate(double a, double b);

    public double[] getValueArray(double a, double b, int n) {
        double[] valueArray = new double[n];
        double step = (b - a) / (n - 1);
        double x = a;
        for (int i = 0; i < n; i++) {
            valueArray[i] = evaluateAt(x);
            x += step;
        }
        return valueArray;
    }

    public LinearFunction getLinearApproximation(double a, double b) {
        return new LinearFunction(evaluateAt(a) - a * (evaluateAt(b) - evaluateAt(a)) / (b - a), (evaluateAt(b) - evaluateAt(a)) / (b - a));
    }
}
