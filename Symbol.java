import java.util.List;

public class Symbol {
    public String name;
    private Type type;
    private Scope scope;

    public Symbol(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public Scope getScope() { return scope; }
    public void setScope(Scope scope) { this.scope = scope; }
    //getSymbols
    public List<Symbol> getSymbols() {
        return null;
    }

    public String toString() {
        return String.format("<%s:%s>", name, type);
    }
}