import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Saldo extends JFrame {
    private JButton menubtn;
    private JPanel panelsl;
    private JLabel Saldotxt;
    private String nombreUsuario;



    public Saldo(String nombreUsuario) {
        super("Saldo");
        this.nombreUsuario = nombreUsuario;
        setContentPane(panelsl);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menubtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelsl);
                frame.dispose();
            }
        });

        mostrarSaldoDesdeBaseDeDatos();
    }

    private void mostrarSaldoDesdeBaseDeDatos() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Banco", "root", "123456");

            String query = "SELECT saldo_actual FROM Usuarios WHERE nombre_de_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                float saldo = rs.getFloat("saldo_actual");
                Saldotxt.setText(String.valueOf(saldo));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el saldo desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
