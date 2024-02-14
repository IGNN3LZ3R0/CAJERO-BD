import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
public class Deposito extends JFrame {
    private JTextField pantallad;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton Enter;
    private JButton menu;
    private JButton borrarButton;
    private JPanel paneldp;
    private String nombreUsuario;
    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Banco";
        String usuarioDB = "root";
        String contraseñaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseñaDB);
    }

    public Deposito(String nombreUsuario) {
        super("Deposito");
        this.nombreUsuario = nombreUsuario;
        setContentPane(paneldp);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);


        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frameRegreso = (JFrame) SwingUtilities.getWindowAncestor(paneldp);
                frameRegreso.dispose();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantallad.setText("");
            }
        });
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "1";
                pantallad.setText(cons);
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "2";
                pantallad.setText(cons);
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "3";
                pantallad.setText(cons);
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "4";
                pantallad.setText(cons);
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "5";
                pantallad.setText(cons);
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "6";
                pantallad.setText(cons);
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "7";
                pantallad.setText(cons);
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "8";
                pantallad.setText(cons);
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "9";
                pantallad.setText(cons);
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons = pantallad.getText() + "0";
                pantallad.setText(cons);
            }
        });


        Enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });
    }
    private void realizarDeposito() {
        try {
            Connection conn = establecerConexion();
            String query = "SELECT saldo_actual FROM Usuarios WHERE nombre_de_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                float saldoActual = rs.getFloat("saldo_actual");
                String depositoStr = pantallad.getText();
                float deposito = Float.parseFloat(depositoStr);
                if (deposito > 0) {
                    float nuevoSaldo = saldoActual + deposito;
                    String updateQuery = "UPDATE Usuarios SET saldo_actual = ? WHERE nombre_de_usuario = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setFloat(1, nuevoSaldo);
                    updateStmt.setString(2, nombreUsuario);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Se depositó: " + deposito + "\nSaldo actual: " + nuevoSaldo);
                } else {
                    JOptionPane.showMessageDialog(null, "El monto del depósito debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar el depósito: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
