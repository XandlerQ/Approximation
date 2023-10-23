package Function;

public class TargetFunction extends AbstractFunction {
    @Override
    public double evaluateAt(double x) {
        return Math.cos((x + 1) * (x + 1) * (x + 1));
    }

    @Override
    public double integrate(double a, double b) {
        int definition = AbstractFunction.integrationDefinition;
        double step = (b - a) / definition;
        double left = a;
        double right = a + step;
        double value = 0;
        for (int i = 0; i < definition; i++) {
            value += (right - left) * (evaluateAt(left) + evaluateAt(right)) / 2;
            left += step;
            right += step;
        }

        return value;
    }

    public LinearFunction getLinearApproximation(double a, double b) {
        return new LinearFunction(evaluateAt(a) - a * (evaluateAt(b) - evaluateAt(a)) / (b - a), (evaluateAt(b) - evaluateAt(a)) / (b - a));
    }
}
