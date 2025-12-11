package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ComprasDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List ListaCompras(String valor){
        List<Compras> lista = new ArrayList();
        String sql= "SELECT c.*, p.Proveedor FROM compras c INNER JOIN proveedor p ON c.ID_proveedor = p.ID ORDER BY c.ID ASC";
        String buscar ="SELECT c.*, p.Proveedor FROM compras c INNER JOIN proveedor p ON c.ID_proveedor = p.ID WHERE p.Proveedor LIKE '%"+valor+"%' OR c.Fecha LIKE '%"+valor+"%'";
        try {
            con = cn.getConexion();
            if(valor.equalsIgnoreCase("")){
                ps = con.prepareStatement(sql);
            }else{
                ps = con.prepareStatement(buscar);
            }
            rs = ps.executeQuery();
            while(rs.next()){
                Compras com = new Compras();
                com.setId(rs.getInt("Id"));
                com.setId_proveedor(rs.getInt("Id_proveedor"));
                com.setTotal(rs.getDouble("Total"));
                com.setFecha(rs.getString("Fecha"));
                com.setNomProveedor(rs.getString("Proveedor"));
                lista.add(com);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return lista;
    }
    
    public int contarCompras(){
    int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM detalle_compra";
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
