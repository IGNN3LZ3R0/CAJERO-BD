import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Bienvenida buelcome = new Bienvenida();
                buelcome.setSize(800,500);
                buelcome.setVisible(true);
                buelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
