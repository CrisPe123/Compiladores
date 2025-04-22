package ver.compilador;

import java.util.List;

public class GeneradorArbolSintactico {

    private static int indice;
    private static List<Token> tokens;

    // Método principal para generar el árbol sintáctico
    public static Nodo generar(List<Token> listaTokens) {
        tokens = listaTokens;
        indice = 0;
        return EXP();
    }

    // La regla de producción EXP → EXP + TERM | TERM
    private static Nodo EXP() {
        Nodo nodo = new Nodo("EXP");

        // La expresión comienza con un término
        Nodo term = TERM();
        nodo.agregarHijo(term);

        // Si el siguiente token es una suma, agregamos el operador y analizamos otro término
        while (indice < tokens.size() && tokens.get(indice).getTipo().equals("SUMA")) {
            Token suma = tokens.get(indice++);
            nodo.agregarHijo(new Nodo(suma.getValor()));

            Nodo term2 = TERM();
            nodo.agregarHijo(term2);

            System.out.println("EXP → EXP + TERM");
        }

        return nodo;
    }

    // La regla de producción TERM → TERM * FACTOR | FACTOR
    private static Nodo TERM() {
        Nodo nodo = new Nodo("TERM");

        // El término comienza con un factor
        Nodo factor = FACTOR();
        nodo.agregarHijo(factor);

        // Si el siguiente token es un operador de multiplicación, agregamos el operador y analizamos otro factor
        while (indice < tokens.size() && tokens.get(indice).getTipo().equals("MULT")) {
            Token mult = tokens.get(indice++);
            nodo.agregarHijo(new Nodo(mult.getValor()));

            Nodo factor2 = FACTOR();
            nodo.agregarHijo(factor2);

            System.out.println("TERM → TERM * FACTOR");
        }

        return nodo;
    }

    // La regla de producción FACTOR → (EXP) | num
    private static Nodo FACTOR() {
        Token actual = tokens.get(indice);
        Nodo nodo = new Nodo("FACTOR");

        // Si el token es un número, lo agregamos al nodo
        if (actual.getTipo().equals("NUM")) {
            nodo.agregarHijo(new Nodo(actual.getValor()));
            indice++;
            System.out.println("FACTOR → num");
        } 
        // Si el token es un paréntesis de apertura, analizamos una expresión dentro de paréntesis
        else if (actual.getTipo().equals("PAR_ABRE")) {
            nodo.agregarHijo(new Nodo("("));
            indice++;
            Nodo exp = EXP();
            nodo.agregarHijo(exp);

            // Aseguramos que el token siguiente sea un paréntesis de cierre
            if (tokens.get(indice).getTipo().equals("PAR_CIERRA")) {
                nodo.agregarHijo(new Nodo(")"));
                indice++;
                System.out.println("FACTOR → (EXP)");
            }
        }

        return nodo;
    }
}