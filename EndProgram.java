public class EndProgram extends Tuple{
    public EndProgram() {
        super(-1, -1);
    }

    public String toString() {
        return "EndProgram()";
    }

    public int execute(SymbolTable ts) {
        return -1;
    }
}
