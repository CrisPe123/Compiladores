  
  package ver.compilador;
    
public class Token {
    public String tipo;
    public String valor;

    public Token(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    
    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
    @Override
    public String toString() {
        return tipo + "('" + valor + "')";
    }
}

