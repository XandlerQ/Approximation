package Approximation;

import Function.TargetFunction;
import Function.ApproximationFunction;
import Function.LinearFunction;

public class AbstractApproximation {
    protected TargetFunction targetFunction;
    protected ApproximationFunction approximationFunction;

    public AbstractApproximation() {
        this.targetFunction = null;
        this.approximationFunction = null;
    }

    public ApproximationFunction getApproximationFunction() {
        return approximationFunction;
    }

    public TargetFunction getTargetFunction() {
        return targetFunction;
    }

    public AbstractApproximation(int M, String basis) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, basis);
    }

    public AbstractApproximation(int M, LinearFunction linearFunction, String basis) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, linearFunction, basis);
    }

    public AbstractApproximation(int M, double beta0, double beta1, String basis) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, beta0, beta1, basis);
    }

    public void deriveLinearFunction(double a, double b) {
        this.approximationFunction.setPsi(this.targetFunction.getLinearApproximation(a, b));
    }
}
