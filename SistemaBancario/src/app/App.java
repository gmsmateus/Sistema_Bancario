package app;

import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}