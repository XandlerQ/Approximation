package Function;

public abstract class BasisFunction extends AbstractFunction {
    protected int m;

    BasisFunction() {
        this.m = 0;
    }

    BasisFunction(int m) {
        this.m = m;
    }

    public void setM(int m) {
        this.m = m;
    }

    @Override
    public double evaluateAt(double x) {
        return 0;
    }

    public abstract double integrate(double a, double b);
}
