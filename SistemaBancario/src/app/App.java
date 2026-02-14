package app;

import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class App {
    public static void main(String[] args) {
        // O invokeLater garante que a interface rode na thread correta do Java
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}