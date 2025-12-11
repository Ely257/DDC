package Controllers;

import Models.Combo;
import Models.Proveedores;
import Models.ProveedoresDao;
import Models.Tables;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ProveedoresControllers implements ActionListener, MouseListener, KeyListener {

    private Proveedores prov;
    private ProveedoresDao provDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public ProveedoresControllers(Proveedores prov, ProveedoresDao provDao, PanelAdmin views) {
        this.prov = prov;
        this.provDao = provDao;
        this.views = views;
        this.views.btnRegistrarProv.addActionListener(this);
        this.views.btnModificarProv.addActionListener(this);
        this.views.btnNuevoProv.addActionListener(this);
        this.views.tableProveedor.addMouseListener(this);
        this.views.JMenuEliminarProv.addActionListener(this);
        this.views.JMenuReingresarProv.addActionListener(this);
        this.views.txtBuscarProv.addKeyListener(this);
        this.views.labelProveedores.addMouseListener(this);
        listarProveedores();
        llenarProveedor();
        AutoCompleteDecorator.decorate(views.cbxProveedorPro);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarProv) {
            if (views.txtRucProveedor.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtCorreoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                prov.setRuc(views.txtRucProveedor.getText());
                prov.setNombre(views.txtNombreProv.getText());
                prov.setTelefono(views.txtTelefonoProv.getText());
                prov.setCorreo(views.txtCorreoProv.getText());
                prov.setDireccion(views.txtDireccionProv.getText());

                String respuesta = provDao.registrar(prov);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El RIF, Teléfono y Correo del proveedor deben ser únicos", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarProveedores();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Proveedor Registrado", "Registrar Proveedor", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        } else if (e.getSource() == views.btnModificarProv) {
            if (views.txtIDprov.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione la fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (views.txtRucProveedor.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtCorreoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    prov.setRuc(views.txtRucProveedor.getText());
                    prov.setNombre(views.txtNombreProv.getText());
                    prov.setTelefono(views.txtTelefonoProv.getText());
                    prov.setCorreo(views.txtCorreoProv.getText());
                    prov.setDireccion(views.txtDireccionProv.getText());
                    prov.setId(Integer.parseInt(views.txtIDprov.getText()));

                    String respuesta = provDao.modificar(prov);
                    switch (respuesta) {
                        case "Existe":
                            JOptionPane.showMessageDialog(null, "El RIF, Teléfono y Correo del proveedor deben ser únicos", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Modificado":
                            limpiarTable();
                            listarProveedores();
                            limpiar();
                            JOptionPane.showMessageDialog(null, "Proveedor Modificado", "Modificar Proveedor", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error al Modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
            }
        } else if (e.getSource() == views.JMenuEliminarProv) {
            if (views.txtIDprov.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDprov.getText());
                if (provDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarProveedores();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado", "Eliminar Proveedor", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar proveedor", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.JMenuReingresarProv) {
            if (views.txtIDprov.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDprov.getText());
                if (provDao.accion("Activo", id)) {
                    limpiarTable();
                    listarProveedores();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Proveedor reingresado", "Reingresar Proveedor", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reingresar proveedor", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            limpiar();
        }
    }

    public void listarProveedores() {
        Tables color = new Tables();
        views.tableProveedor.setDefaultRenderer(views.tableProveedor.getColumnClass(0), color);

        List<Proveedores> lista = provDao.ListaProveedores(views.txtBuscarProv.getText());
        modelo = (DefaultTableModel) views.tableProveedor.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getRuc();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getTelefono();
            ob[4] = lista.get(i).getCorreo();
            ob[5] = lista.get(i).getDireccion();
            ob[6] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }

        views.tableProveedor.setModel(modelo);
        JTableHeader header = views.tableProveedor.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableProveedor.getColumnCount(); i++) {
            views.tableProveedor.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.tableProveedor) {
            int fila = views.tableProveedor.rowAtPoint(e.getPoint());
            views.txtIDprov.setText(views.tableProveedor.getValueAt(fila, 0).toString());
            views.txtRucProveedor.setText(views.tableProveedor.getValueAt(fila, 1).toString());
            views.txtNombreProv.setText(views.tableProveedor.getValueAt(fila, 2).toString());
            views.txtTelefonoProv.setText(views.tableProveedor.getValueAt(fila, 3).toString());
            views.txtCorreoProv.setText(views.tableProveedor.getValueAt(fila, 4).toString());
            views.txtDireccionProv.setText(views.tableProveedor.getValueAt(fila, 5).toString());
        } else if (e.getSource() == views.labelProveedores) {
            views.jTabbedPane1.setSelectedIndex(2);
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
        if (e.getSource() == views.txtBuscarProv) {
            limpiarTable();
            listarProveedores();
        }
    }

    private void limpiar() {
        views.txtBuscarProv.setText("");
        views.txtIDprov.setText("");
        views.txtRucProveedor.setText("");
        views.txtNombreProv.setText("");
        views.txtTelefonoProv.setText("");
        views.txtCorreoProv.setText("");
        views.txtDireccionProv.setText("");
    }

    private void llenarProveedor() {
        List<Proveedores> lista = provDao.ListaProveedores(views.txtBuscarProv.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxProveedorPro.addItem(new Combo(id, nombre));
            views.cbxProveedorNC.addItem(new Combo(id, nombre));
        }
    }
}
