package Function;

public class Integrator {
    public static double integrate(AbstractFunction function, double a, double b) {
        return function.integrate(a, b);
    }

    public static double integrateMultiplication(AbstractFunction factor1, AbstractFunction factor2, double a, double b) {
        int definition = AbstractFunction.integrationDefinition;
        double step = (b - a) / definition;
        double left = a;
        double right = a + step;
        double value = 0;
        for (int i = 0; i < definition; i++) {
            value += (right - left)
                    * (factor1.evaluateAt(left) * factor2.evaluateAt(left) + factor1.evaluateAt(right) * factor2.evaluateAt(right)) / 2;
            left += step;
            right += step;
        }

        return value;
    }

    public static double integrateDifferenceSquared(AbstractFunction factor1, AbstractFunction factor2, double a, double b) {
        int definition = AbstractFunction.integrationDefinition;
        double step = (b - a) / definition;
        double left = a;
        double right = a + step;
        double value = 0;
        for (int i = 0; i < definition; i++) {
            double f1L = factor1.evaluateAt(left);
            double f1R = factor1.evaluateAt(right);
            double f2L = factor2.evaluateAt(left);
            double f2R = factor2.evaluateAt(right);
            value += (right - left)
                    * ((f1L - f2L) * (f1L - f2L) + (f1R - f2R) * (f1R - f2R)) / 2;
            left += step;
            right += step;
        }

        return value;
    }
}
