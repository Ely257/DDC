package Controllers;

import Models.Clientes;
import Models.ClientesDao;
import Models.Combo;
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

public class ClientesControllers implements ActionListener, MouseListener, KeyListener {

    private Clientes cl;
    private ClientesDao clDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public ClientesControllers(Clientes cl, ClientesDao clDao, PanelAdmin views) {
        this.cl = cl;
        this.clDao = clDao;
        this.views = views;
        this.views.btnRegistrarCli.addActionListener(this);
        this.views.btnModificarCli.addActionListener(this);
        this.views.btnRegistrarCliV.addActionListener(this);
        this.views.btnNuevoCli.addActionListener(this);
        this.views.btnNuevoCliV.addActionListener(this);
        this.views.tableClientes.addMouseListener(this);
        this.views.JMenuEliminarCli.addActionListener(this);
        this.views.JMenuReingresarCli.addActionListener(this);
        this.views.txtBuscarCli.addKeyListener(this);
        this.views.labelClientes.addMouseListener(this);
        this.views.labelEstadisticas.addMouseListener(this);
        listarClientes();
        llenarClientes();
        AutoCompleteDecorator.decorate(views.cbxClienteVentas);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarCli) {
            if (views.txtNombreCli.getText().equals("") || views.txtTelefonoCli.getText().equals("") || views.txtDireccionCli.getText().equals("") || views.txtCorreoCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                cl.setNombre(views.txtNombreCli.getText());
                cl.setTelefono(views.txtTelefonoCli.getText());
                cl.setCorreo(views.txtCorreoCli.getText());
                cl.setDireccion(views.txtDireccionCli.getText());
                String respuesta = clDao.registrar(cl);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El teléfono y/o correo eléctronico del cliente debe(n) ser único(s)", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarClientes();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Cliente Registrado", "Registrar Cliente", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        } else if (e.getSource() == views.btnRegistrarCliV) {
            if (views.txtNombreCliV.getText().equals("") || views.txtTelefonoCliV.getText().equals("") || views.txtDireccionCliV.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                cl.setNombre(views.txtNombreCliV.getText());
                cl.setTelefono(views.txtTelefonoCliV.getText());
                cl.setCorreo(views.txtCorreoCliV.getText());
                cl.setDireccion(views.txtDireccionCliV.getText());
                String respuesta = clDao.registrar(cl);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El teléfono y/o correo eléctronico del cliente debe(n) ser único(s)", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarClientes();
                        limpiar();
                        llenarClientes();
                        views.ventanaRegCliente.setVisible(false);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        }else if (e.getSource() == views.btnModificarCli) {
            if (views.txtIDCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione la fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (views.txtNombreCli.getText().equals("") || views.txtTelefonoCli.getText().equals("") || views.txtDireccionCli.getText().equals("") || views.txtCorreoCli.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    cl.setNombre(views.txtNombreCli.getText());
                    cl.setTelefono(views.txtTelefonoCli.getText());
                    cl.setCorreo(views.txtCorreoCli.getText());
                    cl.setDireccion(views.txtDireccionCli.getText());
                    cl.setId(Integer.parseInt(views.txtIDCli.getText()));
                    String respuesta = clDao.modificar(cl);
                    switch (respuesta) {
                        case "Existe":
                            JOptionPane.showMessageDialog(null, "El teléfono y/o correo eléctronico del cliente debe(n) ser único(s)", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "Modificado":
                            limpiarTable();
                            listarClientes();
                            limpiar();
                            JOptionPane.showMessageDialog(null, "Cliente Modificado", "Modificar Cliente", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Error al modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
            }
        } else if (e.getSource() == views.JMenuEliminarCli) {
            if (views.txtIDCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDCli.getText());
                if (clDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado", "Eliminar Cliente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.JMenuReingresarCli) {
            if (views.txtIDCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDCli.getText());
                if (clDao.accion("Activo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente reingresado", "Reingresar Cliente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reingresar cliente", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == views.btnNuevoCliV) {
            limpiar();
        }else {
            limpiar();
        }
    }

    public void listarClientes() {
        Tables color = new Tables();
        views.tableClientes.setDefaultRenderer(views.tableClientes.getColumnClass(0), color);
        List<Clientes> lista = clDao.ListaClientes(views.txtBuscarCli.getText());
        modelo = (DefaultTableModel) views.tableClientes.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getTelefono();
            ob[3] = lista.get(i).getCorreo();
            ob[4] = lista.get(i).getDireccion();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.tableClientes.setModel(modelo);
        JTableHeader header = views.tableClientes.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableClientes.getColumnCount(); i++) {
            views.tableClientes.getColumnModel().getColumn(i).setCellRenderer(centroRender);
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
        if (e.getSource() == views.tableClientes) {
            int fila = views.tableClientes.rowAtPoint(e.getPoint());
            views.txtIDCli.setText(views.tableClientes.getValueAt(fila, 0).toString());
            views.txtNombreCli.setText(views.tableClientes.getValueAt(fila, 1).toString());
            views.txtTelefonoCli.setText(views.tableClientes.getValueAt(fila, 2).toString());
            views.txtCorreoCli.setText(views.tableClientes.getValueAt(fila, 3).toString());
            views.txtDireccionCli.setText(views.tableClientes.getValueAt(fila, 4).toString());
        } else if (e.getSource() == views.labelClientes) {
            views.jTabbedPane1.setSelectedIndex(1);
        } else if (e.getSource() == views.labelEstadisticas) {
            views.jTabbedPane1.setSelectedIndex(14);
            ClientesDao cliDao = new ClientesDao();
            int total = cliDao.contarClientes();
            views.labeltotalclientes.setText("" + total);
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

    private void limpiar() {
        views.txtBuscarCli.setText("");
        views.txtNombreCli.setText("");
        views.txtTelefonoCli.setText("");
        views.txtCorreoCli.setText("");
        views.txtDireccionCli.setText("");
        views.txtNombreCliV.setText("");
        views.txtTelefonoCliV.setText("");
        views.txtCorreoCliV.setText("");
        views.txtDireccionCliV.setText("");
        views.txtIDCli.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtBuscarCli) {
            limpiarTable();
            listarClientes();
        }
    }

    private void llenarClientes() {
        List<Clientes> lista = clDao.ListaClientes(views.txtBuscarCli.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxClienteVentas.addItem(new Combo(id, nombre));
        }
    }
}
