# Proyecto Compilador en Java

Este proyecto es una implementación básica de un compilador en Java que incluye análisis léxico, sintáctico, semántico y la construcción de árboles sintácticos.

## 🔧 Tecnologías

- Java
- Swing (Interfaz gráfica)
- JTree (Visualización de árboles sintácticos)
---
## 📁 Estructura del Proyecto

## ⚙️ Funcionalidades

- **Análisis léxico**: Divide el código en tokens como palabras clave, identificadores, operadores, números, etc.
- **Análisis sintáctico**: Verifica que las expresiones cumplan con la gramática del lenguaje.
- **Análisis semántico**: Verifica que las variables estén correctamente declaradas antes de usarse.
- **Árbol sintáctico**: Representa la estructura jerárquica de una sentencia con un árbol visual.
---
### 🧬 Enfoque Tecnico

1. **Inicialización**: Se recibe la lista de tokens ya analizados por el analizador léxico.
2. **Construcción manual del árbol**:
   - Se inicia un nodo raíz con la etiqueta `"Sentencia"`.
   - Se agregan nodos hijos de forma secuencial si los tokens coinciden con la estructura esperada (por ejemplo: `PalabraClave`, `Identificador`, `=`, `Número`...).
   - La clase `GeneradorArbolSintacticoDesdeTokens` maneja esta lógica recursivamente, usando métodos como `expresion()` para construir subnodos.
3. **Visualización**: Se convierte el nodo raíz en un `JTree` y se muestra en la GUI al presionar el botón **"Árbol"**.

> El árbol generado refleja la estructura jerárquica de una sentencia como `int x = 5 + 3;`, representando claramente los componentes de la gramática en forma de árbol.
---
## 🚀 Características principales

✅ Interfaz gráfica amigable con campos para escribir el código fuente  
✅ Análisis léxico con clasificación de tokens (palabras clave, identificadores, números, operadores, delimitadores)  
✅ Análisis sintáctico basado en una gramática libre de contexto simple  
✅ Análisis semántico para detección de uso de variables no declaradas  
✅ Generación automática de un **Árbol Sintáctico** visualizado con `JTree`  
✅ Manejo básico de errores sintácticos y semánticos  

---

## 🧪Estructura Simplificada
Sentencia → PalabraClave Identificador '=' Expresion ';'
Expresion → Número (Operador Número)?
---
📌 Posibles mejoras
✅Añadir soporte para más tipos de datos (float, char, etc.)
✅Reconocimiento de estructuras de control (if, while, for)
✅Guardar tokens o árboles generados en archivos .txt o .json
✅Resaltar errores léxicos en el texto ingresado
✅Internacionalización de la GUI

## 🧪Diagrama de flujo
![Diagrama de Flujo Compilador](https://github.com/user-attachments/assets/7102871a-9a39-4e57-874c-16996cb81fb0)


## 📘 Gramática utilizada
La gramática base que maneja el compilador permite analizar expresiones como:
```text
int x = 5;
int y = 4 + 2;




