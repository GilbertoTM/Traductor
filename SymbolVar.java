public class SymbolVar extends Symbol {
    private Object value;

    public SymbolVar(String name, Type type) {
        super(name, type);
    }

    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }
}