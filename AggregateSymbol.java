import java.util.HashMap;
import java.util.Map;

public class AggregateSymbol extends Symbol {
    private Map<String, Symbol> fields;

    public AggregateSymbol(String name, AggregateType type) {
        super(name, type);
        this.fields = new HashMap<>();
    }

    public void addField(String fieldName, Symbol fieldSymbol) {
        fields.put(fieldName, fieldSymbol);
    }

    public Symbol getField(String fieldName) {
        return fields.get(fieldName);
    }
}