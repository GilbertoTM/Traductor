import java.util.List;

public class PseudoParser {
    private List<Token> tokens;
    private int indiceToken = 0;
    private SyntaxException ex;


    public void analizar(PseudoLexer lexer) throws SyntaxException {

        tokens = lexer.getTokens();

        if (Programa()) {
            if (indiceToken == tokens.size()) {
                System.out.println("La sintaxis del programa es correcta");
            } 
            else {
                ex = new SyntaxException("Error de sintaxis");
                throw ex;
            }
        }
    }

    private boolean Programa() {
        if (match("INICIOPROGRAMA")) {
            if (Enunciados()) {
                if (match("FINPROGRAMA")) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean Enunciados() {

        int indiceAux = indiceToken;

        if (Enunciado()) {
            while (Enunciado());
            return true;
        }

        indiceToken = indiceAux;
        return false;
    }

    private boolean Enunciado() {
        int indiceAux = indiceToken;

        if (tokens.get(indiceToken).getTipo().getNombre().equals("VARIABLE")) {
            if (Asignacion()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        // Agreamos soporte para variables
        if (tokens.get(indiceToken).getTipo().getNombre().equals("VARIABLES")) {
            if (Variables()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        if (tokens.get(indiceToken).getTipo().getNombre().equals("LEER")) {
            if (Leer()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        if (tokens.get(indiceToken).getTipo().getNombre().equals("ESCRIBIR")) {
            if (Escribir()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        if (tokens.get(indiceToken).getTipo().getNombre().equals("SI")) {
            if (Si()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        if (tokens.get(indiceToken).getTipo().getNombre().equals("MIENTRAS")) {
            if (Mientras()) {
                return true;
            }
        }

        indiceToken = indiceAux;

        // Agreamos soporte para repeticiones
        if (tokens.get(indiceToken).getTipo().getNombre().equals("REPITE")) {
            if (Repite()) {
                return true;
            }
        }

        indiceToken = indiceAux;
        return false;


    }

    //Asignacion -> VARIABLE = Expresion
    private boolean Asignacion() {
        int indiceAux = indiceToken;

        if (match("VARIABLE")) {
            if (match("IGUAL")) {
                if (Expresion()) {  
                    return true;
                }
            }
        }

        indiceToken = indiceAux;
        return false;
    }

    //Expresion
    private boolean Expresion() {

        int indiceAux = indiceToken;

        if (Valor()) {
            if (match("OPARITMETICO")) {
                if (Valor()) {
                    return true;
                }
            }
        }

        indiceToken = indiceAux;

        if (Valor()) {
            return true;
        }

        indiceToken = indiceAux;
        return false;
    }

    //Valor
    private boolean Valor() {
        int indiceAux = indiceToken;
        if (match("VARIABLE") || match("NUMERO")) {
            return true;
        }

        indiceToken = indiceAux;
        return false;
    }

    //Leer -> LEER VARIABLE
    private boolean Leer() {
        int indiceAux = indiceToken;

        if (match("LEER")) {
            if (match("VARIABLE")) {
                return true;
            }
        }

        indiceToken = indiceAux;
        return false;
    }

    //Escribir -> escribir CADENA , VARIABLE | escribir CADENA | escribir VARIABLE
    private boolean Escribir() {
        int indiceAux = indiceToken;

        if (match("ESCRIBIR")) {
            if (match("CADENA")) {
                if (match("COMA")) {
                    if (match("VARIABLE")) {
                        return true;
                    }
                }
            }
        }

        indiceToken = indiceAux;

        if (match("ESCRIBIR")) {
            if (match("CADENA")) {
                return true;
            }
        }

        indiceToken = indiceAux;

        if (match("ESCRIBIR")) {
            if (match("VARIABLE")) {
                return true;
            }
        }

        indiceToken = indiceAux;
        return false;
    }   
    
    //Si -> SI Comparacion ENTONCES Enunciados FINSI
    private boolean Si() {
        int indiceAux = indiceToken;

        if (match("SI")) {
            if (Comparacion()) {
                if (match("ENTONCES")) {
                    if (Enunciados()) {
                        if (match("FINSI")) {
                            return true;
                        }
                    }
                }
            }
        }

        indiceToken = indiceAux;
        return false;
    }

    //Mientras -> MIENTRAS Comparacion ENTONCES Enunciados FINMIENTRAS
    private boolean Mientras() {
        int indiceAux = indiceToken;

        if (match("MIENTRAS")) {
            if (Comparacion()) {
                if (match("ENTONCES")) {
                    if (Enunciados()) {
                        if (match("FINMIENTRAS")) {
                            return true;
                        }
                    }
                }
            }
        }

        indiceToken = indiceAux;
        return false;
    }

    //Comparacion -> Valor OPERADOR Valor
    private boolean Comparacion() {
        int indiceAux = indiceToken;

        if (match("PARANTESISIZQ")) {
            if (Valor()) {
                if (match("OPERADOR")) {
                    if (Valor()) {
                        if (match("PARENTESISDER")) {
                            return true;
                        }
                    }
                }
            }
        }

        indiceToken = indiceAux;
        return false;
    }

    // VARIABLES -> VARIABLES DOS PUNTOS VARIABLE , VARIABLE | VARIABLES DOS PUNTOS VARIABLE
    private boolean Variables() {
        int indiceAux = indiceToken;
    
        // Verificamos si empieza con 'VARIABLES' seguido de ':' y una 'VARIABLE'
        if (match("VARIABLES")) {
            if (match("DOSPUNTOS")) {
                if (match("VARIABLE")) {
                    
                    // Mientras siga habiendo comas, esperamos otra variable
                    while (match("COMA")) {
                        if (!match("VARIABLE")) {
                            // Si hay una coma, pero no otra variable, es un error
                            indiceToken = indiceAux;
                            return false;
                        }
                    }
                    
                    // Si llegamos aquí, la lista de variables es válida
                    return true;
                }
            }
        }
    
        // Si falla en cualquier punto, regresamos al estado original
        indiceToken = indiceAux;
        return false;
    }
    

    //REPETIR -> "repite" "(" VARIABLE "," NUMERO "," VARIABLE ")" { INSTRUCCIONES } "fin-repite"
    private boolean Repite() {
        int indiceAux = indiceToken;  // Guardamos el estado inicial del índice
    
        // Comprobamos si es la palabra clave 'repite'
        if (match("REPITE")) {
            // Verificamos si tiene un paréntesis izquierdo '('
            if (match("PARENTESISIZQ")) {
                // Verificamos la variable de control
                if (match("VARIABLE")) {
                    // Verificamos la primera coma ','
                    if (match("COMA")) {
                        // Verificamos el número inicial (en este caso es un NUMERO)
                        if (match("NUMERO")) {
                            // Verificamos la segunda coma ','
                            if (match("COMA")) {
                                // Verificamos la variable de límite superior
                                if (match("VARIABLE")) {
                                    // Verificamos el paréntesis derecho ')'
                                    if (match("PARENTESISDER")) {
                                        // Ahora analizamos las instrucciones dentro del ciclo
                                        if (Enunciados()) {
                                            // Verificamos si es la palabra clave 'fin-repite'
                                            if (match("FINREPITE")) {
                                                // Si todo sale bien, retornamos verdadero
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    
        // Si algo falla, retrocedemos el índice
        indiceToken = indiceAux;
        return false;
    }
    


    private boolean match(String nombre) {
        if (tokens.get(indiceToken).getTipo().getNombre().equals(nombre)) {
            System.out.println(nombre + ": " + tokens.get(indiceToken).getValor());
            indiceToken++;
            return true;
        }

        ex = new SyntaxException("Se esperaba " + nombre + " pero se encontró " + tokens.get(indiceToken).getTipo().getNombre());

        return false;
    }
}
