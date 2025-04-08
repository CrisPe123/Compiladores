import re

# Nodo del árbol
class Nodo:
    def __init__(self, tipo, valor=None, hijos=None):
        self.tipo = tipo
        self.valor = valor
        self.hijos = hijos or []

    def imprimir_arbol(self, nivel=0):
        sangria = '  ' * nivel
        if self.valor:
            print(f"{sangria}{self.tipo}: {self.valor}")
        else:
            print(f"{sangria}{self.tipo}")
        for hijo in self.hijos:
            hijo.imprimir_arbol(nivel + 1)

# Tokenizador
def tokenizar(expresion):
    return re.findall(r'\d+|[a-zA-Z_]\w*|[()+\-*/=]', expresion)

# Analizador descendente
class Analizador:
    def __init__(self, tokens):
        self.tokens = tokens
        self.posicion = 0

    def actual(self):
        return self.tokens[self.posicion] if self.posicion < len(self.tokens) else None

    def consumir(self, esperado):
        if self.actual() == esperado:
            self.posicion += 1
        else:
            raise SyntaxError(f"Se esperaba '{esperado}', se encontró '{self.actual()}'")

    def analizar_E(self):
        nodo = self.analizar_T()
        while self.actual() in ('+', '-'):
            operador = self.actual()
            self.consumir(operador)
            derecho = self.analizar_T()
            nodo = Nodo('E', operador, [nodo, derecho])
        return nodo

    def analizar_T(self):
        nodo = self.analizar_F()
        while self.actual() in ('*', '/'):
            operador = self.actual()
            self.consumir(operador)
            derecho = self.analizar_F()
            nodo = Nodo('T', operador, [nodo, derecho])
        return nodo

    def analizar_F(self):
        if self.actual() == '(':
            self.consumir('(')
            nodo = self.analizar_E()
            self.consumir(')')
            return Nodo('F', None, [nodo])
        elif re.match(r'[a-zA-Z_]\w*', self.actual()):
            identificador = self.actual()
            self.consumir(identificador)
            return Nodo('F', identificador)
        elif re.match(r'\d+', self.actual()):
            numero = self.actual()
            self.consumir(numero)
            return Nodo('F', numero)
        else:
            raise SyntaxError(f"Token inesperado: {self.actual()}")

# Ejecución
expresion = "a * (b + c) - d"
tokens = tokenizar(expresion)
analizador = Analizador(tokens)
arbol = analizador.analizar_E()

# Imprimir árbol sintáctico
arbol.imprimir_arbol()