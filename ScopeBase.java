import java.util.HashMap;
import java.util.Map;

public abstract class ScopeBase implements Scope {
    protected Scope enclosingScope;
    protected Map<String, Symbol> symbols = new HashMap<>();

    public ScopeBase(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        if (enclosingScope != null) return enclosingScope.resolve(name);
        return null;
    }
}