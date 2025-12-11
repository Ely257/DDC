package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedidasDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public String registrar(Medidas med) {
        String consulta = "SELECT * FROM medidas WHERE Medida = ?";
        String sql = "INSERT INTO medidas (Medida, Nombre_corto) VALUES (?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, med.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, med.getNombre());
                ps.setString(2, med.getNombre_corto());
                ps.execute();
                return "Registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaMedidas(String valor) {
        List<Medidas> listaMed = new ArrayList();
        String sql = "SELECT * FROM medidas ORDER BY Estado ASC";
        String buscar = "SELECT * FROM medidas WHERE Medida LIKE '%" + valor + "%' OR Nombre_corto LIKE '%" + valor + "%'";
        try {
            con = cn.getConexion();
            if (valor.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                Medidas med = new Medidas();
                med.setId(rs.getInt("Id"));
                med.setNombre(rs.getString("Medida"));
                med.setNombre_corto(rs.getString("Nombre_corto"));
                med.setEstado(rs.getString("Estado"));
                listaMed.add(med);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaMed;
    }

    public String modificar(Medidas med) {
        String consulta = "SELECT * FROM medidas WHERE Medida = ? AND ID != ?";
        String sql = "UPDATE medidas SET Medida = ?, Nombre_corto = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, med.getNombre());
            ps.setInt(2, med.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, med.getNombre());
                ps.setString(2, med.getNombre_corto());
                ps.setInt(3, med.getId());
                ps.execute();
                return "Modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE medidas SET Estado = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }
}
