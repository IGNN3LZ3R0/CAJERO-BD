import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cambioclave extends JFrame {
    private JPanel cambiopn;
    private JButton cambiarButton;
    private JButton menuButton;
    private JTextField txt1;
    private JTextField txt2;
    private String nombreUsuario;
    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Banco";
        String usuarioDB = "root";
        String contraseñaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseñaDB);
    }

    public cambioclave() {
        super("CambioClave");
        this.nombreUsuario = nombreUsuario;
        setContentPane(cambiopn);
        setSize(8000, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                JFrame frameRegreso = (JFrame) SwingUtilities.getWindowAncestor(cambiopn);
                frameRegreso.dispose();
            }
        });
        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String claveActual = txt1.getText();
                String nuevaClave = txt2.getText();
                cambiarClave(claveActual, nuevaClave);
            }
        });
    }
    private void cambiarClave(String claveActual, String nuevaClave) {
        try {
            Connection conn = establecerConexion();
            String consultaClaveQuery = "SELECT contraseña_encriptada FROM Usuarios WHERE nombre_de_usuario = ?";
            PreparedStatement consultaStmt = conn.prepareStatement(consultaClaveQuery);
            consultaStmt.setString(1, nombreUsuario);
            ResultSet rs = consultaStmt.executeQuery();

            if (rs.next()) {
                String claveAlmacenada = rs.getString("contraseña_encriptada");
                if (claveAlmacenada.equals(claveActual)) {
                    String updateQuery = "UPDATE Usuarios SET contraseña_encriptada = ? WHERE nombre_de_usuario = ?";
                    PreparedStatement stmt = conn.prepareStatement(updateQuery);
                    stmt.setString(1, nuevaClave);
                    stmt.setString(2, nombreUsuario);
                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Clave cambiada exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo cambiar la clave.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    stmt.close();
                } else {
                    JOptionPane.showMessageDialog(null, "La clave actual no coincide con la almacenada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            rs.close();
            consultaStmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cambiar la clave: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
