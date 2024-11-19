import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionSymbol extends Symbol implements Scope {
    Map<String, Symbol> parameters = new LinkedHashMap<>();
    Scope enclosingScope;

    public FunctionSymbol(String name, Type type, Scope enclosingScope) {
        super(name, type);
        this.enclosingScope = enclosingScope;
    }

    public Map<String, Symbol> getParameters() { return parameters; }

    @Override
    public String getScopeName() { return name; }

    @Override
    public Scope getEnclosingScope() { return enclosingScope; }

    @Override
    public void define(Symbol sym) { parameters.put(sym.getName(), sym); }

    @Override
    public Symbol resolve(String name) {
        Symbol s = parameters.get(name);
        if (s != null) return s;
        if (enclosingScope != null) return enclosingScope.resolve(name);
        return null;
    }
}