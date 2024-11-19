import java.util.HashMap;
import java.util.Map;

public class AggregateType implements Type {
    private String name;
    private Map<String, Type> fields;

    public AggregateType(String name) {
        this.name = name;
        this.fields = new HashMap<>();
    }

    public void addField(String fieldName, Type fieldType) {
        fields.put(fieldName, fieldType);
    }

    public Type getFieldType(String fieldName) {
        return fields.get(fieldName);
    }

    @Override
    public String getName() {
        return name;
    }
}
