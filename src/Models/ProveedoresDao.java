package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedoresDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public String registrar(Proveedores prov) {
        String consulta = "SELECT * FROM proveedor WHERE Ruc = ? OR Telefono = ? OR Correo = ?";
        String sql = "INSERT INTO proveedor (Ruc, Proveedor, Telefono, Correo, Direccion) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, prov.getRuc());
            ps.setString(2, prov.getTelefono());
            ps.setString(3, prov.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, prov.getRuc());
                ps.setString(2, prov.getNombre());
                ps.setString(3, prov.getTelefono());
                ps.setString(4, prov.getCorreo());
                ps.setString(5, prov.getDireccion());
                ps.execute();
                return "Registrado";
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaProveedores(String valor) {
        List<Proveedores> listaProv = new ArrayList();
        String sql = "SELECT * FROM proveedor ORDER BY Estado ASC";
        String buscar = "SELECT * FROM proveedor WHERE Ruc LIKE '%" + valor + "%' OR Proveedor LIKE '%" + valor + "%' OR Telefono LIKE '%" + valor + "%'";
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
                Proveedores prov = new Proveedores();
                prov.setId(rs.getInt("Id"));
                prov.setRuc(rs.getString("Ruc"));
                prov.setNombre(rs.getString("Proveedor"));
                prov.setTelefono(rs.getString("Telefono"));
                prov.setCorreo(rs.getString("Correo"));
                prov.setDireccion(rs.getString("Direccion"));
                prov.setEstado(rs.getString("Estado"));
                listaProv.add(prov);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaProv;
    }

    public String modificar(Proveedores prov) {
        String consulta = "SELECT * FROM proveedor WHERE (Ruc = ? OR Telefono = ? OR Correo = ?) AND ID != ?";
        String sql = "UPDATE proveedor SET Ruc = ?, Proveedor = ?, Telefono = ?, Correo = ?, Direccion = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setString(1, prov.getRuc());
            ps.setString(2, prov.getTelefono());
            ps.setString(3, prov.getCorreo());
            ps.setInt(4, prov.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(sql);
                ps.setString(1, prov.getRuc());
                ps.setString(2, prov.getNombre());
                ps.setString(3, prov.getTelefono());
                ps.setString(4, prov.getCorreo());
                ps.setString(5, prov.getDireccion());
                ps.setInt(6, prov.getId());
                ps.execute();
                return "Modificado";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE proveedor SET Estado = ? WHERE ID = ?";
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

    public Proveedores getDatos(int id_compra) {
        String sql = "SELECT p.*, c.ID, c.ID_proveedor FROM proveedor p INNER JOIN compras c ON p.ID = c.ID_proveedor WHERE c.ID = ?";
        Proveedores pr = new Proveedores();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_compra);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setNombre(rs.getString("Proveedor"));
                pr.setDireccion(rs.getString("Direccion"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return pr;
    }
}
