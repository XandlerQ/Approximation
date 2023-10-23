package Function;

public class BasisFunctionSine extends BasisFunction {
    @Override
    public double evaluateAt(double x) {
        return Math.sin(Math.PI * this.m * x);
    }

    @Override
    public double integrate(double a, double b) {
        return (Math.cos(Math.PI * this.m * a) - Math.cos(Math.PI * this.m * b)) / (Math.PI * this.m);
    }

    @Override
    public BasisFunctionSine getNewInstance() {
        return new BasisFunctionSine();
    }
}
