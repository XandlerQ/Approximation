package Approximation;

import Function.ApproximationFunction;
import Function.BasisFunction;
import Function.Integrate;
import Function.TargetFunction;
import Jama.Matrix;

public class WeightedApproximation extends AbstractApproximation {
    private Matrix collocationMatrix;
    private Matrix collocationVector;
    private double[] xArray;
    private int M;
    private double a;
    private double b;
    public WeightedApproximation(int M, String basis, double a, double b) {
        this.targetFunction = new TargetFunction();
        this.approximationFunction = new ApproximationFunction(M, basis);
        this.collocationMatrix = new Matrix(M, M);
        this.collocationVector = new Matrix(M, 1);
        this.M = M;
        this.a = a;
        this.b = b;
    }

    public void dotCollocationFillMatrices() {
        this.xArray = new double[this.M];
        if (this.xArray.length > 0) {
            double step = (this.b - this.a) / (this.M + 1);
            for (int m = 0; m < this.M; m++) {
                this.xArray[m] = this.a + step * (m + 1);
            }
        }
        BasisFunction basisFunction = this.approximationFunction.getBasisFunction();
        for (int l = 0; l < this.M; l++) {
            for (int m = 0; m < this.M; m++) {
                basisFunction.setM(m + 1);
                this.collocationMatrix.set(l, m, basisFunction.evaluateAt(this.xArray[l]));
            }
            double collocationVectorValue = this.targetFunction.evaluateAt(this.xArray[l])
                    - this.approximationFunction.getPsi().evaluateAt(this.xArray[l]);
            this.collocationVector.set(l, 0, collocationVectorValue);
        }
    }

    public void areaCollocationFillMatrices() {
        this.xArray = new double[this.M + 1];
        double step = (this.b - this.a) / this.M;
        for (int m = 0; m < this.M + 1; m++) {
            this.xArray[m] = this.a + step * m;
        }
        BasisFunction basisFunction = this.approximationFunction.getBasisFunction();
        for (int l = 0; l < this.M; l++) {
            for (int m = 0; m < this.M; m++) {
                basisFunction.setM(m + 1);
                this.collocationMatrix.set(l, m, Integrate.integrate(basisFunction, this.xArray[l], this.xArray[l + 1]));
            }
            double collocationVectorValue = 0;
            collocationVectorValue += Integrate.integrate(this.targetFunction, this.xArray[l], this.xArray[l + 1]);
            collocationVectorValue -= Integrate.integrate(this.approximationFunction.getPsi(), this.xArray[l], this.xArray[l + 1]);
            this.collocationVector.set(l, 0, collocationVectorValue);
        }
    }

    public void galerkinFillMatrices() {
        BasisFunction basisFunctionL = this.approximationFunction.getBasisFunction().getNewInstance();
        BasisFunction basisFunctionM = this.approximationFunction.getBasisFunction().getNewInstance();
        for (int l = 0; l < this.M; l++) {
            basisFunctionL.setM(l + 1);
            for (int m = 0; m < this.M; m++) {
                basisFunctionM.setM(m + 1);
                this.collocationMatrix.set(l, m, Integrate.integrateMultiplication(basisFunctionL, basisFunctionM, this.a, this.b));
            }
            double collocationVectorValue = 0;
            collocationVectorValue += Integrate.integrateMultiplication(this.targetFunction, basisFunctionL, this.a, this.b);
            collocationVectorValue -= Integrate.integrateMultiplication(this.approximationFunction.getPsi(), basisFunctionL, this.a, this.b);
            this.collocationVector.set(l, 0, collocationVectorValue);
        }
    }

    public void approximate() {
        Matrix solution = this.collocationMatrix.solve(this.collocationVector);
        double[][] solutionMatrixArray = solution.getArray();
        for (int m = 0; m < this.approximationFunction.getM(); m++) {
            this.approximationFunction.setParameter(m, solutionMatrixArray[m][0]);
        }
    }

}
