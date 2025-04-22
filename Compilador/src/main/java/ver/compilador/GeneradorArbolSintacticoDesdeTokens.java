package ver.compilador;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;



public class GeneradorArbolSintacticoDesdeTokens {

    public List<Token> tokens; // Lista de tokens obtenida del análisis léxico
    public int indice; // Índice actual dentro de la lista de tokens

    // Constructor que inicializa la lista de tokens y el índice
    public GeneradorArbolSintacticoDesdeTokens(List<Token> tokens) {
        this.tokens = tokens;
        this.indice = 0;
    }
    /**
     * Método principal que genera el árbol sintáctico a partir de la lista de tokens.
     * Este método asume que la entrada sigue la estructura:
     * int identificador = número [+ operador número] ;*/
    
    public DefaultMutableTreeNode generarArbol() throws Exception { //DefaultMutableTreeNode: clase de Swing que permite construir nodos para un JTree.
         // Creamos el nodo raíz del árbol sintáctico
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Sentencia");
        // Paso 1: Verificar si el primer token es una palabra clave
        if (match("PalabraClave")) {
            raiz.add(new DefaultMutableTreeNode("PalabraClave: " + anterior().getValor()));
        // Paso 2: Verificar si sigue un identificador
            if (match("Identificador")) {
                raiz.add(new DefaultMutableTreeNode("Identificador: " + anterior().getValor()));
        // Paso 3: Verificar si sigue el operador de asignación
                if (match("Operador", "=")) {
                    raiz.add(new DefaultMutableTreeNode("Operador: ="));
        // Paso 4: Analizar la expresión que está a la derecha del signo igual
                    DefaultMutableTreeNode nodoExp = expresion();
                    if (nodoExp != null) {
                        raiz.add(nodoExp);
        // Paso 5: Verificar si la sentencia finaliza con un punto y coma
                        if (match("Delimitador", ";")) {
                            raiz.add(new DefaultMutableTreeNode("Delimitador: ;"));
                            return raiz;
                        } else {
            // Si falla cualquier paso anterior, se lanza un error de sintaxis general
                            throw new Exception("Falta punto y coma ';'");
                        }
                    }
                }
            }
        }

        throw new Exception("Error de sintaxis");
    }

    private DefaultMutableTreeNode expresion() throws Exception {
         // Comienza esperando un número
         //Verifica si la expresión empieza con un número.
        //Si sí, crea un nodo “Expresion” y le agrega el número.
        if (match("Número")) {
            DefaultMutableTreeNode exp = new DefaultMutableTreeNode("Expresion");
            exp.add(new DefaultMutableTreeNode("Número: " + anterior().getValor()));
            // Si después del primer número hay un operador, se agrega.
            if (match("Operador")) {
                exp.add(new DefaultMutableTreeNode("Operador: " + anterior().getValor()));
                /* Luego se espera un segundo número.
                Si todo está bien, devuelve el árbol de la expresión
                Si falta algo, lanza errores específicos.*/
                if (match("Número")) {
                    exp.add(new DefaultMutableTreeNode("Número: " + anterior().getValor()));
                    return exp;
                } else {
                    throw new Exception("Se esperaba un número después del operador.");
                }
            }

            return exp;
        }

        throw new Exception("Expresión inválida.");
    }
    // Verifica si el token coincide tanto en tipo como en valor 
    private boolean match(String tipo) {
        if (indice < tokens.size() && tokens.get(indice).getTipo().equals(tipo)) {
            indice++;
            return true;
        }
        return false;
    }
// verifica si el token coincide pero tiene una entrada de valor para poder mostrarlo en el arbol
    private boolean match(String tipo, String valor) {
        if (indice < tokens.size()) {
            Token t = tokens.get(indice);
            if (t.getTipo().equals(tipo) && t.getValor().equals(valor)) {
                indice++;
                return true;
            }
        }
        return false;
    }

    private Token anterior() {
        return tokens.get(indice - 1);
    }
}