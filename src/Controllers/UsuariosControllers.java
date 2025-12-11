package Controllers;

import Models.Combo;
import Models.Tables;
import Models.Usuarios;
import Models.UsuariosDao;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class UsuariosControllers implements ActionListener, MouseListener, KeyListener {

    private Usuarios us;
    private UsuariosDao usDao;
    private PanelAdmin views;

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloGeneral = new DefaultTableModel();

    public UsuariosControllers(Usuarios us, UsuariosDao usDao, PanelAdmin views) {
        this.us = us;
        this.usDao = usDao;
        this.views = views;
        this.views.btnRegistrarUser.addActionListener(this);
        this.views.btnModificarUser.addActionListener(this);
        this.views.JMenuEliminarUser.addActionListener(this);
        this.views.JMenuReingresarUser.addActionListener(this);
        this.views.btnNuevoUser.addActionListener(this);
        this.views.btnCambiarContra.addActionListener(this);
        this.views.btnCambiarContraGeneral.addActionListener(this);
        this.views.txtBuscarUser.addKeyListener(this);
        this.views.tableUsers.addMouseListener(this);
        this.views.tableUsersGenerales.addMouseListener(this);
        this.views.labelUsers.addMouseListener(this);
        this.views.btnLimpiarContra.addActionListener(this);
        this.views.btnLimpiarContraGeneral.addActionListener(this);
        listarUsuarios();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarUser) {
            if (views.txtUsuarioUser.getText().equals("") || views.txtNombreUser.getText().equals("") || String.valueOf(views.txtClaveUser.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                us.setUsuario(views.txtUsuarioUser.getText());
                us.setNombre(views.txtNombreUser.getText());
                us.setClave(String.valueOf(views.txtClaveUser.getPassword()));
                Combo itemCaja = (Combo) views.cbxCajaUser.getSelectedItem();
                us.setCaja(itemCaja.getId());
                us.setRol(views.cbxRolUser.getSelectedItem().toString());
                
                if(us.getClave().matches("^(?=(?:.*\\d){2,})(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_\\-]).{8,16}$")){
                    String respuesta = usDao.registrar(us);
                    switch (respuesta) {
                        case "Existe":
                            JOptionPane.showMessageDialog(null, "El Usuario debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Registrado":
                            limpiarTable();
                            listarUsuarios();
                            limpiar();
                            JOptionPane.showMessageDialog(null, "Usuario Registrado", "Registrar Usuario", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener una longitud de entre 8-16 carácteres e incluir mayúsculas, minúsculas, dos números y al menos un caracter especial (!@#$%^&*()_-)", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.btnModificarUser) {
            if (views.txtUsuarioUser.getText().equals("") || views.txtNombreUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                us.setUsuario(views.txtUsuarioUser.getText());
                us.setNombre(views.txtNombreUser.getText());
                Combo itemCaja = (Combo) views.cbxCajaUser.getSelectedItem();
                us.setCaja(itemCaja.getId());
                us.setRol(views.cbxRolUser.getSelectedItem().toString());
                us.setId(Integer.parseInt(views.txtIDuser.getText()));
                
                if(usDao.modificar(us)){
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario modificado con éxito", "Modificar Usuario", JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "Error al modificar el usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.JMenuEliminarUser) {
            if (views.txtIDuser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDuser.getText());
                if (usDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado", "Eliminar Usuario", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.JMenuReingresarUser) {
            if (views.txtIDuser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDuser.getText());
                if (usDao.accion("Activo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario reingresado con éxito", "Reingresar Usuario", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reingresar usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } 
        //Tabla Principal
        else if (e.getSource() == views.btnCambiarContra) {
            String nuevaContra = String.valueOf(views.nuevaContra.getPassword());
            String confirmarContra = String.valueOf(views.confirmarContra.getPassword());
            if (nuevaContra.equals("") || confirmarContra.equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (nuevaContra.equals(confirmarContra)) {

                us.setClave(String.valueOf(views.nuevaContra.getPassword()));
                us.setId(Integer.parseInt(views.txtIDUsuarioRol.getText()));
                
                if(us.getClave().matches("^(?=(?:.*\\d){2,})(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_\\-]).{8,16}$")){
                    if (usDao.cambiarContra(us)) {
                        JOptionPane.showMessageDialog(null, "Clave modificada con éxito", "Modificar Clave", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCambiarClave();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al cambiar la clave", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener una longitud de entre 8-16 carácteres e incluir mayúsculas, minúsculas, dos números y al menos un caracter especial (!@#$%^&*()_-)", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } 
        //Tabla Admin
        else if (e.getSource() == views.btnCambiarContraGeneral) {
            String nuevaContra = String.valueOf(views.nuevaContraGeneral.getPassword());
            String confirmarContra = String.valueOf(views.confirmarContraGeneral.getPassword());
            String IDUserGeneral = views.txtIDUserGeneral.getText();
            if (nuevaContra.equals("") || confirmarContra.equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (nuevaContra.equals(confirmarContra)) {
                if(IDUserGeneral.equals("")){
                    JOptionPane.showMessageDialog(null, "Seleccione un usuario primero", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    us.setClave(String.valueOf(views.nuevaContraGeneral.getPassword()));
                    us.setId(Integer.parseInt(views.txtIDUserGeneral.getText()));

                    if(us.getClave().matches("^(?=(?:.*\\d){2,})(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_\\-]).{8,16}$")){
                        if (usDao.cambiarContra(us)) {
                            JOptionPane.showMessageDialog(null, "Clave modificada con éxito", "Modificar Clave", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCambiarClave();
                            limpiarTable();
                            listarUsuarios();
                            limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al cambiar la clave", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "La contraseña debe tener una longitud de entre 8-16 carácteres e incluir mayúsculas, minúsculas, dos números y al menos un caracter especial (!@#$%^&*()_-)", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == views.btnLimpiarContraGeneral){
            limpiarCambiarClave();
        }else if(e.getSource() == views.btnLimpiarContra){
            limpiarCambiarClave();
        }
        limpiar();
    }

    public void listarUsuarios() {
        //Tabla Principal
        Tables color = new Tables();
        views.tableUsers.setDefaultRenderer(views.tableUsers.getColumnClass(0), color);
        List<Usuarios> lista = usDao.ListaUsuarios(views.txtBuscarUser.getText());
        modelo = (DefaultTableModel) views.tableUsers.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getUsuario();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getRol();
            ob[4] = lista.get(i).getCaja();
            ob[5] = lista.get(i).getNombre_caja();
            ob[6] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.tableUsers.setModel(modelo);
        JTableHeader header = views.tableUsers.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableUsers.getColumnCount(); i++) {
            views.tableUsers.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
        
        //Tabla Admin
        views.tableUsersGenerales.setDefaultRenderer(views.tableUsersGenerales.getColumnClass(0), color);
        modeloGeneral = (DefaultTableModel) views.tableUsersGenerales.getModel();
        Object[] obGeneral = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            obGeneral[0] = lista.get(i).getId();
            obGeneral[1] = lista.get(i).getUsuario();
            obGeneral[2] = lista.get(i).getNombre();
            obGeneral[3] = lista.get(i).getClave();
            obGeneral[4] = lista.get(i).getRol();
            obGeneral[5] = lista.get(i).getEstado();
            modeloGeneral.addRow(obGeneral);
        }
        views.tableUsersGenerales.setModel(modeloGeneral);
        JTableHeader headerGeneral = views.tableUsersGenerales.getTableHeader();
        headerGeneral.setOpaque(false);
        headerGeneral.setBackground(new Color(43, 147, 72));
        headerGeneral.setForeground(Color.white);

        for (int i = 0; i < views.tableUsersGenerales.getColumnCount(); i++) {
            views.tableUsersGenerales.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
        
        for (int i = 0; i < modeloGeneral.getRowCount(); i++) {
            modeloGeneral.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Tabla Principal
        if (e.getSource() == views.tableUsers) {
            int fila = views.tableUsers.rowAtPoint(e.getPoint());
            views.txtIDuser.setText(views.tableUsers.getValueAt(fila, 0).toString());
            views.txtUsuarioUser.setText(views.tableUsers.getValueAt(fila, 1).toString());
            views.txtNombreUser.setText(views.tableUsers.getValueAt(fila, 2).toString());
            views.cbxRolUser.setSelectedItem(views.tableUsers.getValueAt(fila, 3).toString());
            int id_caja = Integer.parseInt(views.tableUsers.getValueAt(fila, 4).toString());
            String caja = views.tableUsers.getValueAt(fila, 5).toString();
            views.cbxCajaUser.setSelectedItem(new Combo(id_caja, caja));
            views.txtClaveUser.setEnabled(false);
            views.btnRegistrarUser.setEnabled(false);
        }
        //Tabla Admin
        else if(e.getSource() == views.tableUsersGenerales){
            int fila = views.tableUsersGenerales.rowAtPoint(e.getPoint());
            views.txtIDUserGeneral.setText(views.tableUsersGenerales.getValueAt(fila, 0).toString());
            views.labelUsuarioSeleccionado.setText(views.tableUsersGenerales.getValueAt(fila, 2).toString());
        } 
        else if (e.getSource() == views.labelUsers) {
            views.jTabbedPane1.setSelectedIndex(3);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtBuscarUser) {
            limpiarTable();
            listarUsuarios();
        }
    }

    private void limpiar() {
        views.txtBuscarUser.setText("");
        views.txtIDuser.setText("");
        views.txtUsuarioUser.setText("");
        views.txtNombreUser.setText("");
        views.txtClaveUser.setText("");

        views.txtClaveUser.setEnabled(true);
        views.btnRegistrarUser.setEnabled(true);
    }
    
    private void limpiarCambiarClave(){
        views.nuevaContra.setText("");
        views.confirmarContra.setText("");
        views.nuevaContraGeneral.setText("");
        views.confirmarContraGeneral.setText("");
    }
}
