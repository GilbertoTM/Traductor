public class Write extends Tuple{
    Token str, var;

    public Write(Token strVar, int sv, int sf) {
        super(sv, sf);
        this.str = strVar;
        
        if (strVar.getTipo().getNombre().equals(new TipoToken("arg1", "arg2").getNombre()))
            str = strVar;
        else
            var = strVar;
    }

    public Write(Token str, Token var, int sv, int sf) {
        super(sv, sf);
        this.str = str;
        this.var = var;
    }

    public String toString() {
        if (var == null)
            return "Write(" + str + ")";
        else
            return "Write(" + str + ", " + var + ")";
    }

    public int execute(SymbolTable ts) {
        if (var == null)
            System.out.println(str.getValor());
        else
            System.out.println(str.getValor() + ts.resolve(var.getNombre()));

        return rlJump;
    }

}
