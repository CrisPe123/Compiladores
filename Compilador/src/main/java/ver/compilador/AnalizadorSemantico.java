package ver.compilador;

import java.util.*;

public class AnalizadorSemantico {

    // Lista de tokens que ya han sido generados por el analizador léxico
    public List<Token> tokens;
     // Conjunto para almacenar los nombres de las variables que han sido declaradas correctamente. EL Hashset no permite palabras repetidas.
    public Set<String> variablesDeclaradas = new HashSet<>();

     // Constructor que recibe los tokens y los guarda para su análisis.
    public AnalizadorSemantico(List<Token> tokens) {
        this.tokens = tokens;
    }
     // Método principal que realiza el análisis semántico.
    public String analizar() {
        // recorremos el tamaño del token
        for (int i = 0; i < tokens.size(); i++) {
            Token actual = tokens.get(i);

            // Declaración: int x = 5;
              // Verifica si el token actual es una palabra clave (por ejemplo "int")
            if (actual.getTipo().equals("PalabraClave") && actual.getValor().equals("int")) {
                if (i + 1 < tokens.size()) {
                    // Verifica que haya un token después de la palabra clave
                    Token siguiente = tokens.get(i + 1);
                     // Si lo que viene después es un identificador, se considera que se está declarando una variable
                    if (siguiente.getTipo().equals("Identificador")) {
                        // Se guarda el nombre de la variable como "declarada"
                        variablesDeclaradas.add(siguiente.getValor());
                    }
                }
            }

            // Uso: x = 5;
            if (actual.getTipo().equals("Identificador")) {
                String nombreVar = actual.getValor();
                // Verifica si está después de una declaración o no
                boolean esDeclaracion = (i > 0 && tokens.get(i - 1).getTipo().equals("PalabraClave"));
                 // Si no es parte de una declaración y no fue previamente declarado, se considera error semántico
                if (!esDeclaracion && !variablesDeclaradas.contains(nombreVar)) {
                    return "Error semántico: Variable '" + nombreVar + "' no declarada.";
                }
            }
        }

        return "Análisis Semántico: OK";
    }
 }