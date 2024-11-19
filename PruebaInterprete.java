
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PruebaInterprete {
    public static void main(String[] args) throws LexicalException {
        String entrada = leerPrograma("ruta");
        PseudoLexer lexer = new PseudoLexer();
        lexer.analizar(entrada);

        System.out.println("*** Análisis léxico ***");

        for (Token t : lexer.getTokens()) {
            System.out.println(t);
        }

        System.out.println("\n*** Análisis sintáctico ***");

        Scope scope = new Scope();
        Scope globalScope = new Scope();
        SymbolTable ts = new SymbolTable(scope, globalScope);
        PseudoGenerator generator = new PseudoGenerator(lexer.getTokens());

        PseudoParser parser = new PseudoParser();
        parser.analizar(lexer);

        System.out.println("\n*** Tabla de simbolos ***");

        for (Symbol s : ts.getSymbols()) {
            System.out.println(s);
        }

        System.out.println("\n*** Tuplas Generadas ***");

        for (Tuple t : generator.getTuples()) {
            System.out.println(t);
        }

        System.out.println("\n*** Ejecución ***");

        PseudoInterprete interpreter = new PseudoInterprete(ts);
        interpreter.interpret(new ArrayList<>(generator.getTuples()));
    }

    //leerPrograma
    private static String leerPrograma(String nombre) {
        String entrada = "";

        try {
            FileReader reader = new FileReader(nombre);
            int caracter;

            while ((caracter = reader.read()) != -1) {
                entrada += (char) caracter;
            }

            reader.close();
            return entrada;

        } catch (IOException e) {
            return "";
        }
    }
}
