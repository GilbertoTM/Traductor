public class SyntaxException extends Exception {
    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(String message1, String message2) {
        super("Se esperaba " + message1 + " pero se encontró " + message2);
    }

}
