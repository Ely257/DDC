package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CajasDao {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Cajas cj) {
        String sql = "INSERT INTO cajas (Nombre) VALUES (?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cj.getNombre());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List ListaCajas(String valor) {
        List<Cajas> listaCaj = new ArrayList();
        String sql = "SELECT * FROM cajas ORDER BY Estado ASC";
        String buscar = "SELECT * FROM cajas WHERE Nombre LIKE '%" + valor + "%'";
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
                Cajas cj = new Cajas();
                cj.setId(rs.getInt("Id"));
                cj.setNombre(rs.getString("Nombre"));
                cj.setEstado(rs.getString("Estado"));
                listaCaj.add(cj);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaCaj;
    }

    public boolean modificar(Cajas cj) {
        String sql = "UPDATE cajas SET Nombre = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cj.getNombre());
            ps.setInt(2, cj.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE cajas SET Estado = ? WHERE ID = ?";
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

    public String abrirCaja(String fecha_apertura, double monto, int id_usuario) {
        String consulta = "SELECT d.*, u.Nombre from detalle_cajas d INNER JOIN usuarios u ON d.ID_usuario = u.ID WHERE d.Estado = 1 AND u.ID=?";
        String consultaActividad = "SELECT c.*, u.ID_caja from cajas c inner join usuarios u on u.ID_caja = c.ID WHERE c.Estado = 'Inactivo' AND u.ID=?";
        String sql = "INSERT INTO detalle_cajas (Fecha_apertura, Monto_inicial, ID_usuario) VALUES (?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(consulta);
            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                return "Existe";
            } else {
                ps = con.prepareStatement(consultaActividad);
                ps.setInt(1, id_usuario);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return "Inactiva";
                }else{
                    ps = con.prepareStatement(sql);
                    ps.setString(1, fecha_apertura);
                    ps.setDouble(2, monto);
                    ps.setInt(3, id_usuario);
                    ps.execute();
                    return "Registrado";
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }

    public List ListaAperturas(String valor) {
        List<AperturaCierre> listaCaj = new ArrayList();
        try {
            con = cn.getConexion();
            if (valor.equalsIgnoreCase("")) {
                String sql = "SELECT d.*, u.Nombre from detalle_cajas d INNER JOIN usuarios u ON d.ID_usuario = u.ID ORDER BY d.Fecha_apertura DESC";
                ps = con.prepareStatement(sql);
            } else {
                String buscar = "SELECT d.*, u.Nombre from detalle_cajas d INNER JOIN usuarios u ON d.ID_usuario = u.ID WHERE u.Nombre LIKE '%" + valor + "%' OR d.Fecha_apertura LIKE '%" + valor + "%'";
                ps = con.prepareStatement(buscar);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                AperturaCierre apert = new AperturaCierre();
                apert.setFecha_apertura(rs.getString("Fecha_apertura"));
                apert.setFecha_cierre(rs.getString("Fecha_cierre"));
                apert.setMonto_inicial(rs.getDouble("Monto_inicial"));
                apert.setMonto_final(rs.getDouble("Monto_final"));
                apert.setTotal_ventas(rs.getInt("Total_ventas"));
                apert.setNom_usuario(rs.getString("Nombre"));
                listaCaj.add(apert);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaCaj;
    }
    
    public double MontoFinal(int ID_user) {
        double monto = 0.00;
        String sql = "SELECT SUM(Total) AS monto_total FROM ventas WHERE ID_user = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ID_user);
            rs = ps.executeQuery();
            if(rs.next()){
                monto = rs.getDouble("monto_total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return monto;
    }
    
    public int totalVentas(int ID_user) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM ventas WHERE ID_user = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ID_user);
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return total;
    }
    
    public boolean cerrarCaja(AperturaCierre apert) {
        String sql = "UPDATE detalle_cajas SET Fecha_cierre = ?, Monto_final = ?, Total_ventas = ?, Estado = ? WHERE ID_usuario = ? AND Estado = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, apert.getFecha_cierre());
            ps.setDouble(2, apert.getMonto_final());
            ps.setDouble(3, apert.getTotal_ventas());
            ps.setDouble(4, 0);
            ps.setDouble(5, apert.getId_usuario());
            ps.setDouble(6, 1);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return false;
    }
}
