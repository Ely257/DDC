package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VentasDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List ListaVentas(String valor){
        List<Ventas> lista = new ArrayList();
        String sql= "SELECT v.*, c.Nombre FROM ventas v INNER JOIN clientes c ON v.ID_cliente = c.ID ORDER BY v.ID ASC";
        String buscar ="SELECT v.*, c.Nombre FROM ventas v INNER JOIN clientes c ON v.ID_cliente = c.ID WHERE c.Nombre LIKE '%"+valor+"%' OR v.Fecha LIKE '%"+valor+"%' ORDER BY v.ID DESC";
        try {
            con = cn.getConexion();
            if(valor.equalsIgnoreCase("")){
                ps = con.prepareStatement(sql);
            }else{
                ps = con.prepareStatement(buscar);
            }
            rs = ps.executeQuery();
            while(rs.next()){
                Ventas ven = new Ventas();
                ven.setId(rs.getInt("Id"));
                ven.setId_cliente(rs.getInt("Id_cliente"));
                ven.setTotal(rs.getDouble("Total"));
                ven.setFecha(rs.getString("Fecha"));
                ven.setNomCliente(rs.getString("Nombre"));
                lista.add(ven);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return lista;
    }
    
    public String verificarCaja(int id_user){
        String consulta = "SELECT * FROM detalle_cajas WHERE Estado = ? AND ID_usuario = ?";
        con = cn.getConexion();
        try {
            ps = con.prepareStatement(consulta);
            ps.setInt(1, 1);
            ps.setInt(2, id_user);
            rs = ps.executeQuery();
            if(rs.next()){
                return "Existe";
            }else{
                return "No";
            }
        } catch (SQLException e) {
            return "Error";
        }
    }
    
    public int contarVentas(){
    int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM detalle_ventas";
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
