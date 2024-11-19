import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable extends ScopeBase {

    private final Map<String, Symbol> symbolTable = new HashMap<>();
    private final Map<String, AggregateType> aggregateTypes = new HashMap<>();
    private Scope currentEnclosingScope;
    private Scope scope;

    public SymbolTable(Scope enclosingScope, Scope scope) {
        super(enclosingScope);
        this.currentEnclosingScope = enclosingScope;
        this.scope = scope;
    }

    @Override
    public String getScopeName() {
        return "global";
    }

    @Override
    public Scope getEnclosingScope() {
        return currentEnclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        symbolTable.put(sym.getName(), sym);
        sym.setScope(this);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = symbolTable.get(name);
        if (s != null) return s;
        if (currentEnclosingScope != null) return currentEnclosingScope.resolve(name);
        return null;
    }

    public Symbol resolveInGlobalScope(String name) {
        return scope.resolve(name);
    }

    public List<Symbol> getSymbols() {
        return scope.getSymbols();
    }

    public void enterScope() {
        // Create new nested scope
        SymbolTable nestedScope = new SymbolTable(this, this.scope);
        this.currentEnclosingScope = nestedScope;
    }

    public void exitScope() {
        // Return to enclosing scope
        if (currentEnclosingScope != null && currentEnclosingScope.getEnclosingScope() != null) {
            this.currentEnclosingScope = currentEnclosingScope.getEnclosingScope();
        }
    }

    public void defineAggregateType(String typeName) {
        AggregateType type = new AggregateType(typeName);
        aggregateTypes.put(typeName, type);
    }

    public void addFieldToAggregate(String typeName, String fieldName, Type fieldType) {
        AggregateType type = aggregateTypes.get(typeName);
        if (type != null) {
            type.addField(fieldName, fieldType);
        }
    }

    public Symbol resolveField(String aggregateName, String fieldName) {
        Symbol sym = resolve(aggregateName);
        if (sym instanceof AggregateSymbol aggregateSymbol) {
            return aggregateSymbol.getField(fieldName);
        }
        return null;
    }
}
