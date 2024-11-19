public class Assig extends Tuple{
    Token var1, var2, op;

    public Assig(Token var, Token val1, int sv, int sf) {
        super(sv, sf);
        this.var1 = var;
        this.var2 = val1;
    }

    public Assig(Token var, Token val1, Token op, Token val2, int sv, int sf) {
        super(sv, sf);
        this.var1 = var;
        this.var2 = val1;
        this.op = op;
    }

    //toString
    public String toString() {
        return "Assig(" + var1 + ", " + var2 + ", " + op + ")";
    }

    //execute
    @Override
    public int execute(SymbolTable ts) {
        Variable var = (Variable) ts.resolve(var1.getNombre());
        float value1 = Float.parseFloat(var2.getValor());
        float value2 = Float.parseFloat(var2.getValor());

        switch (op.getValor()) {
            case "=" -> var.setValue(value1);
            case "+=" -> var.setValue(var.getValue() + value2);
            case "-=" -> var.setValue(var.getValue() - value2);
            case "*=" -> var.setValue(var.getValue() * value2);
            case "/=" -> var.setValue(var.getValue() / value2);
            default -> var.setValue(0);
        }

        return rlJump;
    }
}
