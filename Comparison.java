public class Comparison extends Tuple{
    Token var1, var2, op;

    public Comparison(Token var, Token val1, int sv, int sf) {
        super(sv, sf);
        this.var1 = var;
        this.var2 = val1;
    }

    //toString
    public String toString() {
        return "Comparison(" + var1 + ", " + var2 + ")";
    }

    //execute
    public int execute(SymbolTable ts) {
        Variable var = (Variable) ts.resolve(var1.getNombre());
        float value1 = Float.parseFloat(var2.getValor());
        float value2 = Float.parseFloat(var2.getValor());

        switch (op.getValor()) {
            case "==" -> var.setValue(value1 == value2 ? 1 : 0);
            case "!=" -> var.setValue(value1 != value2 ? 1 : 0);
            case "<" -> var.setValue(value1 < value2 ? 1 : 0);
            case ">" -> var.setValue(value1 > value2 ? 1 : 0);
            case "<=" -> var.setValue(value1 <= value2 ? 1 : 0);
            case ">=" -> var.setValue(value1 >= value2 ? 1 : 0);
            default -> var.setValue(0);
        }

        return rlJump;
    }

    
}
