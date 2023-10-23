package Function;

public class BasisFunctionPoly extends BasisFunction {
    @Override
    public double evaluateAt(double x) {
        double value = (1 - x);
        for (int i = 0; i < this.m; i++) {
            value *= x;
        }
        return value;
    }

    @Override
    public double integrate(double a, double b) {
        double value = 0;

        double powerA = 1;
        double powerB = 1;
        for (int i = 0; i < this.m + 1; i++) {
            powerA *= a;
            powerB *= b;
        }

        value += powerB / (this.m + 1) - powerA / (this.m + 1);

        powerA *= a;
        powerB *= b;
        value += powerA / (this.m + 2) - powerB / (this.m + 2);

        return value;
    }

    @Override
    public BasisFunctionPoly getNewInstance() {
        return new BasisFunctionPoly();
    }
}
