import re

# Definimos las categorías de tokens con sus expresiones regulares
TOKEN_REGEX = [
    (r'\b(int|float|if|while|return)\b', 'KEYWORD'),  # Palabras clave
    (r'[a-zA-Z_]\w*', 'IDENTIFIER'),                 # Identificadores (variables y funciones)
    (r'==|!=|<=|>=|<|>', 'COMPARISON_OPERATOR'),     # Operadores de comparación
    (r'\+|-|\*|/|=', 'OPERATOR'),                   # Operadores aritméticos y asignación
    (r'[{}();]', 'DELIMITER'),                      # Delimitadores
    (r'"[^"]*"', 'STRING'),                         # Cadenas de texto
    (r'\d+\.\d+|\d+', 'NUMBER'),                    # Números (enteros y decimales)
    (r'\s+', None)                                  # Espacios en blanco (ignorados)
]

class Lexer:
    def __init__(self, input_text):
        self.input_text = input_text
        self.position = 0
        self.line_number = 1

    def tokenize(self):
        tokens = []
        while self.position < len(self.input_text):
            match = None
            for expresion, token_type in TOKEN_REGEX:
                regex = re.compile(expresion)
                match = regex.match(self.input_text, self.position)
                if match:
                    text = match.group(0)
                    if token_type:  # Ignorar espacios en blanco
                        tokens.append((self.line_number, token_type, text))
                    self.position += len(text)
                    self.line_number += text.count("\n")  # Contar líneas nuevas
                    break
            if not match:
                raise SyntaxError(f"Carácter inesperado en línea {self.line_number}: {self.input_text[self.position]}")
        return tokens

# Código de prueba
input_text = '''
int x = 10;
float y = 5.5;
string mensaje = "Hola";
if (x > y) {
x = x + 1;
}
void imprimir() {
return;
}
'''

lexer = Lexer(input_text)
tokens = lexer.tokenize()

# Imprimir tokens en formato de tabla sin tabulate
print(f"{'Línea':<6} | {'Tipo':<20} | Valor")
print("-" * 40)
for line, token_type, value in tokens:
    print(f"{line:<6} | {token_type:<20} | {value}")