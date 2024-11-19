public class Variable extends Symbol {
    
    private float value;

    public Variable(String name, Type type) {
        super(name, type);
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
    
}
