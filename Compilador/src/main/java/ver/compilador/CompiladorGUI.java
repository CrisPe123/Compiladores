package ver.compilador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;


class Compilador extends JFrame {

    public JTextArea codigoFuente;
    public JTextField resultadoLexico;
    public JTextField resultadoSintactico;
    public JTextField resultadoSemantico;
    public JTextField resultadoEscritoLexico;
    public JButton btnAnalizar;
    public JButton btnArbol; //

    public Compilador() {
        setTitle("Compilador en Java");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        codigoFuente = new JTextArea();
        JScrollPane scroll = new JScrollPane(codigoFuente);

        resultadoLexico = new JTextField("Análisis Léxico: ");
        resultadoSintactico = new JTextField("Análisis Sintáctico: ");
        resultadoSemantico = new JTextField("Análisis Semántico: ");
        resultadoEscritoLexico = new JTextField("Análisis Lexico Escrito: ");

        resultadoLexico.setEditable(false);
        resultadoSintactico.setEditable(false);
        resultadoSemantico.setEditable(false);
        resultadoEscritoLexico.setEditable(false);

        JPanel panelInferior = new JPanel(new GridLayout(3, 1, 5, 5));
        panelInferior.add(resultadoLexico);
        panelInferior.add(resultadoEscritoLexico);
        panelInferior.add(resultadoSintactico);
        panelInferior.add(resultadoSemantico);
        

        
        btnAnalizar = new JButton("Analizar");
       btnArbol = new JButton("Arbol");
    
        
        btnAnalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoFuente.getText();

                //Analisis Léxico
                List<Token> tokens = AnalizadorLexico.tokenizar(codigo);
                resultadoLexico.setText("Análisis Léxico: OK (" + tokens.size() + " tokens)");
                
                resultadoEscritoLexico.setText("Análisis Léxico: " + AnalizadorLexico.analizar(codigo));

                //Analisis Sintáctico
                AnalizadorSintectico parser = new AnalizadorSintectico(tokens);
                String sintaxis = parser.analizar();
                resultadoSintactico.setText(sintaxis);
              // Análisis Semántico
                AnalizadorSemantico semantico = new AnalizadorSemantico(tokens);
                String resultadoSem = semantico.analizar();
                resultadoSemantico.setText(resultadoSem);
            }
        });
        
            // Botón para mostrar el árbol sintáctico
        btnArbol = new JButton("Árbol");
       btnArbol.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String codigo = codigoFuente.getText();
            List<Token> tokens = AnalizadorLexico.tokenizar(codigo);

            GeneradorArbolSintacticoDesdeTokens generador = new GeneradorArbolSintacticoDesdeTokens(tokens);
            DefaultMutableTreeNode raiz = generador.generarArbol();

            JTree arbol = new JTree(raiz);
            JScrollPane scrollArbol = new JScrollPane(arbol);

            JFrame frameArbol = new JFrame("Árbol Sintáctico");
            frameArbol.setSize(400, 300);
            frameArbol.add(scrollArbol);
            frameArbol.setLocationRelativeTo(null);
            frameArbol.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar árbol: " + ex.getMessage());
        }
    }
});

        setLayout(new BorderLayout(10, 10));
    
            // Panel con los botones uno debajo del otro
            JPanel panelBotones = new JPanel();
            panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
            panelBotones.add(btnAnalizar);
            panelBotones.add(Box.createVerticalStrut(5)); // espacio entre botones
            panelBotones.add(btnArbol);

// Agregar el panel al norte)
add(panelBotones, BorderLayout.NORTH);


        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Compilador().setVisible(true));
    }
}