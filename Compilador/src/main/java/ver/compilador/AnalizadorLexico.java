package ver.compilador;

import java.util.*;
import java.util.regex.*;

public class AnalizadorLexico {
    
    // Guardamos las palabras claves se pueden agregar mas para que en analisis lexico sea mas completo.
    private static final String[] PALABRAS_CLAVE = {"int", "float", "if", "else", "while", "return", "void","for" };

    // esta clase al final devuelve una lista de tokens que van a ser usados para crear el arbol sintactico
    public static List<Token> tokenizar(String codigo) {
        List<Token> tokens = new ArrayList<>();

        // aca creamos expresiones regulares y los organizamos por palabras claves, numeros, operadores, delimitadores, se pueden agregar mas para que sea mas completo.
        String regex = "\\s*(?:(?<KEYWORD>int|float|if|else|while|return|void)|" +
                       "(?<NUMBER>\\d+)|" +
                       "(?<ID>[a-zA-Z_][a-zA-Z0-9_]*)|" +
                       "(?<OP>[+\\-*/=])|" +
                       "(?<DELIM>[;(){}])|" +
                       "(?<UNKNOWN>\\S))";

        Pattern pattern = Pattern.compile(regex); // clase reservada de java.util.regex es usada para compilar una expresion regular y convertirlo en un objeto utilizable
        Matcher matcher = pattern.matcher(codigo); // con el matcher lo que logramos es agrupar las expresiones y al final se guarda en codigo.

        while (matcher.find()) {
            if (matcher.group("KEYWORD") != null) {
                tokens.add(new Token("PalabraClave", matcher.group()));
            } else if (matcher.group("NUMBER") != null) {
                tokens.add(new Token("Número", matcher.group()));
            } else if (matcher.group("ID") != null) {
                String val = matcher.group();
                if (!Arrays.asList(PALABRAS_CLAVE).contains(val)) {
                    tokens.add(new Token("Identificador", val));
                }
            } else if (matcher.group("OP") != null) {
                tokens.add(new Token("Operador", matcher.group()));
            } else if (matcher.group("DELIM") != null) {
                tokens.add(new Token("Delimitador", matcher.group()));
            } else if (matcher.group("UNKNOWN") != null) {
                tokens.add(new Token("Desconocido", matcher.group()));
            }
        }

        return tokens;
    }

  // esta clase analizar funciona de la misma manera que tokenizar solo que esta la usamos para mostrar el resultado en resultadoEscritoLexico  
    public static String analizar(String codigo) {
        List<Token> tokens = new ArrayList<>();

        String regex = "\\s*(?:(?<KEYWORD>int|float|if|else|while|return|void)|" +
                       "(?<NUMBER>\\d+)|" +
                       "(?<ID>[a-zA-Z_][a-zA-Z0-9_]*)|" +
                       "(?<OP>[+\\-*/=])|" +
                       "(?<DELIM>[;(){}])|" +
                       "(?<UNKNOWN>\\S))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codigo);

        while (matcher.find()) {
            if (matcher.group("KEYWORD") != null) {
                tokens.add(new Token("PalabraClave", matcher.group()));
            } else if (matcher.group("NUMBER") != null) {
                tokens.add(new Token("Número", matcher.group()));
            } else if (matcher.group("ID") != null) {
                // Asegurarse que no es palabra clave (por coincidencias)
                String val = matcher.group();
                if (!Arrays.asList(PALABRAS_CLAVE).contains(val)) {
                    tokens.add(new Token("Identificador", val));
                }
            } else if (matcher.group("OP") != null) {
                tokens.add(new Token("Operador", matcher.group()));
            } else if (matcher.group("DELIM") != null) {
                tokens.add(new Token("Delimitador", matcher.group()));
            } else if (matcher.group("UNKNOWN") != null) {
                tokens.add(new Token("Desconocido", matcher.group()));
            }
        }

        // Devolver todos los tokens en una línea
        StringBuilder resultado = new StringBuilder("OK: ");
        for (Token t : tokens) {
            resultado.append(t.toString()).append(" ");
        }
        return resultado.toString();
    }
}