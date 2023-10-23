import Approximation.AbstractApproximation;
import Approximation.CollocationApproximation;
import Approximation.SquareErrorApproximation;
import Function.TargetFunction;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import grafica.GPlot;
import grafica.GPoint;
import grafica.GPointsArray;
import processing.core.*;

import java.awt.*;
import java.io.FileReader;

public class App extends PApplet{
    public PApplet processingRef = this;
    private GPlot plot;
    private double a = 0;
    private double b = 1;
    private double epsilon = 0.000001;
    private double[] plotXArray = new double[251];
    double[] approximationFunctionValueArray = null;
    private boolean report = false;

    AbstractApproximation approximation;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        background(0);
        frameRate(300);

        Gson gson = new Gson();
        PojoProblem problem = null;

        try {
            JsonReader reader = new JsonReader(new FileReader("problem.json"));
            problem = gson.fromJson(reader, PojoProblem.class);
        }
        catch (Exception exception) {
            System.out.println(exception);
        }

        this.plot = new GPlot(this);
        this.plot.setPos(0,0);
        this.plot.setOuterDim(1000,1000);
        this.plot.getXAxis().setAxisLabelText("X");
        this.plot.getYAxis().setAxisLabelText("Y");
        this.plot.activatePanning();
        GPointsArray targetPoints = new GPointsArray();

        TargetFunction targetFunction = new TargetFunction();
        double plotStep = (this.b - this.a) / 250;
        double[] targetFunctionValueArray = targetFunction.getValueArray(this.a, this.b, 251);
        for (int i = 0; i < 251; i++) {
            this.plotXArray[i] = a + plotStep * i;
            targetPoints.add(new GPoint((float) this.plotXArray[i], (float) targetFunctionValueArray[i]));
        }
        this.plot.addLayer("targetPoints", targetPoints);
        this.plot.getLayer("targetPoints").setLineColor(Color.BLUE.getRGB());
        this.plot.getLayer("targetPoints").setPointColor(Color.BLUE.getRGB());
        this.plot.getLayer("targetPoints").setPointSize(2);



        switch (problem.getSolverType()) {
            case "Sq" -> {
                this.approximation = new SquareErrorApproximation(problem.getM(), problem.getBasis());
                this.approximation.deriveLinearFunction(this.a, this.b);
                int iterations = ((SquareErrorApproximation) this.approximation).hookeJeeves(10, this.epsilon);
                System.out.println(iterations);
                System.out.println(this.approximation.getApproximationFunction().squareError(this.approximation.getTargetFunction()));
            }
            case "Cl" -> {
                this.approximation = new CollocationApproximation(problem.getM(), problem.getBasis(), this.a, this.b);
                this.approximation.deriveLinearFunction(this.a, this.b);
                switch (problem.getCollocationType()) {
                    case "Dot" -> ((CollocationApproximation) this.approximation).dotCollocationFillMatrices();
                    case "Area" -> ((CollocationApproximation) this.approximation).areaCollocationFillMatrices();
                    case "Galerkin" -> ((CollocationApproximation) this.approximation).galerkinFillMatrices();
                }
                ((CollocationApproximation) this.approximation).approximate();
                System.out.println(this.approximation.getApproximationFunction().squareError(this.approximation.getTargetFunction()));
            }
        }

        this.approximationFunctionValueArray = this.approximation.getApproximationFunction().getValueArray(this.a, this.b, 251);

        GPointsArray approximationPoints = new GPointsArray();

        for (int i = 0; i < 251; i++) {
            approximationPoints.add(new GPoint((float) this.plotXArray[i], (float) this.approximationFunctionValueArray[i]));
        }

        this.plot.setPoints(approximationPoints);
        this.plot.defaultDraw();
    }

    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
