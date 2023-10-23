package Function;

public abstract class BasisFunction extends AbstractFunction {
    protected int m;

    BasisFunction() {
        this.m = 0;
    }

    BasisFunction(int m) {
        this.m = m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public abstract BasisFunction getNewInstance();
}
