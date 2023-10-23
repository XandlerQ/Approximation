package Function;

public class ApproximationFunction extends AbstractFunction {
    private int M;
    private LinearFunction psi;
    private BasisFunction basisFunction;
    private double[] basisCoordinates;

    ApproximationFunction() {
        this.M = 0;
        this.psi = new LinearFunction();
        this.basisFunction = null;
        this.basisCoordinates = null;
    }

    ApproximationFunction(int M, LinearFunction psi, String basis) {
        this.M = M;
        this.psi = psi;
        switch (basis) {
            case "Poly" -> {
                this.basisFunction = new BasisFunctionPoly();
            }
            case "Sine" -> {
                this.basisFunction = new BasisFunctionSine();
            }
        }
    }

    @Override
    public double evaluateAt(double x) {
        double value = psi.evaluateAt(x);
        for (int i = 0; i < this.M; i++) {
            this.basisFunction.setM(i + 1);
            value += basisCoordinates[i] * this.basisFunction.evaluateAt(x);
        }
        return value;
    }

    @Override
    public double integrate(double a, double b) {
        double value = this.psi.integrate(a, b);
        for (int i = 0; i < this.M; i++) {
            this.basisFunction.setM(i + 1);
            value += basisCoordinates[i] * this.basisFunction.integrate(a, b);
        }
        return value;
    }
}
