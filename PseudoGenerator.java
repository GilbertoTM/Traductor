import java.util.ArrayList;
import java.util.List;

class PseudoGenerator {
    private List<Token> tokens;
    private List<Tuple> tuples;
    private int currentToken;
    
    public PseudoGenerator(List<Token> tokens) {
        this.tokens = tokens;
        this.tuples = new ArrayList<>();
        this.currentToken = 0;
    }
    
    private static class Tuple {
        String operator;
        String arg1;
        String arg2;
        String result;
        
        Tuple(String operator, String arg1, String arg2, String result) {
            this.operator = operator;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.result = result;
        }
        
        @Override
        public String toString() {
            return String.format("(%s, %s, %s, %s)", operator, arg1, arg2, result);
        }
    }
    
    private Token getCurrentToken() {
        if (currentToken < tokens.size()) {
            return tokens.get(currentToken);
        }
        return null;
    }
    
    private void advance() {
        currentToken++;
    }
    
    public void generate() {
        while (currentToken < tokens.size()) {
            Token token = getCurrentToken();
            switch (token.getTipo().getNombre()) {
                case "VARIABLES":
                    generateVariableDeclarations();
                    break;
                case "LEER":
                    generateRead();
                    break;
                case "ESCRIBIR":
                    generateWrite();
                    break;
                default:
                    advance();
            }
        }
    }
    
    private void generateVariableDeclarations() {
        advance(); 
        while (getCurrentToken() != null && 
               !getCurrentToken().getTipo().getNombre().equals("INICIOPROGRAMA")) {
            if (getCurrentToken().getTipo().getNombre().equals("VARIABLE")) {
                String varName = getCurrentToken().getLexema();
                tuples.add(new Tuple("DECLARE", varName, "-", "-"));
            }
            advance();
        }
    }
    
    private void generateRead() {
        advance(); 
        if (getCurrentToken().getTipo().getNombre().equals("PARENTESISIZQ")) {
            advance();
            if (getCurrentToken().getTipo().getNombre().equals("VARIABLE")) {
                String varName = getCurrentToken().getLexema();
                tuples.add(new Tuple("READ", "-", "-", varName));
            }
            advance(); 
            advance(); 
        }
    }
    
    private void generateWrite() {
        advance(); 
        if (getCurrentToken().getTipo().getNombre().equals("PARENTESISIZQ")) {
            advance();
            String expr = parseExpression();
            tuples.add(new Tuple("WRITE", expr, "-", "-"));
            advance(); 
        }
    }
    
    private String parseExpression() {
        if (getCurrentToken().getTipo().getNombre().equals("VARIABLE") ||
            getCurrentToken().getTipo().getNombre().equals("NUMERO")) {
            return getCurrentToken().getLexema();
        }
        return "";
    }
    
    public List<Tuple> getTuples() {
        return tuples;
    }
}