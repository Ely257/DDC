package Controllers;

import Models.Combo;
import Models.Medidas;
import Models.MedidasDao;
import Models.Productos;
import Models.ProductosDao;
import Models.Tables;
import Models.VentasDao;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ProductosControllers implements ActionListener, MouseListener, KeyListener {

    MedidasDao medDao = new MedidasDao();

    private Productos pro;
    private ProductosDao proDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp;
    DefaultTableModel tmpCompras;
    
    private int filaTMPVentas;
    private int filaTMPCompras;

    public ProductosControllers(Productos pro, ProductosDao proDao, PanelAdmin views) {
        this.pro = pro;
        this.proDao = proDao;
        this.views = views;
        this.views.btnAbrirModal.addActionListener(this);
        this.views.btnRegistrarPro.addActionListener(this);
        this.views.btnModificarPro.addActionListener(this);
        this.views.btnNuevoPro.addActionListener(this);
        this.views.JMenuEliminarPro.addActionListener(this);
        this.views.JMenuReingresarPro.addActionListener(this);
        this.views.tableProductos.addMouseListener(this);
        this.views.tableNuevaVenta.addMouseListener(this);
        this.views.tableNuevaCompra.addMouseListener(this);
        this.views.labelProductos.addMouseListener(this);
        this.views.txtCodNC.addKeyListener(this);
        this.views.txtCantNC.addKeyListener(this);
        this.views.txtPagarConNC.addKeyListener(this);
        this.views.btnGenerarCompra.addActionListener(this);
        this.views.labelNuevaCompra.addMouseListener(this);
        this.views.labelNuevaVenta.addMouseListener(this);
        this.views.txtCodNV.addKeyListener(this);
        this.views.txtCantNV.addKeyListener(this);
        this.views.btnGenerarVenta.addActionListener(this);
        this.views.txtBuscarProducto.addKeyListener(this);
        this.views.txtPagarConNV.addKeyListener(this);
        this.views.labelEstadisticas.addMouseListener(this);
        this.views.btnLimpiarTablaTemporal.addActionListener(this);
        this.views.btnLimpiarTablaTemporalCompras.addActionListener(this);
        
        listarProductos();
        llenarCodigo();
        llenarDescripciones();
        
        AutoCompleteDecorator.decorate(views.cbxProveedorNC);
        AutoCompleteDecorator.decorate(views.txtCodNV);
        AutoCompleteDecorator.decorate(views.txtCodNC);
        AutoCompleteDecorator.decorate(views.txtProductoNV);
        AutoCompleteDecorator.decorate(views.txtProductoNC);
        
        //Cargar Evento de ENTER en ComboBoxes editables
        views.txtCodNV.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String cod = views.txtCodNV.getEditor().getItem().toString();
                    buscarProducto(cod, views.txtIDNV, views.txtProductoNV, views.txtPrecioNV, views.txtCantNV, 1);
                }
            }
        });
        
        views.txtCodNC.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String cod = views.txtCodNC.getEditor().getItem().toString();
                    buscarProducto(cod, views.txtIDNC, views.txtProductoNC, views.txtPrecioNC, views.txtCantNC, 0);
                }
            }
        });
        
        views.txtProductoNV.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String descripcion = views.txtProductoNV.getEditor().getItem().toString();
                    buscarProductoPorDescripcion(views.txtIDNV, descripcion, views.txtCodNV, views.txtPrecioNV, views.txtCantNV, 1);
                }
            }
        });
        
        views.txtProductoNC.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String descripcion = views.txtProductoNC.getEditor().getItem().toString();
                    buscarProductoPorDescripcion(views.txtIDNC, descripcion, views.txtCodNC, views.txtPrecioNC, views.txtCantNC, 0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarPro) {
            if (views.txtCodigoPro.getText().equals("")
                    || views.txtDescripcionPro.getText().equals("")
                    || views.txtPrecioCompraPro.getText().equals("")
                    || views.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                pro.setCodigo(views.txtCodigoPro.getText());
                pro.setDescripcion(views.txtDescripcionPro.getText());
                pro.setPrecio_compra(Double.parseDouble(views.txtPrecioCompraPro.getText()));
                pro.setPrecio_venta(Double.parseDouble(views.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) views.cbxProveedorPro.getSelectedItem();
                Combo itemC = (Combo) views.cbxCatPro.getSelectedItem();
                Combo itemM = (Combo) views.cbxMedidaPro.getSelectedItem();
                pro.setId_proveedor(itemP.getId());
                pro.setId_categoria(itemC.getId());
                pro.setId_medida(itemM.getId());

                String respuesta = proDao.registrar(pro);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Código del producto debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarProductos();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Producto Registrado", "Registrar Producto", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        } else if (e.getSource() == views.btnModificarPro) {
            if (views.txtCodigoPro.getText().equals("")
                    || views.txtDescripcionPro.getText().equals("")
                    || views.txtPrecioCompraPro.getText().equals("")
                    || views.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                pro.setCodigo(views.txtCodigoPro.getText());
                pro.setDescripcion(views.txtDescripcionPro.getText());
                pro.setPrecio_compra(Double.parseDouble(views.txtPrecioCompraPro.getText()));
                pro.setPrecio_venta(Double.parseDouble(views.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) views.cbxProveedorPro.getSelectedItem();
                Combo itemC = (Combo) views.cbxCatPro.getSelectedItem();
                Combo itemM = (Combo) views.cbxMedidaPro.getSelectedItem();
                pro.setId_proveedor(itemP.getId());
                pro.setId_categoria(itemC.getId());
                pro.setId_medida(itemM.getId());
                pro.setId(Integer.parseInt(views.txtIDpro.getText()));

                String respuesta = proDao.modificar(pro);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Código del producto debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Modificado":
                        limpiarTable();
                        listarProductos();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Producto Modificado", "Modificar Producto", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        } else if (e.getSource() == views.JMenuEliminarPro) {
            if (views.txtIDpro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDpro.getText());
                if (proDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarProductos();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Producto eliminado", "Eliminar Producto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar producto", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.JMenuReingresarPro) {
            if (views.txtIDpro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(views.txtIDpro.getText());
                if (proDao.accion("Activo", id)) {
                    limpiarTable();
                    listarProductos();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Producto reingresado con éxito", "Reingresar Producto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reingresar producto", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.btnGenerarCompra) {
            insertaCompra();
        } else if (e.getSource() == views.btnGenerarVenta) {
            VentasDao venDao = new VentasDao();
            String respuesta = venDao.verificarCaja(Integer.parseInt(views.txtIDUsuarioRol.getText()));
            if (respuesta.equals("Existe")) {
                try {
                    insertaVenta();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (respuesta.equals("No")) {
                JOptionPane.showMessageDialog(null, "La Caja está cerrada", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error Fatal", "ERROR FATAL", JOptionPane.ERROR_MESSAGE);
            }
        } else if(e.getSource() == views.btnLimpiarTablaTemporal){
            if(tmp == null || tmp.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "No hay productos que quitar", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else if(filaTMPVentas == -1){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para quitar el producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                tmp.removeRow(filaTMPVentas);
                filaTMPVentas = -1;
            }
        }else if(e.getSource() == views.btnLimpiarTablaTemporalCompras){
            if(tmpCompras == null || tmpCompras.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "No hay productos que quitar", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else if(filaTMPCompras == -1){
                JOptionPane.showMessageDialog(null, "Seleccione una fila para quitar el producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                tmpCompras.removeRow(filaTMPCompras);
                filaTMPCompras = -1;
            }
        } else if(e.getSource() == views.btnAbrirModal){
            views.ventanaRegCliente.setModal(true);
            views.ventanaRegCliente.setLocationRelativeTo(views);
            views.ventanaRegCliente.setVisible(true);
        }else{
            limpiar();
        }
    }

    public void listarProductos() {
        Tables color = new Tables();
        views.tableProductos.setDefaultRenderer(views.tableProductos.getColumnClass(0), color);

        List<Productos> lista = proDao.ListaProductos(views.txtBuscarProducto.getText());
        modelo = (DefaultTableModel) views.tableProductos.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getCodigo();
            ob[2] = lista.get(i).getDescripcion();
            ob[3] = lista.get(i).getPrecio_venta();
            ob[4] = lista.get(i).getCantidad();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.tableProductos.setModel(modelo);
        JTableHeader header = views.tableProductos.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);

        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableProductos.getColumnCount(); i++) {
            views.tableProductos.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void limpiarTableDetalle() {
        for (int i = 0; i < tmp.getRowCount(); i++) {
            tmp.removeRow(i);
            i = i - 1;
        }
    }
    
    public void limpiarTableDetalleCompras() {
        for (int i = 0; i < tmpCompras.getRowCount(); i++) {
            tmpCompras.removeRow(i);
            i = i - 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.tableProductos) {
            
            int fila = views.tableProductos.rowAtPoint(e.getPoint());
            views.txtIDpro.setText(views.tableProductos.getValueAt(fila, 0).toString());
            pro = proDao.buscarPro(Integer.parseInt(views.txtIDpro.getText()));
            views.txtCodigoPro.setText(pro.getCodigo());
            views.txtDescripcionPro.setText(pro.getDescripcion());
            views.txtPrecioCompraPro.setText("" + pro.getPrecio_compra());
            views.txtPrecioVentaPro.setText("" + pro.getPrecio_venta());
            views.cbxProveedorPro.setSelectedItem(new Combo(pro.getId_proveedor(), pro.getProveedor()));
            views.cbxMedidaPro.setSelectedItem(new Combo(pro.getId_medida(), pro.getMedida()));
            views.cbxCatPro.setSelectedItem(new Combo(pro.getId_categoria(), pro.getCat()));
            
        }else if(e.getSource() == views.tableNuevaVenta){
            
            this.filaTMPVentas = views.tableNuevaVenta.rowAtPoint(e.getPoint());
            
        }else if(e.getSource() == views.tableNuevaCompra){
            
            this.filaTMPCompras = views.tableNuevaCompra.rowAtPoint(e.getPoint());
            
        } else if (e.getSource() == views.labelProductos) {
            views.jTabbedPane1.setSelectedIndex(0);
            limpiarTable();
            listarProductos();
        } else if (e.getSource() == views.labelNuevaCompra) {
            views.jTabbedPane1.setSelectedIndex(10);
        } else if (e.getSource() == views.labelNuevaVenta) {
            views.jTabbedPane1.setSelectedIndex(6);
        } else if (e.getSource() == views.labelEstadisticas) {
            views.jTabbedPane1.setSelectedIndex(14);
            int total = proDao.contarProductos();
            views.labeltotalproductos.setText("" + total);
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

    public void limpiar() {
        views.txtCodigoPro.setText("");
        views.txtDescripcionPro.setText("");
        views.txtPrecioCompraPro.setText("");
        views.txtPrecioVentaPro.setText("");
        views.txtIDpro.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {      
        if (e.getSource() == views.txtCantNC) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(views.txtCantNC.getText().matches("\\d+")){
                    if(views.txtPrecioNC.getText().equals("")){
                        String descripcion = views.txtProductoNC.getEditor().getItem().toString();
                        buscarProductoPorDescripcion(views.txtIDNC, descripcion, views.txtCodNC, views.txtPrecioNC, views.txtCantNC, 0);
                    }
                    int cant = Integer.parseInt(views.txtCantNC.getText());
                    String desc = views.txtProductoNC.getSelectedItem().toString();
                    double precio = Double.parseDouble(views.txtPrecioNC.getText());
                    int id = Integer.parseInt(views.txtIDNC.getText());

                    agregarTemp(cant, desc, precio, id, views.tableNuevaCompra, views.txtCodNC);
                    limpiarCompras();
                    calcularTotal(views.tableNuevaCompra, views.labelTotalCompra);
                    double totalBS = Double.parseDouble(views.labelTotalCompra.getText());
                    calcularTotalDolares(totalBS, views.labelTotalCompraDolares);
                }else{
                    JOptionPane.showMessageDialog(null, "Ingrese un número!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == views.txtCantNV) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txtCantNV.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la cantidad", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if(views.txtCantNV.getText().matches("\\d+")){
                        if(views.txtPrecioNV.getText().equals("")){
                            String descripcion = views.txtProductoNV.getEditor().getItem().toString();
                            buscarProductoPorDescripcion(views.txtIDNV, descripcion, views.txtCodNV, views.txtPrecioNV, views.txtCantNV, 1);
                        }
                        int cant = Integer.parseInt(views.txtCantNV.getText());
                        int stock = Integer.parseInt(views.txtStockNV.getText());
                        if (cant > stock) {
                            JOptionPane.showMessageDialog(null, "Stock no disponible", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String desc = views.txtProductoNV.getSelectedItem().toString();
                            double precio = Double.parseDouble(views.txtPrecioNV.getText());
                            int id = Integer.parseInt(views.txtIDNV.getText());

                            agregarTemp(cant, desc, precio, id, views.tableNuevaVenta, views.txtCodNV);
                            limpiarVentas();
                            calcularTotal(views.tableNuevaVenta, views.labelTotalPagar);
                            double totalBS = Double.parseDouble(views.labelTotalPagar.getText());
                            calcularTotalDolares(totalBS, views.labelTotalPagarDolares);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Ingrese un número!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txtCantNC) {
            int cantidad;
            double precio;
            if(!views.txtPrecioNC.getText().equals("")){
                if (views.txtCantNC.getText().equals("")) {
                    cantidad = 1;
                    precio = Double.parseDouble(views.txtPrecioNC.getText());
                    views.txtTotalNC.setText("" + precio);
                } else {
                    if(views.txtCantNC.getText().matches("\\d+")){
                        if(!views.txtTotalNC.getText().equals("")){
                            cantidad = Integer.parseInt(views.txtCantNC.getText());
                            precio = Double.parseDouble(views.txtPrecioNC.getText());
                            views.txtTotalNC.setText("" + cantidad * precio);    
                        }
                    }
                }            
            }
        }else if(e.getSource() == views.txtCantNV){
            int cantidad;
            double precio;
            if(!views.txtPrecioNV.getText().equals("")){
                if (views.txtCantNV.getText().equals("")) {
                    cantidad = 1;
                    precio = Double.parseDouble(views.txtPrecioNV.getText());
                    views.txtTotalNV.setText("" + precio);
                } else {
                    if(views.txtCantNV.getText().matches("\\d+")){
                        if(!views.txtTotalNV.getText().equals("")){
                            cantidad = Integer.parseInt(views.txtCantNV.getText());
                            precio = Double.parseDouble(views.txtPrecioNV.getText());
                            views.txtTotalNV.setText("" + cantidad * precio);       
                        }
                    }
                }             
            }
        } else if (e.getSource() == views.txtPagarConNC) {
            int pagar;
            if(!views.labelTotalCompra.getText().equals("-----------")){
                if (views.txtPagarConNC.getText().equals("")) {
                    views.txtVueltoNC.setText("");
                } else {
                    pagar = Integer.parseInt(views.txtPagarConNC.getText());
                    double total = Double.parseDouble(views.labelTotalCompra.getText());
                    views.txtVueltoNC.setText("" + (pagar - total));
                }
            } 
        } else if (e.getSource() == views.txtBuscarProducto) {
            views.jTabbedPane1.setSelectedIndex(0);
            limpiarTable();
            listarProductos();
        } else if (e.getSource() == views.txtPagarConNV) {
            int pagar1;
            if(!views.labelTotalPagar.getText().equals("-----------")){
                if (views.txtPagarConNV.getText().equals("")) {
                    views.txtVueltoNV.setText("");
                } else {
                    pagar1 = Integer.parseInt(views.txtPagarConNV.getText());
                    double total1 = Double.parseDouble(views.labelTotalPagar.getText());
                    views.txtVueltoNV.setText("" + (pagar1 - total1));
                }
            }
        }
    }

    private void limpiarCompras() {
        views.txtIDNC.setText("");
        views.txtCantNC.setText("");
        views.txtPrecioNC.setText("");
        views.txtTotalNC.setText("");
    }

    private void limpiarVentas() {
        views.txtIDNV.setText("");
        views.txtCantNV.setText("");
        views.txtPrecioNV.setText("");
        views.txtTotalNV.setText("");
        views.txtStockNV.setText("");
    }

    private void calcularTotal(JTable tabla, JLabel totalPagar) {
        double total = 0.00;
        int numfila = tabla.getRowCount();
        for (int i = 0; i < numfila; i++) {
            total = total + Double.parseDouble(String.valueOf(tabla.getValueAt(i, 4)));
        }
        totalPagar.setText("" + total);
    }
    
    private void calcularTotalDolares(double totalBS, JLabel totalPagarDolares){
        String total = views.labelDolar.getText().replace(",", "."); 
        double totalDolarParseado = Double.parseDouble(total);
        
        double totalEnDolares = totalBS / totalDolarParseado;
        String formateado = String.format("%.2f", totalEnDolares);
        
        totalPagarDolares.setText("" + formateado);
    }

    private void insertaCompra() {
        Combo id_p = (Combo) views.cbxProveedorNC.getSelectedItem();
        int id_proveedor = id_p.getId();
        String total = views.labelTotalCompra.getText();
        
        if(views.tableNuevaCompra.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Primero seleccione los productos", "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            if (proDao.registrarCompra(id_proveedor, total)) {
                int id_compra = proDao.getUltimoId("compras");
                for (int i = 0; i < views.tableNuevaCompra.getRowCount(); i++) {

                    double precio = Double.parseDouble(views.tableNuevaCompra.getValueAt(i, 3).toString());
                    int cantidad = Integer.parseInt(views.tableNuevaCompra.getValueAt(i, 2).toString());
                    int id = Integer.parseInt(views.tableNuevaCompra.getValueAt(i, 0).toString());
                    double sub_total = precio * cantidad;

                    proDao.registrarCompraDetalle(id_compra, id, precio, cantidad, sub_total);
                    pro = proDao.buscarId(id);
                    int stockActual = pro.getCantidad() + cantidad;
                    proDao.actualizarStock(stockActual, id);
                }
                limpiarTableDetalleCompras();
                views.labelTotalCompra.setText("-----------");
                views.labelTotalCompraDolares.setText("-----------");
                JOptionPane.showMessageDialog(null, "Compra Generada", "Generar Compra", JOptionPane.INFORMATION_MESSAGE);
                proDao.generarReporte(id_compra);
            }
        }
    }

    //Agregar productos para la compra y venta
    private void agregarTemp(int cant, String desc, double precio, int id, JTable tabla, JComboBox campo) {
        if (cant > 0) {
            ArrayList lista = new ArrayList();
            int item = 1;
            lista.add(item);
            lista.add(id);
            lista.add(desc);
            lista.add(cant);
            lista.add(precio);
            lista.add(cant * precio);
            Object[] ob = new Object[5];
            ob[0] = lista.get(1);
            ob[1] = lista.get(2);
            ob[2] = lista.get(3);
            ob[3] = lista.get(4);
            ob[4] = lista.get(5);
            if(tabla == views.tableNuevaVenta){
                tmp = (DefaultTableModel) tabla.getModel();
                tmp.addRow(ob);
                tabla.setModel(tmp);
            }else if(tabla == views.tableNuevaCompra){
                tmpCompras = (DefaultTableModel) tabla.getModel();
                tmpCompras.addRow(ob);
                tabla.setModel(tmpCompras);
            }
          
            //Tabla Ventas
            JTableHeader header = views.tableNuevaVenta.getTableHeader();
            header.setOpaque(false);
            header.setBackground(new Color(43, 147, 72));
            header.setForeground(Color.white);

            //Tabla Compras
            JTableHeader header2 = views.tableNuevaCompra.getTableHeader();
            header2.setOpaque(false);
            header2.setBackground(new Color(43, 147, 72));
            header2.setForeground(Color.white);

            DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
            centroRender.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                tabla.getColumnModel().getColumn(i).setCellRenderer(centroRender);
            }

            campo.requestFocus();
        }
    }

    //Buscar Productos para la compra y venta
    private void buscarProducto(String cod, JTextField id, JComboBox prod, JTextField precio, JTextField cant, int accion) {
        pro = proDao.buscarCodigo(cod);
        if (pro.getId() > 0) {
            id.setText("" + pro.getId());
            prod.setSelectedItem(pro.getDescripcion());
            if (accion == 0) {
                precio.setText("" + pro.getPrecio_compra());
            } else {
                precio.setText("" + pro.getPrecio_venta());
                views.txtStockNV.setText("" + pro.getCantidad());
            }
            cant.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "El producto no existe o está inactivo", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarProductoPorDescripcion(JTextField id, String desc, JComboBox cod, JTextField precio, JTextField cant, int accion) {
        pro = proDao.buscarDescripcion(desc);
        if (pro.getId() > 0) {
            id.setText("" + pro.getId());
            cod.setSelectedItem(pro.getCodigo());
            if (accion == 0) {
                precio.setText("" + pro.getPrecio_compra());
            } else {
                precio.setText("" + pro.getPrecio_venta());
                views.txtStockNV.setText("" + pro.getCantidad());
            }
            cant.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "El producto no existe o está inactivo", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Insertar venta
    private void insertaVenta() {
        Combo id_cliente = (Combo) views.cbxClienteVentas.getSelectedItem();
        int id_cli = id_cliente.getId();
        int id_user = Integer.parseInt(views.txtIDUsuarioRol.getText());
        String total = views.labelTotalPagar.getText();
        
        if(views.tableNuevaVenta.getRowCount() == 0){
            JOptionPane.showMessageDialog(null, "Primero seleccione los productos", "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            if (proDao.registrarVenta(id_cli, total, id_user)) {
                int id = proDao.getUltimoId("ventas");
                for (int i = 0; i < views.tableNuevaVenta.getRowCount(); i++) {

                    double precio = Double.parseDouble(views.tableNuevaVenta.getValueAt(i, 3).toString());
                    int cantidad = Integer.parseInt(views.tableNuevaVenta.getValueAt(i, 2).toString());
                    int id_producto = Integer.parseInt(views.tableNuevaVenta.getValueAt(i, 0).toString());
                    double sub_total = precio * cantidad;

                    proDao.registrarVentaDetalle(id, id_producto, precio, cantidad, sub_total);
                    pro = proDao.buscarId(id_producto);
                    int stockActual = pro.getCantidad() - cantidad;
                    proDao.actualizarStock(stockActual, id_producto);
                }
                limpiarTableDetalle();
                views.labelTotalPagar.setText("-----------");
                views.labelTotalPagarDolares.setText("-----------");
                JOptionPane.showMessageDialog(null, "Venta Generada", "Generar Venta", JOptionPane.INFORMATION_MESSAGE);
                proDao.generarReporteVenta(id);
            }
        }
    }

    private void llenarCodigo() {
        List<Productos> lista = proDao.ListaProductos(views.txtBuscarProducto.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String codigo = lista.get(i).getCodigo();
            views.txtCodNV.addItem(new Combo(id, codigo));
            views.txtCodNC.addItem(new Combo(id, codigo));
        }
    }
    
    private void llenarDescripciones() {
        List<Productos> lista = proDao.ListaProductos(views.txtBuscarProducto.getText());
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String descripcion = lista.get(i).getDescripcion();
            views.txtProductoNV.addItem(new Combo(id, descripcion));
            views.txtProductoNC.addItem(new Combo(id, descripcion));
        }
    }
}
