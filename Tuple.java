public abstract class Tuple {
    protected int rlJump, fkJump;

    public Tuple(int rlJump, int fkJump) {
        this.rlJump = rlJump;
        this.fkJump = fkJump;
    }

    // Getters
    public int getRlJump() { return rlJump; }
    public int getFkJump() { return fkJump; }

    // Setters
    public void setRlJump(int rlJump) { this.rlJump = rlJump; }
    public void setFkJump(int fkJump) { this.fkJump = fkJump; }

    // toString
    public String toString() {
        return "Tuple(rlJump=" + rlJump + ", fkJump=" + fkJump + ")";
    }

    public abstract int execute(SymbolTable ts);
}
