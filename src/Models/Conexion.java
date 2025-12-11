package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    Connection con;
    public Connection getConexion(){
        try {
            String db = "jdbc:mysql://localhost:3306/ddc?serverTimezone=UTC";
            con = DriverManager.getConnection(db, "root", "");
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la Base de datos: " + e.toString());
        }
        return null;
    }
}
