package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientesDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public String registrar(Clientes cl) {
        String consulta = "SELECT * FROM clientes WHERE Telefono = ? OR Correo = ?";
        String sql = "INSERT INTO clientes (Nombre, Telefono, Correo, Direccion) VALUES (?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getTelefono());
            ps.setString(2, cl.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getTelefono());
                ps.setString(3, cl.getCorreo());
                ps.setString(4, cl.getDireccion());
                ps.execute();
                return "Registrado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaClientes(String valor) {
        List<Clientes> listaCli = new ArrayList();
        String sql = "SELECT * FROM clientes ORDER BY Estado ASC";
        String buscar = "SELECT * FROM clientes WHERE Nombre LIKE '%" + valor + "%' OR Telefono LIKE '%" + valor + "%'";
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
                Clientes cl = new Clientes();
                cl.setId(rs.getInt("Id"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setTelefono(rs.getString("Telefono"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setEstado(rs.getString("Estado"));
                listaCli.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaCli;
    }

    public String modificar(Clientes cl) {
        String consulta = "SELECT * FROM clientes WHERE (Telefono = ? OR Correo = ?) AND ID != ?";
        String sql = "UPDATE clientes SET Nombre = ?, Telefono = ?, Correo = ?, Direccion = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, cl.getTelefono());
            ps.setString(2, cl.getCorreo());
            ps.setInt(3, cl.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, cl.getNombre());
                ps.setString(2, cl.getTelefono());
                ps.setString(3, cl.getCorreo());
                ps.setString(4, cl.getDireccion());
                ps.setInt(5, cl.getId());
                ps.execute();
                return "Modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE clientes SET Estado = ? WHERE ID = ?";
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
    
    public int contarClientes(){
    int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM clientes";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return total;
    }
}
