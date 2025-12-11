package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuariosDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public Usuarios login(String usuario, String clave){
        String sql = "SELECT * FROM usuarios WHERE Usuario = ? AND clave = ?";
        Usuarios us = new Usuarios();
        try {
            con = cn.getConexion();
            ps= con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs= ps.executeQuery();
            if(rs.next()){
                us.setId(rs.getInt("ID"));
                us.setUsuario(rs.getString("Usuario"));
                us.setNombre(rs.getString("Nombre"));
                us.setRol(rs.getString("Rol"));
                us.setEstado(rs.getString("Estado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return us;
    }
    
    public String registrar(Usuarios us){
        String consulta = "SELECT * FROM usuarios WHERE Usuario = ?";
        String sql= "INSERT INTO usuarios (Usuario, Nombre, Clave, ID_caja, Rol) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps= con.prepareStatement(consulta);
            ps.setString(1, us.getUsuario());
            rs = ps.executeQuery();
            if(rs.next()){
                return "Existe";
            }else{
                ps= con.prepareStatement(sql);
                ps.setString(1, us.getUsuario());
                ps.setString(2, us.getNombre());
                ps.setString(3, us.getClave());
                ps.setInt(4, us.getCaja());
                ps.setString(5, us.getRol());
                ps.execute();
                return "Registrado";
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return "Error";
        }
    }
    
    public List ListaUsuarios(String valor){
        List<Usuarios> listaUsers = new ArrayList();
        String sql= "SELECT u.*, c.Nombre AS Caja FROM Usuarios u INNER JOIN cajas c ON u.ID_caja = c.ID";
        String buscar ="SELECT u.*, c.Nombre AS Caja FROM Usuarios u INNER JOIN cajas c ON u.ID_caja = c.ID WHERE u.Usuario LIKE '%"+valor+"%' OR u.Nombre LIKE '%"+valor+"%' OR c.Nombre LIKE '%"+valor+"%'";
        try {
            con = cn.getConexion();
            if(valor.equalsIgnoreCase("")){
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            }else{
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }
            
            while(rs.next()){
                Usuarios us = new Usuarios();
                us.setId(rs.getInt("Id"));
                us.setUsuario(rs.getString("Usuario"));
                us.setNombre(rs.getString("Nombre"));
                us.setClave(rs.getString("Clave"));
                us.setCaja(rs.getInt("ID_caja"));
                us.setNombre_caja(rs.getString("Caja"));
                us.setRol(rs.getString("Rol"));
                us.setEstado(rs.getString("Estado"));
                listaUsers.add(us);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaUsers;
    }
    
    public boolean modificar(Usuarios us){
        String sql= "UPDATE usuarios SET Usuario = ?, Nombre = ?, ID_caja = ?, Rol = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps= con.prepareStatement(sql);
            ps.setString(1, us.getUsuario());
            ps.setString(2, us.getNombre());
            ps.setInt(3, us.getCaja());
            ps.setString(4, us.getRol());
            ps.setInt(5, us.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
    
    public boolean accion(String estado, int id){
        String sql = "UPDATE usuarios SET Estado = ? WHERE ID = ?";
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
    
    public Configuracion getConfig(){
        String sql = "SELECT * FROM configuracion";
        Configuracion cof = new Configuracion();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                cof.setId(rs.getInt("Id"));
                cof.setRuc(rs.getString("Ruc"));
                cof.setNombre(rs.getString("Nombre"));
                cof.setTelefono(rs.getString("Telefono"));
                cof.setDireccion(rs.getString("Direccion"));
                cof.setMensaje(rs.getString("Mensaje"));
                cof.setIg(rs.getString("IG"));
                cof.setCorreo(rs.getString("Correo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return cof;
    }
    
    public boolean cambiarContra(Usuarios us){
        String sql= "UPDATE usuarios SET Clave = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps= con.prepareStatement(sql);
            ps.setString(1, us.getClave());
            ps.setInt(2, us.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
    
    public boolean modificarDatosEmpresa(Configuracion cof){
        String sql= "UPDATE configuracion SET Ruc = ?, Nombre = ?, Telefono = ?, Direccion = ?, Mensaje = ?, IG = ?, Correo = ? WHERE ID = ?";
        try {
            con = cn.getConexion();
            ps= con.prepareStatement(sql);
            ps.setString(1, cof.getRuc());
            ps.setString(2, cof.getNombre());
            ps.setString(3, cof.getTelefono());
            ps.setString(4, cof.getDireccion());
            ps.setString(5, cof.getMensaje());
            ps.setString(6, cof.getIg());
            ps.setString(7, cof.getCorreo());
            ps.setInt(8, cof.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
}
