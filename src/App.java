import processing.core.*;

public class App extends PApplet{
    public PApplet processingRef = this;

    public void settings() {
        size(600, 300);
    }

    public void setup() {
        background(0);
        frameRate(300);
    }

    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
