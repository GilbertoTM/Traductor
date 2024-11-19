public class SimpleType implements Type {
    private String name;

    public SimpleType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}