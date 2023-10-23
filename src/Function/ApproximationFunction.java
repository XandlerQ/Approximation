package Function;

public class ApproximationFunction extends AbstractFunction {
    private int M;
    private LinearFunction psi;
    private BasisFunction basisFunction;
    private double[] basisCoordinates;

    ApproximationFunction() {
        this.M = 0;
        this.psi = new LinearFunction(0, 0);
        this.basisFunction = null;
        this.basisCoordinates = null;
    }

    public ApproximationFunction(int M, LinearFunction psi, String basis) {
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

    public ApproximationFunction(int M, double beta0, double beta1, String basis) {
        this(M, new LinearFunction(beta0, beta1), basis);
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

    public int getParameterCount() {
        return this.M + 2;
    }

    public double[] getParameters() {
        double[] parameters = new double[this.M + 2];
        parameters[0] = this.psi.getBeta0();
        parameters[1] = this.psi.getBeta1();
        for (int m = 0; m < this.M; m++) {
            parameters[m + 2] = this.basisCoordinates[m];
        }
        return parameters;
    }

    public double getParameter(int i) {
        if (i == 0) return this.psi.getBeta0();
        else if (i == 1) return this.psi.getBeta1();
        else return this.basisCoordinates[i - 2];
    }

    public void setParameters(double[] parameters) {
        this.psi.setBeta0(parameters[0]);
        this.psi.setBeta1(parameters[1]);
        for (int m = 0; m < this.M; m++) {
            this.basisCoordinates[m] = parameters[m + 2];
        }
    }

    public void adjustParameter(int i, double value) {
        if (i == 0) this.psi.adjustBeta0(value);
        else if (i == 1) this.psi.adjustBeta1(value);
        else this.basisCoordinates[i - 2] += value;
    }

    public void setParameter(int i, double value) {
        if (i == 0) this.psi.setBeta0(value);
        else if (i == 1) this.psi.setBeta1(value);
        else this.basisCoordinates[i - 2] = value;
    }

    public double squareError(TargetFunction targetFunction) {
        return Integrator.integrateDifferenceSquared(this, targetFunction, 0, 1);
    }
}
