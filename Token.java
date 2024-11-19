
public class Token {
    private TipoToken tipo;
    private String valor;
    private String nombre;
    private String lexema;

    public Token(TipoToken tipo, String valor, String nombre, String lexema) {
        this.tipo = tipo;
        this.valor = valor;
        this.lexema = lexema;
        this.nombre = nombre;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public String toString() {
        return String.format("<%s, \"%s\">", tipo.getNombre(), valor);
    }

    //getNombre
    public String getNombre(){
        return nombre;
    }

    public String getLexema() {
        return lexema;
    }
}