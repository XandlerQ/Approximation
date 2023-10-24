package Function;

public class ApproximationFunction extends AbstractFunction {
    private int M;
    private LinearFunction psi;
    private BasisFunction basisFunction;
    private double[] basisCoordinates;

    public ApproximationFunction() {
        this.M = 0;
        this.psi = new LinearFunction(0, 0);
        this.basisFunction = null;
        this.basisCoordinates = null;
    }

    public ApproximationFunction(int M, String basis) {
        this.M = M;
        this.psi = new LinearFunction();
        switch (basis) {
            case "Poly" -> {
                this.basisFunction = new BasisFunctionPoly();
            }
            case "Sine" -> {
                this.basisFunction = new BasisFunctionSine();
            }
        }
        this.basisCoordinates = new double[M];
    }

    public ApproximationFunction(int M, LinearFunction psi, String basis) {
        this(M, basis);
        this.psi = psi;
    }

    public ApproximationFunction(int M, double beta0, double beta1, String basis) {
        this(M, new LinearFunction(beta0, beta1), basis);
    }

    public int getM() {
        return M;
    }

    public LinearFunction getPsi() {
        return psi;
    }

    public void setPsi(LinearFunction psi) {
        this.psi = psi;
    }

    public BasisFunction getBasisFunction() {
        return basisFunction;
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
        return this.M;
    }

    public double[] getParameters() {
        double[] parameters = new double[this.M];
        for (int m = 0; m < this.M; m++) {
            parameters[m] = this.basisCoordinates[m];
        }
        return parameters;
    }

    public double getParameter(int i) {
        return this.basisCoordinates[i];
    }

    public void setParameters(double[] parameters) {
        if (this.M >= 0) System.arraycopy(parameters, 0, this.basisCoordinates, 0, this.M);
    }

    public void adjustParameter(int i, double value) {
        this.basisCoordinates[i] += value;
    }

    public void setParameter(int i, double value) {
        this.basisCoordinates[i] = value;
    }

    public double squareError(AbstractFunction targetFunction) {
        return Integrate.distance(this, targetFunction, 0, 1);
    }
}
