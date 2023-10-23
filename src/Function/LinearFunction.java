package Function;

public class LinearFunction extends AbstractFunction {
    private double beta0;
    private double beta1;

    public LinearFunction(double beta0, double beta1) {
        this.beta0 = beta0;
        this.beta1 = beta1;
    }

    public double getBeta0() {
        return beta0;
    }

    public double getBeta1() {
        return beta1;
    }

    public void setBeta0(double beta0) {
        this.beta0 = beta0;
    }

    public void setBeta1(double beta1) {
        this.beta1 = beta1;
    }

    public void adjustBeta0(double value) {
        this.beta0 += value;
    }

    public void adjustBeta1(double value) {
        this.beta1 += value;
    }

    @Override
    public double evaluateAt(double x) {
        return this.beta0 + this.beta1 * x;
    }

    @Override
    public double integrate(double a, double b) {
        return (b - a) * this.beta0
                + (b * b - a * a) * this.beta1 / 2;
    }
}
