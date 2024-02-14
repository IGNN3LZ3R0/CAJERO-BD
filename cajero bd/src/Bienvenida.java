import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bienvenida extends JFrame {
    private JTextField text1;
    private JPasswordField text2;
    private JButton ingresarbtn;
    private JButton salirbtn;
    private JPanel panelbn;
    private JButton verificarConexionButton;


    private Connection establecerConexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Banco";
        String usuarioDB = "root";
        String contraseñaDB = "123456";
        return DriverManager.getConnection(url, usuarioDB, contraseñaDB);
    }

    public Bienvenida() {
        super("Banco BUHO.SA");
        setContentPane(panelbn);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        ingresarbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        salirbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int opcion = JOptionPane.showConfirmDialog(Bienvenida.this, "¿Desea salir del banco?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        verificarConexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion_base();
            }
        });
    }
    public void conexion_base() {
        try {
            Connection conexion = establecerConexion();
            JOptionPane.showMessageDialog(null, "Conexión establecida con la base ", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void login() {
        try (Connection conexion = establecerConexion()) {
            String nombreUsu = text1.getText().trim();
            String contraseña = new String(text2.getPassword()).trim();

            String sql = "SELECT * FROM usuarios WHERE nombre_de_usuario=? AND contraseña_encriptada=?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombreUsu);
            pstmt.setString(2, contraseña);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombreUsuario = rs.getString("nombre_de_usuario");
                Menu menu = new Menu(nombreUsuario);
                menu.setVisible(true);
                dispose();
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(panelbn);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
