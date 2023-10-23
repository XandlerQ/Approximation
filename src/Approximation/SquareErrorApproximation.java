package Approximation;

import Function.ApproximationFunction;
import Function.TargetFunction;

public class SquareErrorApproximation extends AbstractApproximation {

    public SquareErrorApproximation(int M, String basis) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, basis);
    }

    public int hookeJeeves(double stepSize, double epsilon) {
        int iterationCount = 0;
        double currentStepSize = stepSize;
        double currentSqError = this.approximationFunction.squareError(this.targetFunction);
        double lastSqError = Double.MAX_VALUE;
        while (lastSqError - currentSqError > epsilon) {
            boolean improved = false;

            for (int i = 0; i < this.approximationFunction.getParameterCount(); i++) {
                this.approximationFunction.adjustParameter(i, currentStepSize);
                double newSqError = this.approximationFunction.squareError(this.targetFunction);
                if (newSqError > currentSqError) {
                    this.approximationFunction.adjustParameter(i, -2 * currentStepSize);
                    newSqError = this.approximationFunction.squareError(this.targetFunction);
                    if (newSqError > currentSqError) {
                        this.approximationFunction.adjustParameter(i, currentStepSize);
                    }
                    else {
                        lastSqError = currentSqError;
                        currentSqError = newSqError;
                        improved = true;
                    }
                }
                else {
                    lastSqError = currentSqError;
                    currentSqError = newSqError;
                    improved = true;
                }
            }
            if (!improved) currentStepSize /= 2;
            iterationCount++;
        }
        return iterationCount;
    }
}
