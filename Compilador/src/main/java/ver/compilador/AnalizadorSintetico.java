
package ver.compilador;
import java.util.List;


// esta clase se encarga de validar la estructura del codigo fuente segun una gramaticasimple

// verificamos que las sentencias sean del tipo palabraclave = identificador = expresion;
class AnalizadorSintectico {
    
     public List<Token> tokens; // listado de tokens obtenidos tras en analisis lexico
     public int indice = 0; // iniciamos en 0 para recorrer los tokens
   
 
     // constructor de la clase recibe la lista de tokens
    public AnalizadorSintectico(List<Token> tokens) {
        this.tokens = tokens;
       
    }
    // metodo principal que inicia el analisis sintactico
    public String analizar() {
        try {
            // recorremos los tokens de la lista desde 0 que contiene el indice hasta el tamaño que tienen los tokens
            while (indice < tokens.size()) {
                if (!sentencia()) {
                    return "Error de sintaxis en: " + actual();
                }
            }
            return "Análisis Sintáctico: OK";
        } catch (Exception e) {
            return "Error de sintaxis: " + e.getMessage();
        }
    }

    
    private boolean sentencia() {
        // Ejemplo int x = 5;
        
        // Verifica si comienza con una palabra clave (como int, float, etc.)
        if (match("PalabraClave")) {
            // Verifica si le sigue un identificador (nombre de variable)
            if (match("Identificador")) {
                  // Verifica si hay un operador de asignación '='
                if (match("Operador", "=")) {
                    // Verifica si lo que sigue es una expresión válida
                    if (expresion()) {
                     // Verifica que termine con un punto y coma    
                        return match("Delimitador", ";");
                    }
                }
            }
        }
        // Si alguno de los pasos anteriores falla, la sentencia es inválida
        return false;
    }

    private boolean expresion() {
        // La expresión debe comenzar con un número
        if (match("Número")) {
            // Si después hay un operador, espera otro número
            if (match("Operador")) {
                return match("Número"); // Verifica número después del operador (ej. + 3)
            }
            // Si no hay operador, la expresión es simplemente un número
            return true;
        } // Si no comienza con un número, no es una expresión válida
        return false;
    }

    private boolean match(String tipo) {
          // Verifica que haya tokens y accede al token actual
        if (indice < tokens.size() && tokens.get(indice).getTipo().equals(tipo)) {
            indice++;
            return true;
        }
        return false;
    }

    private boolean match(String tipo, String valor) {
        if (indice < tokens.size()) {
            Token token = tokens.get(indice);
            if (token.getTipo().equals(tipo) && token.getValor().equals(valor)) {
                indice++;
                return true;
            }
        }
        return false;
    }
/**
     * Devuelve el token actual para mostrar en mensajes de error.
     * Si no hay más tokens, devuelve un token EOF simulado.
     */
    private Token actual() {
        if (indice < tokens.size()) {
            return tokens.get(indice);
        }
        return new Token("EOF", "");
    }
   
}