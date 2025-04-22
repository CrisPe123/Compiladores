package ver.compilador;

import javax.swing.tree.DefaultMutableTreeNode;

public class ArbolVisual {

    // Método para convertir un Nodo a DefaultMutableTreeNode
    public static DefaultMutableTreeNode convertir(Nodo raiz) {
        DefaultMutableTreeNode nodoSwing = new DefaultMutableTreeNode(raiz.getValor());
        
        // Recursivamente convertir los hijos
        for (Nodo hijo : raiz.getHijos()) {
            nodoSwing.add(convertir(hijo));
        }

        return nodoSwing;
    }
}