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
}
