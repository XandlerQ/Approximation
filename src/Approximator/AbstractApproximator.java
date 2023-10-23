package Approximator;

import Function.*;

public class AbstractApproximator {
    private TargetFunction targetFunction;
    private ApproximationFunction approximationFunction;

    AbstractApproximator() {
        this.targetFunction = null;
        this.approximationFunction = null;
    }

    AbstractApproximator(int M, double beta0, double beta1, String basis) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, beta0, beta1, basis);
    }
}
