public class PojoProblem {
    private String solverType;
    private String collocationType;
    private String basis;
    private int M;

    public PojoProblem() {
        this.solverType = "";
        this.collocationType = "";
        this.M = 0;
    }

    public String getSolverType() {
        return solverType;
    }

    public String getCollocationType() {
        return collocationType;
    }

    public String getBasis() {
        return basis;
    }

    public int getM() {
        return M;
    }

    public void setSolverType(String solverType) {
        this.solverType = solverType;
    }

    public void setCollocationType(String collocationType) {
        this.collocationType = collocationType;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public void setM(int m) {
        M = m;
    }
}
