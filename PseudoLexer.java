import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PseudoLexer {
    private ArrayList<TipoToken> tipos = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();
    private SymbolTable symbolTable;
    private Scope scope;
    private Scope currentScope;

    public PseudoLexer() {
        scope = symbolTable;
        currentScope = symbolTable;
        scope = symbolTable;
        tipos.add(new TipoToken("NUMERO", "-?[0-9]+(\\.([0-9]+))?"));
        tipos.add(new TipoToken("CADENA", "\".*\""));
        tipos.add(new TipoToken("OPARITMETICO", "[*/+-]"));
        tipos.add(new TipoToken("OPRELACIONAL", "<=|>=|==|<|>|!="));
        tipos.add(new TipoToken("IGUAL", "="));
        tipos.add(new TipoToken("COMA", ","));
        tipos.add(new TipoToken("DOSPUNTOS", ":")); // nuevo
        tipos.add(new TipoToken("PARENTESISIZQ", "\\("));
        tipos.add(new TipoToken("PARENTESISDER", "\\)"));
        tipos.add(new TipoToken("INICIOPROGRAMA", "inicio-programa"));
        tipos.add(new TipoToken("FINPROGRAMA", "fin-programa"));
        tipos.add(new TipoToken("LEER", "leer"));
        tipos.add(new TipoToken("ESCRIBIR", "escribir"));
        tipos.add(new TipoToken("SI", "si"));
        tipos.add(new TipoToken("ENTONCES", "entonces"));
        tipos.add(new TipoToken("FINSI", "fin-si"));
        tipos.add(new TipoToken("REPITE", "repite")); // nuevo
        tipos.add(new TipoToken("FINREPITE", "fin-repite")); // nuevo
        tipos.add(new TipoToken("MIENTRAS", "mientras"));
        tipos.add(new TipoToken("FINMIENTRAS", "fin-mientras"));
        tipos.add(new TipoToken("VARIABLES", "variables")); // nuevo
        tipos.add(new TipoToken("VARIABLE", "[a-zA-Z_][a-zA-Z0-9_]*"));
        tipos.add(new TipoToken("ESPACIO", "[ \t\f\r\n]"));
        tipos.add(new TipoToken("ERROR", "[^ \t\f\n]"));
    }

    public void analizar(String entrada) throws LexicalException {
        StringBuffer er = new StringBuffer();

        for (TipoToken tipo : tipos) {
            er.append(String.format("|(?<%s>%s)", tipo.getNombre(), tipo.getPatron()));
        }

        Pattern p = Pattern.compile(er.substring(0));

        Matcher m = p.matcher(entrada);

        while (m.find()) {
            for (TipoToken tt: tipos) {

                if (m.group("ESPACIO") != null) {
                    continue;
                }
                else if (m.group(tt.getNombre()) != null) {
                    if (tt.getNombre().equals("ERROR"))
                        throw new LexicalException(m.group(tt.getNombre()));

                    String nombre = m.group(tt.getNombre());

                    if (tt.getNombre().equals("CADENA")) {
                        nombre = nombre.substring(1, nombre.length() - 1);
                    }

                    tokens.add(new Token(tt, nombre, "", "")); // tipo de token y el token. NUMERO, 4
                    break;    
                }
            }
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void insertSymbol(String name, Type type) {
        symbolTable.define(new VariableSymbol(name, type));
    }
    
    public void enterScope() {
        symbolTable.enterScope();
    }

    public void exitScope() {
        symbolTable.exitScope();
    }

    public void defineFunction(String name, Type returnType) {
        FunctionSymbol function = new FunctionSymbol(name, returnType, currentScope);
        currentScope.define(function);
        currentScope = function; // Cambiar al alcance de la función
    }

    public void defineVariable(String name, Type type) {
        VariableSymbol var = new VariableSymbol(name, type);
        currentScope.define(var);
    }

    public Symbol resolveSymbol(String name) {
        return currentScope.resolve(name);
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}

