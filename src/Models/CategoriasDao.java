package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriasDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public String registrar(Categorias cat) {
        String consulta = "SELECT * FROM categorias WHERE Categoria = ?";
        String sql = "INSERT INTO categorias (Categoria) VALUES (?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, cat.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cat.getNombre());
                ps.execute();
                return "Registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaCategorias(String valor) {
        List<Categorias> listaCat = new ArrayList();
        String sql = "SELECT * FROM categorias";
        String buscar = "SELECT * FROM categorias WHERE Categoria LIKE '%" + valor + "%'";
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
                Categorias cat = new Categorias();
                cat.setId(rs.getInt("Id"));
                cat.setNombre(rs.getString("Categoria"));
                cat.setEstado(rs.getString("Estado"));
                listaCat.add(cat);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaCat;
    }

    public String modificar(Categorias cat) {
        String consulta = "SELECT * FROM categorias WHERE Categoria = ? AND ID != ?";
        String sql = "UPDATE categorias SET Categoria = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, cat.getNombre());
            ps.setInt(2, cat.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cat.getNombre());
                ps.setInt(2, cat.getId());
                ps.execute();
                return "Modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE categorias SET Estado = ? WHERE ID = ?";
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
