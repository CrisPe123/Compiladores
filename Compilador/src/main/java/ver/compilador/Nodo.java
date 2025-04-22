package ver.compilador;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private String valor;
    private List<Nodo> hijos;

    public Nodo(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo) {
        hijos.add(hijo);
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public String getValor() {
        return valor;
    }
}