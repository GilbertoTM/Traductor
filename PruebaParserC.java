import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PruebaParserC {
    public static void main(String[] args) {
        try {
            // Leer archivo de pseudocódigo
            String input = new String(Files.readAllBytes(Paths.get("programa.txt")));
            
            // Crear lexer y parser
            PseudoLexer lexer = new PseudoLexer();
            lexer.analizar(input);
            
            PseudoParser parser = new PseudoParser(lexer);
            
            // Generar código C
            String cCode = parser.generateCode();
            
            // Mostrar resultado
            System.out.println("Código C generado:");
            System.out.println("=================");
            System.out.println(cCode);
            
            // Opcionalmente guardar en archivo
            Files.write(Paths.get("salida.c"), cCode.getBytes());
            
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error en traducción: " + e.getMessage());
        }
    }
}