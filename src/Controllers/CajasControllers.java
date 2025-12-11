package Controllers;

import Models.AperturaCierre;
import Models.Cajas;
import Models.CajasDao;
import Models.Combo;
import Models.Compras;
import Models.ComprasDao;
import Models.Tables;
import Models.Ventas;
import Models.VentasDao;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class CajasControllers implements ActionListener, MouseListener, KeyListener {

    private Cajas cj;
    private CajasDao cjDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    VentasDao venDao = new VentasDao();
    Ventas ven = new Ventas();

    ComprasDao compDao = new ComprasDao();
        
    public CajasControllers(Cajas cj, CajasDao cjDao, PanelAdmin views) {
        this.cj = cj;
        this.cjDao = cjDao;
        this.views = views;
        this.views.btnRegistrarCaja.addActionListener(this);
        this.views.btnModificarCaja.addActionListener(this);
        this.views.btnNuevoCaja.addActionListener(this);
        this.views.tableCaja.addMouseListener(this);
        this.views.txtBuscarCaja.addKeyListener(this);
        this.views.txtBuscarApertura.addKeyListener(this);
        this.views.labelCaja.addMouseListener(this);
        this.views.JMenuEliminarCaja.addActionListener(this);
        this.views.JMenuReingresarCaja.addActionListener(this);
        this.views.btnayc.addActionListener(this);
        this.views.btnventas.addActionListener(this);
        this.views.btncompras.addActionListener(this);
        this.views.btnUsers1.addActionListener(this);
        this.views.btnAbrirCaja.addActionListener(this);
        this.views.btnCerrarCaja.addActionListener(this);
        this.views.btnNuevoApertura.addActionListener(this);
        llenarCajas();
        AutoCompleteDecorator.decorate(views.cbxCajaUser);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarCaja) {
            registrarCaja();
        } else if (e.getSource() == views.btnModificarCaja) {
            modificarCaja();
        } else if (e.getSource() == views.JMenuEliminarCaja) {
            eliminarCaja();
        } else if (e.getSource() == views.JMenuReingresarCaja) {
            reingresarCaja();
        } else if (e.getSource() == views.btnNuevoCaja) {
            limpiar();
        } else if (e.getSource() == views.btnayc) {
            views.jTabbedPane1.setSelectedIndex(12);
            limpiarTable();
            listarAperturas();
        } else if (e.getSource() == views.btnventas) {
            views.jTabbedPane1.setSelectedIndex(7);
            limpiarTable();
            listarVentas();
        } else if (e.getSource() == views.btncompras) {
            views.jTabbedPane1.setSelectedIndex(8);
            limpiarTable();
            listarCompras();
        } else if (e.getSource() == views.btnUsers1) {
            if(views.labelRolUsuarioConectado.getText().equals("Administrador")){
                views.jTabbedPane1.setSelectedIndex(15);
            }else{
                views.jTabbedPane1.setSelectedIndex(13);
            }
        } 
        //Apertura y Cierre
        else if (e.getSource() == views.btnAbrirCaja) {
            abrirCaja();
        } else if (e.getSource() == views.btnCerrarCaja) {
            cerrarCaja();
        } else if (e.getSource() == views.btnNuevoApertura) {
            nuevoApertura();
        }
    }

    public void listarCajas() {
        Tables color = new Tables();
        views.tableCaja.setDefaultRenderer(views.tableCaja.getColumnClass(0), color);

        List<Cajas> lista = cjDao.ListaCajas(views.txtBuscarCaja.getText());
        modelo = (DefaultTableModel) views.tableCaja.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }

        views.tableCaja.setModel(modelo);
        JTableHeader header = views.tableCaja.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableCaja.getColumnCount(); i++) {
            views.tableCaja.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiar() {
        views.txtBuscarCaja.setText("");
        views.txtIDCaja.setText("");
        views.txtNombreCaja.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.tableCaja) {
            int fila = views.tableCaja.rowAtPoint(e.getPoint());
            views.txtIDCaja.setText(views.tableCaja.getValueAt(fila, 0).toString());
            views.txtNombreCaja.setText(views.tableCaja.getValueAt(fila, 1).toString());
        } else if (e.getSource() == views.labelCaja) {
            views.jTabbedPane1.setSelectedIndex(11);
            limpiarTable();
            listarCajas();
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
        if (e.getSource() == views.txtBuscarCaja) {
            limpiarTable();
            listarCajas();
        }else if (e.getSource() == views.txtBuscarApertura) {
            limpiarTable();
            listarAperturas();
        }
    }

    //Cajas
    private void llenarCajas() {
        List<Cajas> lista = cjDao.ListaCajas(views.txtBuscarCaja.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxCajaUser.addItem(new Combo(id, nombre));
        }
    }

    private void registrarCaja() {
        if (views.txtNombreCaja.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            cj.setNombre(views.txtNombreCaja.getText());
            if (cjDao.registrar(cj)) {
                limpiarTable();
                listarCajas();
                limpiar();
                JOptionPane.showMessageDialog(null, "Caja registrada", "Registrar Caja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la caja", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarCaja() {
        if (views.txtIDCaja.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione la fila", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            if (views.txtNombreCaja.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                cj.setNombre(views.txtNombreCaja.getText());
                cj.setId(Integer.parseInt(views.txtIDCaja.getText()));
                if (cjDao.modificar(cj)) {
                    limpiarTable();
                    listarCajas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Caja modificada", "Modificar Caja", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar caja", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void eliminarCaja() {
        if (views.txtIDCaja.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            int id = Integer.parseInt(views.txtIDCaja.getText());
            if (cjDao.accion("Inactivo", id)) {
                limpiarTable();
                listarCajas();
                limpiar();
                JOptionPane.showMessageDialog(null, "Caja eliminada", "Eliminar Caja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar caja", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reingresarCaja() {
        if (views.txtIDCaja.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            int id = Integer.parseInt(views.txtIDCaja.getText());
            if (cjDao.accion("Activo", id)) {
                limpiarTable();
                listarCajas();
                limpiar();
                JOptionPane.showMessageDialog(null, "Caja reingresada", "Reingresar Caja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al reingresar caja", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Apertura y Cierre
    public void abrirCaja() {
        if (views.txtMontoInicial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el monto inicial", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            double monto = Double.parseDouble(views.txtMontoInicial.getText());
            int id_user = Integer.parseInt(views.txtIDUsuarioRol.getText());
            
            LocalDateTime now = LocalDateTime.now();
            String fecha_apertura = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
            
            String resultado = cjDao.abrirCaja(fecha_apertura, monto, id_user);
            if ("Existe".equalsIgnoreCase(resultado)) {
                JOptionPane.showMessageDialog(null, "La caja ya está abierta", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else if("Inactiva".equalsIgnoreCase(resultado)){
                JOptionPane.showMessageDialog(null, "Caja del usuario inactiva", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if ("Registrado".equalsIgnoreCase(resultado)) {
                limpiarTable();
                listarAperturas();
                nuevoApertura();
                JOptionPane.showMessageDialog(null, "Caja Abierta", "Abrir Caja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al abrir la caja", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cerrarCaja() {
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro de cerrar la Caja?");
        if (pregunta == 0) {
            double monto_final = cjDao.MontoFinal(Integer.parseInt(views.txtIDUsuarioRol.getText()));
            System.out.println(monto_final);
            int total_ventas = cjDao.totalVentas(Integer.parseInt(views.txtIDUsuarioRol.getText()));
            System.out.println(total_ventas);
            AperturaCierre apert = new AperturaCierre();
            
            LocalDateTime now = LocalDateTime.now();
            String horaActual = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
            apert.setFecha_cierre(horaActual);

            apert.setMonto_final(monto_final);
            apert.setTotal_ventas(total_ventas);
            apert.setId_usuario(Integer.parseInt(views.txtIDUsuarioRol.getText()));
            if (cjDao.cerrarCaja(apert)) {
                limpiarTable();
                listarAperturas();
                JOptionPane.showMessageDialog(null, "Caja Cerrada", "Cerrar Caja", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al cerrar la caja", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void nuevoApertura() {
        views.txtMontoInicial.setText("");
        views.txtBuscarApertura.setText("");
    }

    public void listarAperturas() {
//        Tables color = new Tables();
//        views.tableApertura.setDefaultRenderer(views.tableApertura.getColumnClass(0), color);

        List<AperturaCierre> lista = cjDao.ListaAperturas(views.txtBuscarApertura.getText());
        modelo = (DefaultTableModel) views.tableApertura.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getFecha_apertura();
            ob[1] = lista.get(i).getFecha_cierre();
            ob[2] = lista.get(i).getMonto_inicial();
            ob[3] = lista.get(i).getMonto_final();
            ob[4] = lista.get(i).getTotal_ventas();
            ob[5] = lista.get(i).getNom_usuario();
            modelo.addRow(ob);
        }

        views.tableApertura.setModel(modelo);
        JTableHeader header = views.tableApertura.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableApertura.getColumnCount(); i++) {
            views.tableApertura.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void listarVentas() {
        List<Ventas> lista = venDao.ListaVentas(views.txtBuscarVenta.getText());
        modelo = (DefaultTableModel) views.tableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNomCliente();
            ob[2] = lista.get(i).getTotal();
            ob[3] = lista.get(i).getFecha();
            modelo.addRow(ob);
        }
        views.tableVentas.setModel(modelo);
        JTableHeader header = views.tableVentas.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableVentas.getColumnCount(); i++) {
            views.tableVentas.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void listarCompras() {
        List<Compras> lista = compDao.ListaCompras(views.txtBuscarCompra.getText());
        modelo = (DefaultTableModel) views.tableCompras.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNomProveedor();
            ob[2] = lista.get(i).getTotal();
            ob[3] = lista.get(i).getFecha();
            modelo.addRow(ob);
        }
        views.tableCompras.setModel(modelo);
        JTableHeader header = views.tableCompras.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableCompras.getColumnCount(); i++) {
            views.tableCompras.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }
}
