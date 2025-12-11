package Views;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Controllers.CajasControllers;
import Controllers.CategoriasControllers;
import Controllers.ClientesControllers;
import Controllers.ComprasControllers;
import Controllers.ConfigControllers;
import Controllers.MedidasControllers;
import Controllers.ProductosControllers;
import Controllers.ProveedoresControllers;
import Controllers.UsuariosControllers;
import Controllers.VentasControllers;
import Models.Cajas;
import Models.CajasDao;
import Models.Categorias;
import Models.CategoriasDao;
import Models.Clientes;
import Models.ClientesDao;
import Models.Compras;
import Models.ComprasDao;
import Models.Configuracion;
import Models.Degradado;
import Models.DegradadoOverlay;
import Models.Medidas;
import Models.MedidasDao;
import Models.Productos;
import Models.ProductosDao;
import Models.Proveedores;
import Models.ProveedoresDao;
import Models.Usuarios;
import Models.UsuariosDao;
import Models.Ventas;
import Models.VentasDao;

import java.awt.*;
import javax.swing.*;
        
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.concurrent.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONObject;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class PanelAdmin extends javax.swing.JFrame {
    
    private ArrayList<JPanel> paneles = new ArrayList<>();
    private ArrayList<Degradado> degradados = new ArrayList<>();
    
    private ArrayList<JPanel> panelesCampos = new ArrayList<>();
        
    private boolean modoOscuro = true;
    
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;

    Usuarios us = new Usuarios();
    UsuariosDao usDao = new UsuariosDao();

    Clientes cl = new Clientes();
    ClientesDao clDao = new ClientesDao();

    Proveedores prov = new Proveedores();
    ProveedoresDao provDao = new ProveedoresDao();

    Categorias cat = new Categorias();
    CategoriasDao catDao = new CategoriasDao();

    Medidas med = new Medidas();
    MedidasDao medDao = new MedidasDao();

    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();

    Configuracion cof = new Configuracion();

    Compras comp = new Compras();
    ComprasDao compDao = new ComprasDao();

    Cajas cj = new Cajas();
    CajasDao cjDao = new CajasDao();

    Ventas ven = new Ventas();
    VentasDao venDao = new VentasDao();

    public PanelAdmin() {
        
    }

    public PanelAdmin(int id, String nombre, String rol, String estado, boolean modoOscuroActivo) {
        
        this.modoOscuro = modoOscuroActivo;
        
        if (modoOscuro) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("TabbedPane.tabSelectionHeight", 0);
        UIManager.put("TabbedPane.contentSeparatorHeight", 0);

        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Img/ddc1.png")).getImage());
        this.setLocationRelativeTo(null);
        ConfigControllers config = new ConfigControllers(cof, usDao, this, modoOscuro);
        CajasControllers cajas = new CajasControllers(cj, cjDao, this);
        UsuariosControllers users = new UsuariosControllers(us, usDao, this);
        ClientesControllers cliente = new ClientesControllers(cl, clDao, this);
        ProveedoresControllers proveedores = new ProveedoresControllers(prov, provDao, this);
        CategoriasControllers categorias = new CategoriasControllers(cat, catDao, this);
        MedidasControllers medidas = new MedidasControllers(med, medDao, this);
        ComprasControllers compras = new ComprasControllers(comp, compDao, this);
        VentasControllers ventas = new VentasControllers(ven, venDao, this);
        ProductosControllers productos = new ProductosControllers(pro, proDao, this); 
        
        btnModoOscuro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoOscuro = !modoOscuro;

                if (modoOscuro) {
                    FlatDarkLaf.setup();
                } else {
                    FlatLightLaf.setup();
                }

                FlatLaf.updateUI();

                aplicarModoOscuro();
                
                boolean nuevoModoOscuro = !config.isModoOscuroActivo();
                config.setModoOscuroActivo(nuevoModoOscuro);
            }
        });

        // Logos 
        ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondo1 = new ImageIcon(imagen1.getImage().getScaledInstance(labelEstadisticas.getWidth(), labelEstadisticas.getHeight(), Image.SCALE_DEFAULT));
        labelEstadisticas.setIcon(fondo1);
        this.repaint();

        ImageIcon imagen2 = new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondo2 = new ImageIcon(imagen2.getImage().getScaledInstance(logoCambiarContra.getWidth(), logoCambiarContra.getHeight(), Image.SCALE_DEFAULT));
        logoCambiarContra.setIcon(fondo2);
        logoCambiarContraGeneral.setIcon(fondo2);
        this.repaint();
        
        //LLenar Arrays de Paneles
        paneles.add(jPanel1);
        paneles.add(jPanel2);
        paneles.add(jPanel3);
        paneles.add(jPanel4);
        paneles.add(jPanel5);
        paneles.add(jPanel6);
        paneles.add(jPanel7);
        paneles.add(jPanel8);
        paneles.add(jPanel9);
        paneles.add(jPanel10);
        paneles.add(jPanel11);
        paneles.add(jPanel12);
        paneles.add(jPanel13);
        paneles.add(jPanel14);
        paneles.add(jPanel16);
        paneles.add(jPanel17);
        paneles.add(jPanel26);
        paneles.add(jPanel27);
        paneles.add(jPanel33);
        
        for (int i = 0; i < paneles.size(); i++) {
            degradados.add(null);
        }
        
        panelesCampos.add(jPanel15);
        panelesCampos.add(jPanel18);
        panelesCampos.add(jPanel19);
        panelesCampos.add(jPanel20);
        panelesCampos.add(jPanel21);
        panelesCampos.add(jPanel22);
        panelesCampos.add(jPanel23);
        panelesCampos.add(jPanel24);
        panelesCampos.add(jPanel25);
        panelesCampos.add(jPanel28);
        panelesCampos.add(jPanel29);
        panelesCampos.add(jPanel30);
        panelesCampos.add(jPanel31);
        panelesCampos.add(jPanel32);
        panelesCampos.add(jPanel34);

        // Filtrado de usuarios
        txtIDUsuarioRol.setText("" + id);
        btnUsers1.setText(nombre);
        labelRolUsuarioConectado.setText(rol);

        txtIDUsuarioRol.setEnabled(false);
        txtPrecioNV.setEnabled(false);
        txtStockNV.setEnabled(false);
        txtPrecioNC.setEnabled(false);

        txtIDEmpresa.setEnabled(false);
        txtIDpro.setEnabled(false);
        txtIDCli.setEnabled(false);
        txtIDprov.setEnabled(false);
        txtIDuser.setEnabled(false);
        txtIDcat.setEnabled(false);
        txtIDmed.setEnabled(false);
        txtIDNV.setEnabled(false);
        txtIDNC.setEnabled(false);
        txtIDVentas.setEnabled(false);
        txtIDCompra.setEnabled(false);
        txtIDCaja.setEnabled(false);
        txtIDUserGeneral.setEnabled(false);
        txtTotalNV.setEnabled(false);
        txtTotalNC.setEnabled(false);
        txtVueltoNV.setEnabled(false);
        txtVueltoNC.setEnabled(false);

        if (rol.equals("Empleado(a)")) {
            labelNuevaCompra.setEnabled(false);
            labelNuevaCompra.setVisible(false);
            panelNuevaCompra.setVisible(false);

            labelProveedores.setEnabled(false);
            labelProveedores.setVisible(false);
            panelProveedores.setVisible(false);

            labelMedidas.setEnabled(false);
            labelMedidas.setVisible(false);
            panelMedidas.setVisible(false);

            labelCategorias.setEnabled(false);
            labelCategorias.setVisible(false);
            panelCategorias.setVisible(false);

            labelConfig.setEnabled(false);
            labelConfig.setVisible(false);
            panelConfig.setVisible(false);

            labelCaja.setEnabled(false);
            labelCaja.setVisible(false);
            panelCaja.setVisible(false);

            labelUsers.setEnabled(false);
            labelUsers.setVisible(false);
            panelUsers.setVisible(false);

            labelNuevaCompra.removeMouseListener(labelNuevaCompra.getMouseListeners()[1]);
            labelProveedores.removeMouseListener(labelProveedores.getMouseListeners()[1]);
            labelMedidas.removeMouseListener(labelMedidas.getMouseListeners()[1]);
            labelCategorias.removeMouseListener(labelCategorias.getMouseListeners()[1]);
            labelConfig.removeMouseListener(labelConfig.getMouseListeners()[0]);
            labelCaja.removeMouseListener(labelCaja.getMouseListeners()[1]);
            labelUsers.removeMouseListener(labelUsers.getMouseListeners()[1]);
        }

        jTabbedPane1.setSelectedIndex(6);
        jTabbedPane1.setEnabled(false);
        
        //Desactivar edicion en linea de tablas
        tableProductos.setDefaultEditor(Object.class, null);
        tableClientes.setDefaultEditor(Object.class, null);
        tableProveedor.setDefaultEditor(Object.class, null);
        tableMedida.setDefaultEditor(Object.class, null);
        tableCat.setDefaultEditor(Object.class, null);
        tableCaja.setDefaultEditor(Object.class, null);
        tableApertura.setDefaultEditor(Object.class, null);
        tableCompras.setDefaultEditor(Object.class, null);
        tableVentas.setDefaultEditor(Object.class, null);
        tableNuevaCompra.setDefaultEditor(Object.class, null);
        tableNuevaVenta.setDefaultEditor(Object.class, null);
        tableUsers.setDefaultEditor(Object.class, null);
        tableUsersGenerales.setDefaultEditor(Object.class, null);
        

        // Consulta del dolar BCV cada hora
        ScheduledExecutorService consulta = Executors.newScheduledThreadPool(1);

        Runnable consultaApi = () -> {
            try {
                URL url = new URL("https://api.dolarvzla.com/public/exchange-rate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    String responseString = response.toString();
                    JSONObject jsonObjectResponse = new JSONObject(responseString);
                    
                    JSONObject jsonObjectCurrent = jsonObjectResponse.getJSONObject("current");
                    JSONObject jsonObjectPrevious = jsonObjectResponse.getJSONObject("previous");

                    labelDolar.setText("" + String.format("%.2f", jsonObjectCurrent.getDouble("usd")));
                    labelDolarAnterior.setText("" + String.format("%.2f", jsonObjectPrevious.getDouble("usd")));

                    labelDolarVenta.setText("" + String.format("%.2f", jsonObjectCurrent.getDouble("usd")));
                    labelDolarVentaAnterior.setText("" + String.format("%.2f", jsonObjectPrevious.getDouble("usd")));
                    
                    Font emojiFont = new Font("Segoe UI Emoji", Font.BOLD, 18);
                    labelEstadoDolar.setFont(emojiFont);
                    labelEstadoDolarCompra.setFont(emojiFont);
                    
                    labelEstadoDolar.setText("Online \u2705");
                    labelEstadoDolarCompra.setText("Online \u2705");
                } else {
                    System.out.println("Error en la solicitud: " + responseCode);
                    
                    labelEstadoDolar.setText("Offline \u274C");
                    labelEstadoDolarCompra.setText("Offline \u274C");
                    labelEstadoDolar.setForeground(Color.RED);
                    labelEstadoDolarCompra.setForeground(Color.RED);
                }
                conn.disconnect();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Conexión Fallida con API DolarVzla", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        consulta.scheduleAtFixedRate(consultaApi, 0, 1, TimeUnit.HOURS);
        
        aplicarModoOscuro();
    }
    
//    private void inicializarComponentesRelativos() {
//        agregarDesdeComponente(jLabel80);
//        agregarDesdeComponente(jLabel48);
//        agregarDesdeComponente(jLabel49);
//        agregarDesdeComponente(txtBuscarProducto);
//        agregarDesdeComponente(labelRolUsuarioConectado);
//        agregarDesdeComponente(btnUsers1);
//        agregarDesdeComponente(txtIDUsuarioRol);
//        agregarDesdeComponente(btnModoOscuro);
//        agregarDesdeComponente(jLabel79);
//    }
//    
//    private void agregarDesdeComponente(JComponent comp) {
//        Rectangle r = comp.getBounds();
//        componentesRelativos.add(new ComponenteRelativo(comp, r.x, r.y, r.width, r.height));
//    }
//    
//    private void actualizarLayout() {
//        int anchoFrame = getWidth();
//        int altoFrame = getHeight();
//        int anchoPanel = (int)(anchoFrame * 1.0005);
//        int altoPanel = 140;
//
//        jPanel3.setBounds(200, 0, anchoPanel, altoPanel);
//
//        int panelOriginalWidth = 1150;
//        int panelOriginalHeight = 140;
//
//        for (ComponenteRelativo cr : componentesRelativos) {
//            // Posición proporcional
//            int xNuevo = (int)((double)cr.xOriginal / panelOriginalWidth * anchoPanel);
//            int yNuevo = (int)((double)cr.yOriginal / panelOriginalHeight * altoPanel);
//
//            // Tamaño proporcional
//            int widthNuevo = (int)((double)cr.widthOriginal / panelOriginalWidth * anchoPanel);
//            int heightNuevo = (int)((double)cr.heightOriginal / panelOriginalHeight * altoPanel);
//
//            cr.componente.setBounds(xNuevo, yNuevo, widthNuevo, heightNuevo);
//        }
//    }
    
    public void aplicarModoOscuro(){
        //Colores
        Color grisClaro = new Color(60, 63, 65);
        Color transparente = new Color(0, 0, 0, 0);
        Color negroMuyMuyFull = new Color(20, 20, 20);
        Color negroBastanteFull = new Color(30, 30, 30);
        Color negroCasiFull = new Color(40, 40, 40);
        Color grisMenosDeCarbon = new Color(120, 120, 120);
        
        Color verde = new Color(43, 147, 72);
        Color panelClaro = new Color(250, 251, 253);

        Color blancoGrisaceo = new Color(230, 230, 230);
        Color grisCarbon = new Color(60, 60, 60);
        
        Color panelGrisModoClaro = new Color(235, 235, 235);
        
        //Pintar Paneles principales
        for (int i = 0; i < paneles.size(); i++) {
            JPanel panel = paneles.get(i);
            Degradado degradado = degradados.get(i);
            
            panel.setLayout(new BorderLayout());

            if (modoOscuro) {
                if (degradado == null) {
                    Color color1 = (i == 0) ? negroBastanteFull 
                                : (i == 1 || i == 2) ? negroMuyMuyFull 
                                : negroCasiFull;

                    Color color2 = (i == 0) ? negroCasiFull 
                                : (i == 1 || i == 2) ? negroBastanteFull 
                                : grisMenosDeCarbon;

                    degradado = new Degradado(color1, color2, true);
                    panel.setLayout(new BorderLayout());
                    panel.add(degradado, BorderLayout.CENTER);
                    degradados.set(i, degradado);
                }
            } else {
                if (degradado != null) {
                    panel.remove(degradado);
                    degradados.set(i, null);
                }
                Color bg = (i == 0 || i == 1 || i == 2) ? panelClaro : panelGrisModoClaro;
                panel.setBackground(bg);
            }
        }
        //Pintar Paneles Con titled border
        for (int i = 0; i < panelesCampos.size(); i++) {
            JPanel panel = panelesCampos.get(i);
            
            panel.setLayout(new BorderLayout());

            if (modoOscuro) {
                DegradadoOverlay.aplicarDegradado(panel, grisMenosDeCarbon, grisClaro, true);
            } else {
                panel.setOpaque(true);
                panel.setBackground(panelClaro);
            }
        }
        
        //Bordes
        Color colorBordeDinamico = modoOscuro ? grisClaro : transparente;
        jTabbedPane1.setBorder(new MatteBorder(0, 2, 0, 0, colorBordeDinamico));
        jPanel29.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
        jPanel30.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLUE));
        jPanel31.setBorder(new MatteBorder(1, 1, 1, 1, Color.GREEN));
        jPanel32.setBorder(new MatteBorder(1, 1, 1, 1, Color.YELLOW));
        
        //Encabezados de Tablas
        aplicarEstiloHeaderOscuro(tableProductos);
        aplicarEstiloHeaderOscuro(tableClientes);
        aplicarEstiloHeaderOscuro(tableProveedor);
        aplicarEstiloHeaderOscuro(tableMedida);
        aplicarEstiloHeaderOscuro(tableCat);
        aplicarEstiloHeaderOscuro(tableNuevaVenta);
        aplicarEstiloHeaderOscuro(tableNuevaCompra);
        aplicarEstiloHeaderOscuro(tableCompras);
        aplicarEstiloHeaderOscuro(tableVentas);
        aplicarEstiloHeaderOscuro(tableUsers);
        aplicarEstiloHeaderOscuro(tableCaja);
        aplicarEstiloHeaderOscuro(tableApertura);
        aplicarEstiloHeaderOscuro(tableUsersGenerales);
        
        panelNuevaVenta.setBackground(transparente);
        panelNuevaCompra.setBackground(transparente);
        panelProductos.setBackground(transparente);
        panelClientes.setBackground(transparente);
        panelProveedores.setBackground(transparente);
        panelMedidas.setBackground(transparente);
        panelCategorias.setBackground(transparente);
        panelConfig.setBackground(transparente);
        panelUsers.setBackground(transparente);
        panelCaja.setBackground(transparente);
        
        Color letraModoOscuro = modoOscuro ? blancoGrisaceo : verde;
        cambiarColorLabels(this.getContentPane(), letraModoOscuro);
        cambiarColorLabels(ventanaRegCliente.getContentPane(), letraModoOscuro);
        cambiarColorTitledBorders(this.getContentPane(), letraModoOscuro);
        
        Color botonesModoOscuro = modoOscuro ? grisCarbon : verde;
        cambiarColorBotones(this.getContentPane(), botonesModoOscuro, blancoGrisaceo);
        cambiarColorBotones(ventanaRegCliente.getContentPane(), botonesModoOscuro, blancoGrisaceo);
     
        //Icons y logos modo oscuro
        ImageIcon imagenOscura1 = modoOscuro ? new ImageIcon(getClass().getResource("/Img/ddc2.png")) : new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondoOscuro1 = new ImageIcon(imagenOscura1.getImage().getScaledInstance(labelEstadisticas.getWidth(), labelEstadisticas.getHeight(), Image.SCALE_DEFAULT));
        labelEstadisticas.setIcon(fondoOscuro1);

        ImageIcon imagenOscura2 = modoOscuro ? new ImageIcon(getClass().getResource("/Img/ddc2.png")) : new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondoOscuro2 = new ImageIcon(imagenOscura2.getImage().getScaledInstance(logoCambiarContra.getWidth(), logoCambiarContra.getHeight(), Image.SCALE_DEFAULT));
        logoCambiarContra.setIcon(fondoOscuro2);
        logoCambiarContraGeneral.setIcon(fondoOscuro2);
        
        ImageIcon iconNV = modoOscuro ? new ImageIcon(getClass().getResource("/Img/ventasBlancas.png")) : new ImageIcon(getClass().getResource("/Img/Nventa.png"));
        labelNuevaVenta.setIcon(iconNV);
        
        ImageIcon iconNC = modoOscuro ? new ImageIcon(getClass().getResource("/Img/comprasBlancas.png")) : new ImageIcon(getClass().getResource("/Img/Carrito-de-compras.png"));
        labelNuevaCompra.setIcon(iconNC);
        
        ImageIcon iconProd = modoOscuro ? new ImageIcon(getClass().getResource("/Img/prods2.png")) : new ImageIcon(getClass().getResource("/Img/producto.png"));
        labelProductos.setIcon(iconProd);
        
        ImageIcon iconCli = modoOscuro ? new ImageIcon(getClass().getResource("/Img/clientesBlancos.png")) : new ImageIcon(getClass().getResource("/Img/Clientes.png"));
        labelClientes.setIcon(iconCli);
        
        ImageIcon iconProv = modoOscuro ? new ImageIcon(getClass().getResource("/Img/provsBlancos.png")) : new ImageIcon(getClass().getResource("/Img/proveedor.png"));
        labelProveedores.setIcon(iconProv);
        
        ImageIcon iconMed = modoOscuro ? new ImageIcon(getClass().getResource("/Img/medidaBlanca.png")) : new ImageIcon(getClass().getResource("/Img/detallista.png"));
        labelMedidas.setIcon(iconMed);
        
        ImageIcon iconCat = modoOscuro ? new ImageIcon(getClass().getResource("/Img/catBlanco.png")) : new ImageIcon(getClass().getResource("/Img/box.png"));
        labelCategorias.setIcon(iconCat);
        
        ImageIcon iconConfig = modoOscuro ? new ImageIcon(getClass().getResource("/Img/configBlanco.png")) : new ImageIcon(getClass().getResource("/Img/config.png"));
        labelConfig.setIcon(iconConfig);
        
        ImageIcon iconCaja = modoOscuro ? new ImageIcon(getClass().getResource("/Img/cajaBlanca.png")) : new ImageIcon(getClass().getResource("/Img/cajero.png"));
        labelCaja.setIcon(iconCaja);
        
        ImageIcon iconUsers = modoOscuro ? new ImageIcon(getClass().getResource("/Img/userBlanco.png")) : new ImageIcon(getClass().getResource("/Img/users.png"));
        labelUsers.setIcon(iconUsers);
        
        ImageIcon iconProdEstadistica = modoOscuro ? new ImageIcon(getClass().getResource("/Img/catBlanco.png")) : new ImageIcon(getClass().getResource("/Img/box.png"));
        jLabel65.setIcon(iconProdEstadistica);
        
        ImageIcon iconClientesEstadistica = modoOscuro ? new ImageIcon(getClass().getResource("/Img/clientesBlancos.png")) : new ImageIcon(getClass().getResource("/Img/Clientes.png"));
        jLabel76.setIcon(iconClientesEstadistica);
        
        ImageIcon iconComprasEstadistica = modoOscuro ? new ImageIcon(getClass().getResource("/Img/comprasBlancas.png")) : new ImageIcon(getClass().getResource("/Img/Carrito-de-compras.png"));
        jLabel77.setIcon(iconComprasEstadistica);
        
        ImageIcon iconVentasEstadistica = modoOscuro ? new ImageIcon(getClass().getResource("/Img/ventasBlancas.png")) : new ImageIcon(getClass().getResource("/Img/Nventa.png"));
        jLabel68.setIcon(iconVentasEstadistica);
        
        ImageIcon imagenBotonModo = modoOscuro ? new ImageIcon(getClass().getResource("/Img/luna.png")) : new ImageIcon(getClass().getResource("/Img/sol.png"));
        btnModoOscuro.setIcon(imagenBotonModo);
        
        btnModoOscuro.setBackground(grisCarbon);
        this.repaint();
    }
    
    public void cambiarColorBotones(Container contenedor, Color colorFondo, Color colorTexto) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JButton) {
                JButton boton = (JButton) comp;
                boton.setBackground(colorFondo);
                boton.setForeground(colorTexto);
            } else if (comp instanceof Container) {
                cambiarColorBotones((Container) comp, colorFondo, colorTexto);
            }
        }
    }
    
    public void cambiarColorLabels(Container contenedor, Color nuevoColor) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setForeground(nuevoColor);
                label.setBackground(new Color(0, 0, 0, 0));
            } else if (comp instanceof Container) {
                cambiarColorLabels((Container) comp, nuevoColor);
            }
        }
    }
    
    public void cambiarColorTitledBorders(Container contenedor, Color nuevoColor) {
        for (Component comp: contenedor.getComponents()) {
            if (comp instanceof JComponent) {
                JComponent jc = (JComponent) comp;
                Border border = jc.getBorder();

                if (border instanceof TitledBorder) {
                    TitledBorder tb = (TitledBorder) border;
                    tb.setTitleColor(nuevoColor);
                    jc.repaint();
                }
                if (jc instanceof Container) {
                    cambiarColorTitledBorders((Container) jc, nuevoColor);
                }
            }
        }
    }
    
    public void aplicarEstiloHeaderOscuro(JTable tabla) {
        JTableHeader header = tabla.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                Color headerDinamico = modoOscuro ? new Color(40, 40, 40) : new Color(43, 147, 72);
                label.setBackground(headerDinamico);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });
        header.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPopupUsuarios = new javax.swing.JPopupMenu();
        JMenuEliminarUser = new javax.swing.JMenuItem();
        JMenuReingresarUser = new javax.swing.JMenuItem();
        JPopupClientes = new javax.swing.JPopupMenu();
        JMenuEliminarCli = new javax.swing.JMenuItem();
        JMenuReingresarCli = new javax.swing.JMenuItem();
        JPopupProveedores = new javax.swing.JPopupMenu();
        JMenuEliminarProv = new javax.swing.JMenuItem();
        JMenuReingresarProv = new javax.swing.JMenuItem();
        JPopupMenuCategorias = new javax.swing.JPopupMenu();
        JMenuEliminarCat = new javax.swing.JMenuItem();
        JMenuReingresarCat = new javax.swing.JMenuItem();
        JPopupMenuMedidas = new javax.swing.JPopupMenu();
        JMenuEliminarMedida = new javax.swing.JMenuItem();
        JMenuReingresarMedida = new javax.swing.JMenuItem();
        JPopupMenuProductos = new javax.swing.JPopupMenu();
        JMenuEliminarPro = new javax.swing.JMenuItem();
        JMenuReingresarPro = new javax.swing.JMenuItem();
        JPopupMenuCajas = new javax.swing.JPopupMenu();
        JMenuEliminarCaja = new javax.swing.JMenuItem();
        JMenuReingresarCaja = new javax.swing.JMenuItem();
        btnUsers = new javax.swing.JButton();
        ventanaRegCliente = new javax.swing.JDialog();
        jLabel91 = new javax.swing.JLabel();
        txtNombreCliV = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        txtTelefonoCliV = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        txtCorreoCliV = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtDireccionCliV = new javax.swing.JTextPane();
        btnNuevoCliV = new javax.swing.JButton();
        btnRegistrarCliV = new javax.swing.JButton();
        jLabel95 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelNuevaVenta = new javax.swing.JPanel();
        labelNuevaVenta = new javax.swing.JLabel();
        panelNuevaCompra = new javax.swing.JPanel();
        labelNuevaCompra = new javax.swing.JLabel();
        panelProductos = new javax.swing.JPanel();
        labelProductos = new javax.swing.JLabel();
        panelClientes = new javax.swing.JPanel();
        labelClientes = new javax.swing.JLabel();
        panelProveedores = new javax.swing.JPanel();
        labelProveedores = new javax.swing.JLabel();
        panelMedidas = new javax.swing.JPanel();
        labelMedidas = new javax.swing.JLabel();
        panelCategorias = new javax.swing.JPanel();
        labelCategorias = new javax.swing.JLabel();
        panelConfig = new javax.swing.JPanel();
        labelConfig = new javax.swing.JLabel();
        panelUsers = new javax.swing.JPanel();
        labelUsers = new javax.swing.JLabel();
        panelCaja = new javax.swing.JPanel();
        labelCaja = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelEstadisticas = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtIDUsuarioRol = new javax.swing.JTextField();
        labelRolUsuarioConectado = new javax.swing.JLabel();
        btnUsers1 = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        btnModoOscuro = new javax.swing.JToggleButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        txtDescripcionPro = new javax.swing.JTextField();
        txtPrecioCompraPro = new javax.swing.JTextField();
        cbxCatPro = new javax.swing.JComboBox<>();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        cbxMedidaPro = new javax.swing.JComboBox<>();
        txtPrecioVentaPro = new javax.swing.JTextField();
        btnNuevoPro = new javax.swing.JButton();
        btnRegistrarPro = new javax.swing.JButton();
        btnModificarPro = new javax.swing.JButton();
        txtIDpro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableClientes = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNombreCli = new javax.swing.JTextField();
        txtTelefonoCli = new javax.swing.JTextField();
        btnNuevoCli = new javax.swing.JButton();
        btnRegistrarCli = new javax.swing.JButton();
        btnModificarCli = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDireccionCli = new javax.swing.JTextPane();
        txtIDCli = new javax.swing.JTextField();
        txtBuscarCli = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtCorreoCli = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProveedor = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombreProv = new javax.swing.JTextField();
        txtTelefonoProv = new javax.swing.JTextField();
        btnNuevoProv = new javax.swing.JButton();
        btnRegistrarProv = new javax.swing.JButton();
        btnModificarProv = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDireccionProv = new javax.swing.JTextPane();
        jLabel15 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        txtIDprov = new javax.swing.JTextField();
        txtBuscarProv = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtCorreoProv = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtNombreUser = new javax.swing.JTextField();
        btnNuevoUser = new javax.swing.JButton();
        btnRegistrarUser = new javax.swing.JButton();
        btnModificarUser = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtUsuarioUser = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cbxCajaUser = new javax.swing.JComboBox<>();
        cbxRolUser = new javax.swing.JComboBox<>();
        txtClaveUser = new javax.swing.JPasswordField();
        txtIDuser = new javax.swing.JTextField();
        txtBuscarUser = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtNombreCat = new javax.swing.JTextField();
        btnNuevoCat = new javax.swing.JButton();
        btnRegistrarCat = new javax.swing.JButton();
        btnModificarCat = new javax.swing.JButton();
        txtBuscarCat = new javax.swing.JTextField();
        txtIDcat = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableCat = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtNombreMedida = new javax.swing.JTextField();
        btnNuevoMedida = new javax.swing.JButton();
        btnRegistrarMedida = new javax.swing.JButton();
        btnModificarMedida = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtNombreCortoMed = new javax.swing.JTextField();
        txtBuscarMed = new javax.swing.JTextField();
        txtIDmed = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableMedida = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableNuevaVenta = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtCantNV = new javax.swing.JTextField();
        txtPrecioNV = new javax.swing.JTextField();
        txtTotalNV = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtStockNV = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        cbxClienteVentas = new javax.swing.JComboBox<>();
        txtPagarConNV = new javax.swing.JTextField();
        txtVueltoNV = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        labelTotalPagar = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtIDNV = new javax.swing.JTextField();
        btnayc = new javax.swing.JButton();
        btnventas = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        labelEstadoDolar = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        labelDolarVentaAnterior = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        labelDolarVenta = new javax.swing.JLabel();
        txtCodNV = new javax.swing.JComboBox<>();
        btnLimpiarTablaTemporal = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        labelTotalPagarDolares = new javax.swing.JLabel();
        txtProductoNV = new javax.swing.JComboBox<>();
        btnAbrirModal = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableVentas = new javax.swing.JTable();
        txtBuscarVenta = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        btnHistorialVentas = new javax.swing.JButton();
        txtIDVentas = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableCompras = new javax.swing.JTable();
        txtBuscarCompra = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        btnHistorialCompra = new javax.swing.JButton();
        txtIDCompra = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtNombreEmpresa = new javax.swing.JTextField();
        txtTelefonoEmpresa = new javax.swing.JTextField();
        btnModificarEmpresa = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        txtDireccionEmpresa = new javax.swing.JTextPane();
        jLabel46 = new javax.swing.JLabel();
        txtRucEmpresa = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtMensaje = new javax.swing.JTextPane();
        txtIDEmpresa = new javax.swing.JTextField();
        txtIGEmpresa = new javax.swing.JTextField();
        txtCorreoEmpresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableNuevaCompra = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtCantNC = new javax.swing.JTextField();
        txtPrecioNC = new javax.swing.JTextField();
        txtTotalNC = new javax.swing.JTextField();
        btnGenerarCompra = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        cbxProveedorNC = new javax.swing.JComboBox<>();
        txtPagarConNC = new javax.swing.JTextField();
        txtVueltoNC = new javax.swing.JTextField();
        labelDolarAnterior = new javax.swing.JLabel();
        labelTotalCompra = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtIDNC = new javax.swing.JTextField();
        btncompras = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        labelDolar = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        labelEstadoDolarCompra = new javax.swing.JLabel();
        txtCodNC = new javax.swing.JComboBox<>();
        btnLimpiarTablaTemporalCompras = new javax.swing.JButton();
        jLabel90 = new javax.swing.JLabel();
        labelTotalCompraDolares = new javax.swing.JLabel();
        txtProductoNC = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        txtNombreCaja = new javax.swing.JTextField();
        btnNuevoCaja = new javax.swing.JButton();
        btnRegistrarCaja = new javax.swing.JButton();
        txtBuscarCaja = new javax.swing.JTextField();
        btnModificarCaja = new javax.swing.JButton();
        txtIDCaja = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tableCaja = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        txtMontoInicial = new javax.swing.JTextField();
        btnNuevoApertura = new javax.swing.JButton();
        btnAbrirCaja = new javax.swing.JButton();
        btnCerrarCaja = new javax.swing.JButton();
        txtBuscarApertura = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tableApertura = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        btnCambiarContra = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        nuevaContra = new javax.swing.JPasswordField();
        confirmarContra = new javax.swing.JPasswordField();
        btnLimpiarContra = new javax.swing.JButton();
        logoCambiarContra = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        labeltotalproductos = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        labeltotalclientes = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        labeltotalcompras = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        labeltotalventas = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        panelbarras = new javax.swing.JPanel();
        panelpie = new javax.swing.JPanel();
        prueba = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        btnCambiarContraGeneral = new javax.swing.JButton();
        jLabel87 = new javax.swing.JLabel();
        labelUsuarioSeleccionado = new javax.swing.JLabel();
        nuevaContraGeneral = new javax.swing.JPasswordField();
        confirmarContraGeneral = new javax.swing.JPasswordField();
        btnLimpiarContraGeneral = new javax.swing.JButton();
        logoCambiarContraGeneral = new javax.swing.JLabel();
        txtIDUserGeneral = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tableUsersGenerales = new javax.swing.JTable();

        JMenuEliminarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarUser.setText("Eliminar");
        JPopupUsuarios.add(JMenuEliminarUser);

        JMenuReingresarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarUser.setText("Reingresar");
        JMenuReingresarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuReingresarUserActionPerformed(evt);
            }
        });
        JPopupUsuarios.add(JMenuReingresarUser);

        JMenuEliminarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarCli.setText("Eliminar");
        JPopupClientes.add(JMenuEliminarCli);

        JMenuReingresarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarCli.setText("Reingresar");
        JPopupClientes.add(JMenuReingresarCli);

        JMenuEliminarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarProv.setText("Eliminar");
        JMenuEliminarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuEliminarProvActionPerformed(evt);
            }
        });
        JPopupProveedores.add(JMenuEliminarProv);

        JMenuReingresarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarProv.setText("Reingresar");
        JPopupProveedores.add(JMenuReingresarProv);

        JMenuEliminarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarCat.setText("Eliminar");
        JPopupMenuCategorias.add(JMenuEliminarCat);

        JMenuReingresarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarCat.setText("Reingresar");
        JPopupMenuCategorias.add(JMenuReingresarCat);

        JMenuEliminarMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarMedida.setText("Eliminar");
        JPopupMenuMedidas.add(JMenuEliminarMedida);

        JMenuReingresarMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarMedida.setText("Reingresar");
        JPopupMenuMedidas.add(JMenuReingresarMedida);

        JMenuEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarPro.setText("Eliminar");
        JPopupMenuProductos.add(JMenuEliminarPro);

        JMenuReingresarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarPro.setText("Reingresar");
        JPopupMenuProductos.add(JMenuReingresarPro);

        JMenuEliminarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        JMenuEliminarCaja.setText("Eliminar");
        JPopupMenuCajas.add(JMenuEliminarCaja);

        JMenuReingresarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/exchange.png"))); // NOI18N
        JMenuReingresarCaja.setText("Reingresar");
        JPopupMenuCajas.add(JMenuReingresarCaja);

        btnUsers.setText("Users");

        ventanaRegCliente.setMinimumSize(new java.awt.Dimension(350, 390));
        ventanaRegCliente.setResizable(false);
        ventanaRegCliente.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel91.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(43, 147, 72));
        jLabel91.setText("Registrar Cliente");
        ventanaRegCliente.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, 30));
        ventanaRegCliente.getContentPane().add(txtNombreCliV, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 230, 30));

        jLabel92.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(43, 147, 72));
        jLabel92.setText("Teléfono");
        ventanaRegCliente.getContentPane().add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 30));
        ventanaRegCliente.getContentPane().add(txtTelefonoCliV, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 230, 30));

        jLabel93.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(43, 147, 72));
        jLabel93.setText("Correo");
        ventanaRegCliente.getContentPane().add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 30));
        ventanaRegCliente.getContentPane().add(txtCorreoCliV, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 230, 30));

        jLabel94.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(43, 147, 72));
        jLabel94.setText("Dirección");
        ventanaRegCliente.getContentPane().add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 30));

        jScrollPane18.setViewportView(txtDireccionCliV);

        ventanaRegCliente.getContentPane().add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 230, 80));

        btnNuevoCliV.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoCliV.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoCliV.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCliV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCliV.setText("Nuevo");
        btnNuevoCliV.setFocusPainted(false);
        ventanaRegCliente.getContentPane().add(btnNuevoCliV, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 130, 35));

        btnRegistrarCliV.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarCliV.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarCliV.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCliV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarCliV.setText("Registrar");
        btnRegistrarCliV.setFocusPainted(false);
        ventanaRegCliente.getContentPane().add(btnRegistrarCliV, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 140, 35));

        jLabel95.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(43, 147, 72));
        jLabel95.setText("Nombre");
        ventanaRegCliente.getContentPane().add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(250, 251, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelNuevaVenta.setBackground(new java.awt.Color(250, 251, 253));

        labelNuevaVenta.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelNuevaVenta.setForeground(new java.awt.Color(43, 147, 72));
        labelNuevaVenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        labelNuevaVenta.setText("Nueva Venta");
        labelNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelNuevaVentaLayout = new javax.swing.GroupLayout(panelNuevaVenta);
        panelNuevaVenta.setLayout(panelNuevaVentaLayout);
        panelNuevaVentaLayout.setHorizontalGroup(
            panelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelNuevaVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelNuevaVentaLayout.setVerticalGroup(
            panelNuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelNuevaVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 40));

        panelNuevaCompra.setBackground(new java.awt.Color(250, 251, 253));

        labelNuevaCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelNuevaCompra.setForeground(new java.awt.Color(43, 147, 72));
        labelNuevaCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNuevaCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Carrito-de-compras.png"))); // NOI18N
        labelNuevaCompra.setText("Nueva Compra");
        labelNuevaCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelNuevaCompraLayout = new javax.swing.GroupLayout(panelNuevaCompra);
        panelNuevaCompra.setLayout(panelNuevaCompraLayout);
        panelNuevaCompraLayout.setHorizontalGroup(
            panelNuevaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelNuevaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelNuevaCompraLayout.setVerticalGroup(
            panelNuevaCompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelNuevaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelNuevaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 180, 40));

        panelProductos.setBackground(new java.awt.Color(250, 251, 253));

        labelProductos.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelProductos.setForeground(new java.awt.Color(43, 147, 72));
        labelProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        labelProductos.setText("Productos");
        labelProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 40));

        panelClientes.setBackground(new java.awt.Color(250, 251, 253));

        labelClientes.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelClientes.setForeground(new java.awt.Color(43, 147, 72));
        labelClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        labelClientes.setText("Clientes");
        labelClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 180, 40));

        panelProveedores.setBackground(new java.awt.Color(250, 251, 253));

        labelProveedores.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelProveedores.setForeground(new java.awt.Color(43, 147, 72));
        labelProveedores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        labelProveedores.setText("Proveedores");
        labelProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 180, 40));

        panelMedidas.setBackground(new java.awt.Color(250, 251, 253));

        labelMedidas.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelMedidas.setForeground(new java.awt.Color(43, 147, 72));
        labelMedidas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMedidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/detallista.png"))); // NOI18N
        labelMedidas.setText("Escalas");
        labelMedidas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelMedidasLayout = new javax.swing.GroupLayout(panelMedidas);
        panelMedidas.setLayout(panelMedidasLayout);
        panelMedidasLayout.setHorizontalGroup(
            panelMedidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelMedidas, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelMedidasLayout.setVerticalGroup(
            panelMedidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelMedidas, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelMedidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 180, 40));

        panelCategorias.setBackground(new java.awt.Color(250, 251, 253));

        labelCategorias.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelCategorias.setForeground(new java.awt.Color(43, 147, 72));
        labelCategorias.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelCategorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/box.png"))); // NOI18N
        labelCategorias.setText("Categorías");
        labelCategorias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelCategoriasLayout = new javax.swing.GroupLayout(panelCategorias);
        panelCategorias.setLayout(panelCategoriasLayout);
        panelCategoriasLayout.setHorizontalGroup(
            panelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelCategoriasLayout.setVerticalGroup(
            panelCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelCategorias, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 180, 40));

        panelConfig.setBackground(new java.awt.Color(250, 251, 253));

        labelConfig.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelConfig.setForeground(new java.awt.Color(43, 147, 72));
        labelConfig.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        labelConfig.setText("Configuración");
        labelConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelConfigLayout = new javax.swing.GroupLayout(panelConfig);
        panelConfig.setLayout(panelConfigLayout);
        panelConfigLayout.setHorizontalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelConfigLayout.setVerticalGroup(
            panelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelConfig, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 180, 40));

        panelUsers.setBackground(new java.awt.Color(250, 251, 253));

        labelUsers.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelUsers.setForeground(new java.awt.Color(43, 147, 72));
        labelUsers.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/users.png"))); // NOI18N
        labelUsers.setText("Usuarios");
        labelUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelUsersLayout = new javax.swing.GroupLayout(panelUsers);
        panelUsers.setLayout(panelUsersLayout);
        panelUsersLayout.setHorizontalGroup(
            panelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelUsersLayout.setVerticalGroup(
            panelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 180, 40));

        panelCaja.setBackground(new java.awt.Color(250, 251, 253));
        panelCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelCaja.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 17)); // NOI18N
        labelCaja.setForeground(new java.awt.Color(43, 147, 72));
        labelCaja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cajero.png"))); // NOI18N
        labelCaja.setText("Caja");
        labelCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelCaja.add(labelCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 40));

        jPanel1.add(panelCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 180, 40));
        jPanel1.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 200, 510));

        jPanel2.setBackground(new java.awt.Color(250, 251, 253));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelEstadisticas.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        labelEstadisticas.setForeground(new java.awt.Color(255, 255, 255));
        labelEstadisticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelEstadisticas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.add(labelEstadisticas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 130));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 140));

        jPanel3.setBackground(new java.awt.Color(250, 251, 253));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(txtBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 260, 50));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/consultas.png"))); // NOI18N
        jPanel3.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 50, 50));

        jLabel49.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(43, 147, 72));
        jLabel49.setText("J -31244349-9");
        jPanel3.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 180, -1));

        txtIDUsuarioRol.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        txtIDUsuarioRol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIDUsuarioRol.setText("4");
        txtIDUsuarioRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDUsuarioRolActionPerformed(evt);
            }
        });
        jPanel3.add(txtIDUsuarioRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 40, 30, 30));

        labelRolUsuarioConectado.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        labelRolUsuarioConectado.setForeground(new java.awt.Color(43, 147, 72));
        labelRolUsuarioConectado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(labelRolUsuarioConectado, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 90, 250, 35));

        btnUsers1.setBackground(new java.awt.Color(43, 147, 72));
        btnUsers1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnUsers1.setForeground(new java.awt.Color(255, 255, 255));
        btnUsers1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/userBlanco.png"))); // NOI18N
        btnUsers1.setFocusPainted(false);
        btnUsers1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsers1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnUsers1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 270, 50));

        jLabel79.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 10)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(204, 204, 204));
        jLabel79.setText("Copyright © 2025, CorzoDev™");
        jPanel3.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 180, 20));

        jLabel80.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 30)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(43, 147, 72));
        jLabel80.setText("Dynamic Data Control C.A");
        jPanel3.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 440, 40));

        btnModoOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoOscuroActionPerformed(evt);
            }
        });
        jPanel3.add(btnModoOscuro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 90, 40, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1150, 140));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel4.setBackground(new java.awt.Color(235, 235, 235));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(250, 251, 253));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel15.setForeground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(43, 147, 72));
        jLabel1.setText("Código");
        jPanel15.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(43, 147, 72));
        jLabel3.setText("Descripción");
        jPanel15.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 30));

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(43, 147, 72));
        jLabel4.setText("Precio de Compra");
        jPanel15.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 30));

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(43, 147, 72));
        jLabel5.setText("Precio de Venta");
        jPanel15.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, 30));

        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(43, 147, 72));
        jLabel6.setText("Proveedor");
        jPanel15.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, 30));

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(43, 147, 72));
        jLabel7.setText("Medida");
        jPanel15.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, 30));

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(43, 147, 72));
        jLabel8.setText("Categoría");
        jPanel15.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, 30));
        jPanel15.add(txtCodigoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 150, 30));
        jPanel15.add(txtDescripcionPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 150, 30));
        jPanel15.add(txtPrecioCompraPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 90, 30));

        cbxCatPro.setEditable(true);
        jPanel15.add(cbxCatPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 150, 30));

        cbxProveedorPro.setEditable(true);
        jPanel15.add(cbxProveedorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 150, 30));

        cbxMedidaPro.setEditable(true);
        jPanel15.add(cbxMedidaPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 150, 30));
        jPanel15.add(txtPrecioVentaPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 90, 30));

        btnNuevoPro.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoPro.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoPro.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoPro.setText("Nuevo");
        btnNuevoPro.setFocusPainted(false);
        jPanel15.add(btnNuevoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, -1, 35));

        btnRegistrarPro.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarPro.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarPro.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarPro.setText("Registrar");
        btnRegistrarPro.setFocusPainted(false);
        jPanel15.add(btnRegistrarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 140, 35));

        btnModificarPro.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarPro.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarPro.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarPro.setText("Modificar");
        btnModificarPro.setFocusPainted(false);
        jPanel15.add(btnModificarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 150, 35));
        jPanel15.add(txtIDpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 50, 30));

        jPanel4.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 470));

        tableProductos.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Descripción", "Precio", "Stock", "Estado"
            }
        ));
        tableProductos.setComponentPopupMenu(JPopupMenuProductos);
        tableProductos.setFocusable(false);
        tableProductos.setRowHeight(23);
        jScrollPane1.setViewportView(tableProductos);
        if (tableProductos.getColumnModel().getColumnCount() > 0) {
            tableProductos.getColumnModel().getColumn(0).setMinWidth(50);
            tableProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableProductos.getColumnModel().getColumn(0).setMaxWidth(50);
            tableProductos.getColumnModel().getColumn(1).setMinWidth(80);
            tableProductos.getColumnModel().getColumn(1).setPreferredWidth(80);
            tableProductos.getColumnModel().getColumn(1).setMaxWidth(80);
            tableProductos.getColumnModel().getColumn(3).setMinWidth(75);
            tableProductos.getColumnModel().getColumn(3).setPreferredWidth(75);
            tableProductos.getColumnModel().getColumn(3).setMaxWidth(75);
            tableProductos.getColumnModel().getColumn(4).setMinWidth(75);
            tableProductos.getColumnModel().getColumn(4).setPreferredWidth(75);
            tableProductos.getColumnModel().getColumn(4).setMaxWidth(75);
            tableProductos.getColumnModel().getColumn(5).setMinWidth(100);
            tableProductos.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableProductos.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 780, 470));

        jTabbedPane1.addTab("Prods", jPanel4);

        jPanel5.setBackground(new java.awt.Color(235, 235, 235));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableClientes.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Teléfono", "Correo", "Dirección", "Estado"
            }
        ));
        tableClientes.setComponentPopupMenu(JPopupClientes);
        tableClientes.setRowHeight(23);
        jScrollPane2.setViewportView(tableClientes);
        if (tableClientes.getColumnModel().getColumnCount() > 0) {
            tableClientes.getColumnModel().getColumn(0).setMinWidth(50);
            tableClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableClientes.getColumnModel().getColumn(0).setMaxWidth(50);
            tableClientes.getColumnModel().getColumn(1).setMinWidth(175);
            tableClientes.getColumnModel().getColumn(1).setPreferredWidth(175);
            tableClientes.getColumnModel().getColumn(1).setMaxWidth(175);
            tableClientes.getColumnModel().getColumn(2).setMinWidth(100);
            tableClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableClientes.getColumnModel().getColumn(2).setMaxWidth(100);
            tableClientes.getColumnModel().getColumn(3).setMinWidth(165);
            tableClientes.getColumnModel().getColumn(3).setPreferredWidth(165);
            tableClientes.getColumnModel().getColumn(3).setMaxWidth(165);
            tableClientes.getColumnModel().getColumn(4).setMinWidth(200);
            tableClientes.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableClientes.getColumnModel().getColumn(4).setMaxWidth(200);
            tableClientes.getColumnModel().getColumn(5).setMinWidth(100);
            tableClientes.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableClientes.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 790, 470));

        jPanel18.setBackground(new java.awt.Color(250, 251, 253));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel18.setForeground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(43, 147, 72));
        jLabel9.setText("Nombre");
        jPanel18.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));

        jLabel10.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(43, 147, 72));
        jLabel10.setText("Correo");
        jPanel18.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 30));

        jLabel11.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(43, 147, 72));
        jLabel11.setText("Dirección");
        jPanel18.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 30));
        jPanel18.add(txtNombreCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 190, 30));
        jPanel18.add(txtTelefonoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 190, 30));

        btnNuevoCli.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoCli.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoCli.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCli.setText("Nuevo");
        btnNuevoCli.setFocusPainted(false);
        jPanel18.add(btnNuevoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 130, 35));

        btnRegistrarCli.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarCli.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarCli.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarCli.setText("Registrar");
        btnRegistrarCli.setFocusPainted(false);
        jPanel18.add(btnRegistrarCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 420, 140, 35));

        btnModificarCli.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarCli.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarCli.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarCli.setText("Modificar");
        btnModificarCli.setFocusPainted(false);
        jPanel18.add(btnModificarCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 150, 35));

        jScrollPane3.setViewportView(txtDireccionCli);

        jPanel18.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 190, 80));

        txtIDCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDCliActionPerformed(evt);
            }
        });
        jPanel18.add(txtIDCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 90, 30));
        jPanel18.add(txtBuscarCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 190, 30));

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel18.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 30, 30));

        jLabel41.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(43, 147, 72));
        jLabel41.setText("Teléfono");
        jPanel18.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 30));
        jPanel18.add(txtCorreoCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 190, 30));

        jPanel5.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 470));

        jTabbedPane1.addTab("Clientes", jPanel5);

        jPanel6.setBackground(new java.awt.Color(235, 235, 235));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableProveedor.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RIF", "Nombre", "Teléfono", "Correo", "Dirección", "Estado"
            }
        ));
        tableProveedor.setComponentPopupMenu(JPopupProveedores);
        tableProveedor.setRowHeight(23);
        jScrollPane4.setViewportView(tableProveedor);
        if (tableProveedor.getColumnModel().getColumnCount() > 0) {
            tableProveedor.getColumnModel().getColumn(0).setMinWidth(50);
            tableProveedor.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableProveedor.getColumnModel().getColumn(0).setMaxWidth(50);
            tableProveedor.getColumnModel().getColumn(1).setMinWidth(100);
            tableProveedor.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(1).setMaxWidth(100);
            tableProveedor.getColumnModel().getColumn(2).setMinWidth(145);
            tableProveedor.getColumnModel().getColumn(2).setPreferredWidth(145);
            tableProveedor.getColumnModel().getColumn(2).setMaxWidth(145);
            tableProveedor.getColumnModel().getColumn(3).setMinWidth(100);
            tableProveedor.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(3).setMaxWidth(100);
            tableProveedor.getColumnModel().getColumn(4).setMinWidth(155);
            tableProveedor.getColumnModel().getColumn(4).setPreferredWidth(155);
            tableProveedor.getColumnModel().getColumn(4).setMaxWidth(155);
            tableProveedor.getColumnModel().getColumn(5).setMinWidth(175);
            tableProveedor.getColumnModel().getColumn(5).setPreferredWidth(175);
            tableProveedor.getColumnModel().getColumn(5).setMaxWidth(175);
            tableProveedor.getColumnModel().getColumn(6).setMinWidth(70);
            tableProveedor.getColumnModel().getColumn(6).setPreferredWidth(70);
            tableProveedor.getColumnModel().getColumn(6).setMaxWidth(70);
        }

        jPanel6.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 800, 480));

        jPanel19.setBackground(new java.awt.Color(250, 251, 253));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Proveedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 147, 72));
        jLabel12.setText("Nombre");
        jPanel19.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 30));

        jLabel13.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(43, 147, 72));
        jLabel13.setText("Correo");
        jPanel19.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 30));

        jLabel14.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(43, 147, 72));
        jLabel14.setText("Dirección");
        jPanel19.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 30));
        jPanel19.add(txtNombreProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 190, 30));

        txtTelefonoProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoProvActionPerformed(evt);
            }
        });
        jPanel19.add(txtTelefonoProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 190, 30));

        btnNuevoProv.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoProv.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoProv.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoProv.setText("Nuevo");
        btnNuevoProv.setFocusPainted(false);
        jPanel19.add(btnNuevoProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 130, 35));

        btnRegistrarProv.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarProv.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarProv.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarProv.setText("Registrar");
        btnRegistrarProv.setFocusPainted(false);
        jPanel19.add(btnRegistrarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 140, 35));

        btnModificarProv.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarProv.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarProv.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarProv.setText("Modificar");
        btnModificarProv.setFocusPainted(false);
        btnModificarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProvActionPerformed(evt);
            }
        });
        jPanel19.add(btnModificarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 140, 35));

        jScrollPane5.setViewportView(txtDireccionProv);

        jPanel19.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 190, 80));

        jLabel15.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(43, 147, 72));
        jLabel15.setText("RIF");
        jPanel19.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        jPanel19.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 190, 30));

        txtIDprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDprovActionPerformed(evt);
            }
        });
        jPanel19.add(txtIDprov, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 90, 30));
        jPanel19.add(txtBuscarProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 190, 30));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel19.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel86.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(43, 147, 72));
        jLabel86.setText("Teléfono");
        jPanel19.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 30));

        txtCorreoProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoProvActionPerformed(evt);
            }
        });
        jPanel19.add(txtCorreoProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 190, 30));

        jPanel6.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 480));

        jTabbedPane1.addTab("Provs", jPanel6);

        jPanel7.setBackground(new java.awt.Color(235, 235, 235));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(250, 251, 253));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuevo Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(43, 147, 72));
        jLabel16.setText("Nombre");
        jPanel20.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 30));

        jLabel17.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(43, 147, 72));
        jLabel17.setText("Contraseña");
        jPanel20.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 30));

        jLabel18.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(43, 147, 72));
        jLabel18.setText("Caja");
        jPanel20.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 30));
        jPanel20.add(txtNombreUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 190, 30));

        btnNuevoUser.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoUser.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoUser.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoUser.setText("Nuevo");
        btnNuevoUser.setFocusPainted(false);
        jPanel20.add(btnNuevoUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 370, 120, 35));

        btnRegistrarUser.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarUser.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarUser.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarUser.setText("Registrar");
        btnRegistrarUser.setFocusPainted(false);
        jPanel20.add(btnRegistrarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 140, 35));

        btnModificarUser.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarUser.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarUser.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarUser.setText("Modificar");
        btnModificarUser.setFocusPainted(false);
        jPanel20.add(btnModificarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 150, 35));

        jLabel19.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(43, 147, 72));
        jLabel19.setText("Usuario");
        jPanel20.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        jPanel20.add(txtUsuarioUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 190, 30));

        jLabel20.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(43, 147, 72));
        jLabel20.setText("Rol");
        jPanel20.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, 30));

        cbxCajaUser.setEditable(true);
        jPanel20.add(cbxCajaUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 190, 30));

        cbxRolUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Gerente", "Empleado(a)" }));
        jPanel20.add(cbxRolUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 190, 30));
        jPanel20.add(txtClaveUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 190, 30));
        jPanel20.add(txtIDuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 60, 30));

        txtBuscarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarUserActionPerformed(evt);
            }
        });
        jPanel20.add(txtBuscarUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 190, 30));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel20.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 30, 30));

        jPanel7.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 470));

        tableUsers.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuario", "Nombre", "Rol", "Código", "Caja", "Estado"
            }
        ));
        tableUsers.setComponentPopupMenu(JPopupUsuarios);
        tableUsers.setRowHeight(23);
        jScrollPane7.setViewportView(tableUsers);
        if (tableUsers.getColumnModel().getColumnCount() > 0) {
            tableUsers.getColumnModel().getColumn(0).setMinWidth(50);
            tableUsers.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableUsers.getColumnModel().getColumn(0).setMaxWidth(50);
            tableUsers.getColumnModel().getColumn(1).setMinWidth(90);
            tableUsers.getColumnModel().getColumn(1).setPreferredWidth(90);
            tableUsers.getColumnModel().getColumn(1).setMaxWidth(90);
            tableUsers.getColumnModel().getColumn(2).setMinWidth(125);
            tableUsers.getColumnModel().getColumn(2).setPreferredWidth(125);
            tableUsers.getColumnModel().getColumn(2).setMaxWidth(125);
            tableUsers.getColumnModel().getColumn(3).setMinWidth(125);
            tableUsers.getColumnModel().getColumn(3).setPreferredWidth(125);
            tableUsers.getColumnModel().getColumn(3).setMaxWidth(125);
            tableUsers.getColumnModel().getColumn(4).setMinWidth(55);
            tableUsers.getColumnModel().getColumn(4).setPreferredWidth(55);
            tableUsers.getColumnModel().getColumn(4).setMaxWidth(55);
            tableUsers.getColumnModel().getColumn(5).setMinWidth(125);
            tableUsers.getColumnModel().getColumn(5).setPreferredWidth(125);
            tableUsers.getColumnModel().getColumn(5).setMaxWidth(125);
            tableUsers.getColumnModel().getColumn(6).setMinWidth(115);
            tableUsers.getColumnModel().getColumn(6).setPreferredWidth(115);
            tableUsers.getColumnModel().getColumn(6).setMaxWidth(115);
        }

        jPanel7.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 690, 470));

        jTabbedPane1.addTab("Users", jPanel7);

        jPanel8.setBackground(new java.awt.Color(235, 235, 235));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(250, 251, 253));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Categoría", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(43, 147, 72));
        jLabel21.setText("Nombre");
        jPanel21.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));
        jPanel21.add(txtNombreCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 190, 30));

        btnNuevoCat.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoCat.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoCat.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCat.setText("Nuevo");
        btnNuevoCat.setFocusPainted(false);
        jPanel21.add(btnNuevoCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 120, 35));

        btnRegistrarCat.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarCat.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarCat.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarCat.setText("Registrar");
        btnRegistrarCat.setFocusPainted(false);
        jPanel21.add(btnRegistrarCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, 35));

        btnModificarCat.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarCat.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarCat.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarCat.setText("Modificar");
        btnModificarCat.setFocusPainted(false);
        jPanel21.add(btnModificarCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, 150, 35));
        jPanel21.add(txtBuscarCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 100, 30));
        jPanel21.add(txtIDcat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 90, 30));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel21.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 30, 30));

        tableCat.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableCat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado"
            }
        ));
        tableCat.setComponentPopupMenu(JPopupMenuCategorias);
        tableCat.setRowHeight(23);
        jScrollPane8.setViewportView(tableCat);
        if (tableCat.getColumnModel().getColumnCount() > 0) {
            tableCat.getColumnModel().getColumn(0).setMinWidth(50);
            tableCat.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableCat.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel21.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 300, 390));

        jPanel8.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 680, 430));

        jTabbedPane1.addTab("Cats", jPanel8);

        jPanel9.setBackground(new java.awt.Color(235, 235, 235));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(250, 251, 253));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Escala", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(43, 147, 72));
        jLabel22.setText("Nombre");
        jPanel22.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, 30));
        jPanel22.add(txtNombreMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 170, 30));

        btnNuevoMedida.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoMedida.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoMedida.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoMedida.setText("Nuevo");
        btnNuevoMedida.setFocusPainted(false);
        jPanel22.add(btnNuevoMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 120, 35));

        btnRegistrarMedida.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarMedida.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarMedida.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarMedida.setText("Registrar");
        btnRegistrarMedida.setFocusPainted(false);
        jPanel22.add(btnRegistrarMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 35));

        btnModificarMedida.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarMedida.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarMedida.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarMedida.setText("Modificar");
        btnModificarMedida.setFocusPainted(false);
        jPanel22.add(btnModificarMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 150, 35));

        jLabel23.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(43, 147, 72));
        jLabel23.setText("Nombre Corto");
        jPanel22.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 30));

        txtNombreCortoMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCortoMedActionPerformed(evt);
            }
        });
        jPanel22.add(txtNombreCortoMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 170, 30));
        jPanel22.add(txtBuscarMed, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 170, 30));
        jPanel22.add(txtIDmed, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 90, 30));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel22.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 30, 30));

        tableMedida.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableMedida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Nombre Corto", "Estado"
            }
        ));
        tableMedida.setComponentPopupMenu(JPopupMenuMedidas);
        tableMedida.setRowHeight(23);
        jScrollPane9.setViewportView(tableMedida);
        if (tableMedida.getColumnModel().getColumnCount() > 0) {
            tableMedida.getColumnModel().getColumn(0).setMinWidth(50);
            tableMedida.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableMedida.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel22.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 360, 400));

        jPanel9.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 430));

        jTabbedPane1.addTab("Medidas", jPanel9);

        jPanel10.setBackground(new java.awt.Color(235, 235, 235));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableNuevaVenta.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        tableNuevaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripción", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane6.setViewportView(tableNuevaVenta);
        if (tableNuevaVenta.getColumnModel().getColumnCount() > 0) {
            tableNuevaVenta.getColumnModel().getColumn(0).setMinWidth(50);
            tableNuevaVenta.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableNuevaVenta.getColumnModel().getColumn(0).setMaxWidth(50);
            tableNuevaVenta.getColumnModel().getColumn(2).setMinWidth(90);
            tableNuevaVenta.getColumnModel().getColumn(2).setPreferredWidth(90);
            tableNuevaVenta.getColumnModel().getColumn(2).setMaxWidth(90);
            tableNuevaVenta.getColumnModel().getColumn(3).setMinWidth(150);
            tableNuevaVenta.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableNuevaVenta.getColumnModel().getColumn(3).setMaxWidth(150);
            tableNuevaVenta.getColumnModel().getColumn(4).setMinWidth(150);
            tableNuevaVenta.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableNuevaVenta.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jPanel10.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 910, 280));

        jLabel24.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(43, 147, 72));
        jLabel24.setText("Código");
        jPanel10.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jLabel25.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(43, 147, 72));
        jLabel25.setText("Producto");
        jPanel10.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, 30));

        jLabel26.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(43, 147, 72));
        jLabel26.setText("Cant.");
        jPanel10.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, -1, 30));

        jLabel27.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(43, 147, 72));
        jLabel27.setText("Precio");
        jPanel10.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, -1, 30));

        jLabel28.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(43, 147, 72));
        jLabel28.setText("Total");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, -1, 30));
        jPanel10.add(txtCantNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 70, 30));

        txtPrecioNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioNVActionPerformed(evt);
            }
        });
        jPanel10.add(txtPrecioNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 80, 30));

        txtTotalNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalNVActionPerformed(evt);
            }
        });
        jPanel10.add(txtTotalNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 70, 30));

        jLabel29.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(43, 147, 72));
        jLabel29.setText("Stock");
        jPanel10.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, -1, 30));
        jPanel10.add(txtStockNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 70, 30));

        btnGenerarVenta.setBackground(new java.awt.Color(43, 147, 72));
        btnGenerarVenta.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnGenerarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/printerBlanco.png"))); // NOI18N
        btnGenerarVenta.setText("Generar Venta");
        btnGenerarVenta.setFocusPainted(false);
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel10.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 190, 50));

        jLabel30.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(43, 147, 72));
        jLabel30.setText("Cliente");
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, 30));

        cbxClienteVentas.setEditable(true);
        jPanel10.add(cbxClienteVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 220, 30));
        jPanel10.add(txtPagarConNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 120, 30));
        jPanel10.add(txtVueltoNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 470, 120, 30));

        jLabel31.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(43, 147, 72));
        jLabel31.setText("Total en $$");
        jPanel10.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 470, -1, 30));

        labelTotalPagar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelTotalPagar.setForeground(new java.awt.Color(43, 147, 72));
        labelTotalPagar.setText("-----------");
        jPanel10.add(labelTotalPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, 100, 30));

        jLabel32.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(43, 147, 72));
        jLabel32.setText("Pagar con");
        jPanel10.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, -1, 30));

        jLabel33.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(43, 147, 72));
        jLabel33.setText("Vuelto");
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 470, -1, 30));
        jPanel10.add(txtIDNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 100, 50, 30));

        btnayc.setBackground(new java.awt.Color(43, 147, 72));
        btnayc.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btnayc.setForeground(new java.awt.Color(255, 255, 255));
        btnayc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/closeBlanco.png"))); // NOI18N
        btnayc.setText("Apertura y cierre de Caja");
        btnayc.setFocusPainted(false);
        btnayc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaycActionPerformed(evt);
            }
        });
        jPanel10.add(btnayc, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 280, 50));

        btnventas.setBackground(new java.awt.Color(43, 147, 72));
        btnventas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnventas.setForeground(new java.awt.Color(255, 255, 255));
        btnventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa2.png"))); // NOI18N
        btnventas.setText("Ventas");
        btnventas.setFocusPainted(false);
        jPanel10.add(btnventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 140, 190, 50));

        jLabel62.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(43, 147, 72));
        jLabel62.setText("| Nueva Venta |");
        jPanel10.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        jLabel82.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(43, 147, 72));
        jLabel82.setText("Estado:");
        jPanel10.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 210, -1, 30));

        labelEstadoDolar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelEstadoDolar.setForeground(new java.awt.Color(43, 147, 72));
        jPanel10.add(labelEstadoDolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 210, 100, 30));

        jLabel83.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(43, 147, 72));
        jLabel83.setText("| Precio Ant. |");
        jPanel10.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 360, -1, 30));

        labelDolarVentaAnterior.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        labelDolarVentaAnterior.setForeground(new java.awt.Color(43, 147, 72));
        labelDolarVentaAnterior.setText("---------");
        jPanel10.add(labelDolarVentaAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 400, 100, 30));

        jLabel84.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(43, 147, 72));
        jLabel84.setText("| Dólar BCV |");
        jPanel10.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 260, -1, 30));

        labelDolarVenta.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        labelDolarVenta.setForeground(new java.awt.Color(43, 147, 72));
        labelDolarVenta.setText("---------");
        jPanel10.add(labelDolarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 300, 100, 30));

        txtCodNV.setEditable(true);
        jPanel10.add(txtCodNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, 30));

        btnLimpiarTablaTemporal.setBackground(new java.awt.Color(43, 147, 72));
        btnLimpiarTablaTemporal.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnLimpiarTablaTemporal.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarTablaTemporal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/basura.png"))); // NOI18N
        btnLimpiarTablaTemporal.setText("Quitar Prod.");
        btnLimpiarTablaTemporal.setFocusPainted(false);
        jPanel10.add(btnLimpiarTablaTemporal, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, 190, 50));

        jLabel88.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(43, 147, 72));
        jLabel88.setText("Total a Pagar");
        jPanel10.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 430, -1, 30));

        labelTotalPagarDolares.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelTotalPagarDolares.setForeground(new java.awt.Color(43, 147, 72));
        labelTotalPagarDolares.setText("-----------");
        jPanel10.add(labelTotalPagarDolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 470, 100, 30));

        txtProductoNV.setEditable(true);
        jPanel10.add(txtProductoNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 260, 30));

        btnAbrirModal.setBackground(new java.awt.Color(43, 147, 72));
        btnAbrirModal.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnAbrirModal.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirModal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/clientesBlancos.png"))); // NOI18N
        btnAbrirModal.setText("Reg. Cliente");
        btnAbrirModal.setFocusPainted(false);
        btnAbrirModal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirModalActionPerformed(evt);
            }
        });
        jPanel10.add(btnAbrirModal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, 170, 50));

        jTabbedPane1.addTab("Nueva Venta", jPanel10);

        jPanel11.setBackground(new java.awt.Color(235, 235, 235));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableVentas.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Total (Bs.)", "Fecha"
            }
        ));
        tableVentas.setRowHeight(23);
        jScrollPane11.setViewportView(tableVentas);
        if (tableVentas.getColumnModel().getColumnCount() > 0) {
            tableVentas.getColumnModel().getColumn(0).setMinWidth(80);
            tableVentas.getColumnModel().getColumn(0).setPreferredWidth(80);
            tableVentas.getColumnModel().getColumn(0).setMaxWidth(120);
            tableVentas.getColumnModel().getColumn(2).setMinWidth(150);
            tableVentas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableVentas.getColumnModel().getColumn(2).setMaxWidth(200);
            tableVentas.getColumnModel().getColumn(3).setMinWidth(200);
            tableVentas.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableVentas.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        jPanel11.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1090, 400));
        jPanel11.add(txtBuscarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 200, 40));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel11.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 30, 40));

        btnHistorialVentas.setBackground(new java.awt.Color(43, 147, 72));
        btnHistorialVentas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnHistorialVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorialVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnHistorialVentas.setText("Generar Reporte");
        btnHistorialVentas.setFocusPainted(false);
        jPanel11.add(btnHistorialVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 200, 40));
        jPanel11.add(txtIDVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 70, -1));

        jLabel69.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(43, 147, 72));
        jLabel69.setText("| Ventas |");
        jPanel11.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jTabbedPane1.addTab("Ventas", jPanel11);

        jPanel12.setBackground(new java.awt.Color(235, 235, 235));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableCompras.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Proveedor", "Total (Bs.)", "Fecha"
            }
        ));
        tableCompras.setRowHeight(23);
        jScrollPane12.setViewportView(tableCompras);
        if (tableCompras.getColumnModel().getColumnCount() > 0) {
            tableCompras.getColumnModel().getColumn(0).setMinWidth(50);
            tableCompras.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableCompras.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel12.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 940, 410));
        jPanel12.add(txtBuscarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 220, 40));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel12.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 40, 40));

        btnHistorialCompra.setBackground(new java.awt.Color(43, 147, 72));
        btnHistorialCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnHistorialCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorialCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnHistorialCompra.setText("Generar Reporte");
        btnHistorialCompra.setFocusPainted(false);
        jPanel12.add(btnHistorialCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 210, 40));
        jPanel12.add(txtIDCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 60, -1));

        jLabel70.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(43, 147, 72));
        jLabel70.setText("| Compras |");
        jPanel12.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jTabbedPane1.addTab("Compras", jPanel12);

        jPanel13.setBackground(new java.awt.Color(235, 235, 235));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(250, 251, 253));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(43, 147, 72));
        jLabel39.setText("Nombre");
        jPanel23.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

        jLabel44.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(43, 147, 72));
        jLabel44.setText("Teléfono");
        jPanel23.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 30));

        jLabel45.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(43, 147, 72));
        jLabel45.setText("Dirección");
        jPanel23.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, 30));
        jPanel23.add(txtNombreEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 190, 30));
        jPanel23.add(txtTelefonoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 190, 30));

        btnModificarEmpresa.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarEmpresa.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarEmpresa.setText("Modificar");
        btnModificarEmpresa.setFocusPainted(false);
        btnModificarEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpresaActionPerformed(evt);
            }
        });
        jPanel23.add(btnModificarEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 170, -1));

        jScrollPane13.setViewportView(txtDireccionEmpresa);

        jPanel23.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 190, 110));

        jLabel46.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(43, 147, 72));
        jLabel46.setText("RIF");
        jPanel23.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));
        jPanel23.add(txtRucEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 190, 30));

        jLabel47.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(43, 147, 72));
        jLabel47.setText("Mensaje");
        jPanel23.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, 30));

        jScrollPane14.setViewportView(txtMensaje);

        jPanel23.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 190, 60));

        txtIDEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDEmpresaActionPerformed(evt);
            }
        });
        jPanel23.add(txtIDEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 80, 30));
        jPanel23.add(txtIGEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 190, 30));
        jPanel23.add(txtCorreoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 190, 30));

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(43, 147, 72));
        jLabel2.setText("IG");
        jPanel23.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 30));

        jLabel64.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(43, 147, 72));
        jLabel64.setText("Correo");
        jPanel23.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        jPanel13.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 600, 350));

        jTabbedPane1.addTab("Config", jPanel13);

        jPanel16.setBackground(new java.awt.Color(235, 235, 235));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableNuevaCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        tableNuevaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripción", "Cantidad", "Precio", "Total"
            }
        ));
        tableNuevaCompra.setRowHeight(23);
        jScrollPane10.setViewportView(tableNuevaCompra);
        if (tableNuevaCompra.getColumnModel().getColumnCount() > 0) {
            tableNuevaCompra.getColumnModel().getColumn(0).setMinWidth(50);
            tableNuevaCompra.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableNuevaCompra.getColumnModel().getColumn(0).setMaxWidth(50);
            tableNuevaCompra.getColumnModel().getColumn(2).setMinWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(2).setPreferredWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(2).setMaxWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(3).setMinWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(3).setPreferredWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(3).setMaxWidth(125);
            tableNuevaCompra.getColumnModel().getColumn(4).setMinWidth(150);
            tableNuevaCompra.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableNuevaCompra.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jPanel16.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 940, 260));

        jLabel34.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(43, 147, 72));
        jLabel34.setText("Código");
        jPanel16.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 30));

        jLabel35.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(43, 147, 72));
        jLabel35.setText("Producto");
        jPanel16.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, 30));

        jLabel36.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(43, 147, 72));
        jLabel36.setText("Cant.");
        jPanel16.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, 30));

        jLabel37.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(43, 147, 72));
        jLabel37.setText("Precio");
        jPanel16.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, -1, 30));

        jLabel38.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(43, 147, 72));
        jLabel38.setText("Total");
        jPanel16.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 100, -1, 30));
        jPanel16.add(txtCantNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 60, 30));

        txtPrecioNC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioNCActionPerformed(evt);
            }
        });
        jPanel16.add(txtPrecioNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 80, 30));
        jPanel16.add(txtTotalNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, 80, 30));

        btnGenerarCompra.setBackground(new java.awt.Color(43, 147, 72));
        btnGenerarCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnGenerarCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/printerBlanco.png"))); // NOI18N
        btnGenerarCompra.setText("Generar Compra");
        btnGenerarCompra.setFocusPainted(false);
        jPanel16.add(btnGenerarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 440, -1, 50));

        jLabel40.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(43, 147, 72));
        jLabel40.setText("Proveedor");
        jPanel16.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, 30));

        cbxProveedorNC.setEditable(true);
        jPanel16.add(cbxProveedorNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 210, 30));

        txtPagarConNC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPagarConNCActionPerformed(evt);
            }
        });
        jPanel16.add(txtPagarConNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, 90, 30));
        jPanel16.add(txtVueltoNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 450, 120, 30));

        labelDolarAnterior.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        labelDolarAnterior.setForeground(new java.awt.Color(43, 147, 72));
        labelDolarAnterior.setText("---------");
        jPanel16.add(labelDolarAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 360, 100, 30));

        labelTotalCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelTotalCompra.setForeground(new java.awt.Color(43, 147, 72));
        labelTotalCompra.setText("-----------");
        jPanel16.add(labelTotalCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 430, 100, 30));

        jLabel42.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(43, 147, 72));
        jLabel42.setText("Pagar con");
        jPanel16.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, -1, 30));

        jLabel43.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(43, 147, 72));
        jLabel43.setText("Vuelto");
        jPanel16.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 450, -1, 30));
        jPanel16.add(txtIDNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 50, 30));

        btncompras.setBackground(new java.awt.Color(43, 147, 72));
        btncompras.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btncompras.setForeground(new java.awt.Color(255, 255, 255));
        btncompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/comprasBlancas.png"))); // NOI18N
        btncompras.setText("Compras");
        btncompras.setFocusPainted(false);
        jPanel16.add(btncompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(967, 110, 170, 50));

        jLabel63.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(43, 147, 72));
        jLabel63.setText("| Nueva Compra |");
        jPanel16.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        jLabel61.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(43, 147, 72));
        jLabel61.setText("Total en $$");
        jPanel16.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, -1, 30));

        jLabel71.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(43, 147, 72));
        jLabel71.setText("| Precio Ant. |");
        jPanel16.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 320, -1, 30));

        labelDolar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        labelDolar.setForeground(new java.awt.Color(43, 147, 72));
        labelDolar.setText("---------");
        jPanel16.add(labelDolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 260, 100, 30));

        jLabel81.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(43, 147, 72));
        jLabel81.setText("| Dólar BCV |");
        jPanel16.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 220, -1, 30));

        jLabel85.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(43, 147, 72));
        jLabel85.setText("Estado:");
        jPanel16.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 180, -1, 30));

        labelEstadoDolarCompra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelEstadoDolarCompra.setForeground(new java.awt.Color(43, 147, 72));
        jPanel16.add(labelEstadoDolarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 180, 100, 30));

        txtCodNC.setEditable(true);
        jPanel16.add(txtCodNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, 30));

        btnLimpiarTablaTemporalCompras.setBackground(new java.awt.Color(43, 147, 72));
        btnLimpiarTablaTemporalCompras.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnLimpiarTablaTemporalCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarTablaTemporalCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/basura.png"))); // NOI18N
        btnLimpiarTablaTemporalCompras.setText("Quitar Prod.");
        btnLimpiarTablaTemporalCompras.setFocusPainted(false);
        jPanel16.add(btnLimpiarTablaTemporalCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 170, 50));

        jLabel90.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(43, 147, 72));
        jLabel90.setText("Total a Pagar");
        jPanel16.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 430, -1, 30));

        labelTotalCompraDolares.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labelTotalCompraDolares.setForeground(new java.awt.Color(43, 147, 72));
        labelTotalCompraDolares.setText("-----------");
        jPanel16.add(labelTotalCompraDolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 460, 100, 30));

        txtProductoNC.setEditable(true);
        txtProductoNC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductoNCActionPerformed(evt);
            }
        });
        jPanel16.add(txtProductoNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 270, 30));

        jTabbedPane1.addTab("Nueva Compra", jPanel16);

        jPanel14.setBackground(new java.awt.Color(235, 235, 235));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(250, 251, 253));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Caja", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel56.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(43, 147, 72));
        jLabel56.setText("Nombre");
        jPanel24.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));
        jPanel24.add(txtNombreCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 190, 30));

        btnNuevoCaja.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoCaja.setText("Nuevo");
        btnNuevoCaja.setFocusPainted(false);
        jPanel24.add(btnNuevoCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 130, 35));

        btnRegistrarCaja.setBackground(new java.awt.Color(43, 147, 72));
        btnRegistrarCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnRegistrarCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnRegistrarCaja.setText("Registrar");
        btnRegistrarCaja.setFocusPainted(false);
        jPanel24.add(btnRegistrarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 140, 35));
        jPanel24.add(txtBuscarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 100, 30));

        btnModificarCaja.setBackground(new java.awt.Color(43, 147, 72));
        btnModificarCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnModificarCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnModificarCaja.setText("Modificar");
        btnModificarCaja.setFocusPainted(false);
        jPanel24.add(btnModificarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 150, 35));
        jPanel24.add(txtIDCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 90, 30));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel24.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 30, 30));

        jPanel14.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 430));

        tableCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado"
            }
        ));
        tableCaja.setComponentPopupMenu(JPopupMenuCajas);
        tableCaja.setRowHeight(23);
        jScrollPane15.setViewportView(tableCaja);
        if (tableCaja.getColumnModel().getColumnCount() > 0) {
            tableCaja.getColumnModel().getColumn(0).setMinWidth(100);
            tableCaja.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableCaja.getColumnModel().getColumn(0).setMaxWidth(100);
            tableCaja.getColumnModel().getColumn(1).setMinWidth(300);
            tableCaja.getColumnModel().getColumn(1).setPreferredWidth(300);
            tableCaja.getColumnModel().getColumn(1).setMaxWidth(300);
            tableCaja.getColumnModel().getColumn(2).setMinWidth(150);
            tableCaja.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableCaja.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        jPanel14.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 550, 430));

        jTabbedPane1.addTab("Cajas", jPanel14);

        jPanel17.setBackground(new java.awt.Color(235, 235, 235));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setBackground(new java.awt.Color(250, 251, 253));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Apertura y Cierre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel59.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(43, 147, 72));
        jLabel59.setText("Monto Inicial");
        jPanel25.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 30));
        jPanel25.add(txtMontoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, 30));

        btnNuevoApertura.setBackground(new java.awt.Color(43, 147, 72));
        btnNuevoApertura.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnNuevoApertura.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoApertura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnNuevoApertura.setText("Nuevo");
        btnNuevoApertura.setFocusPainted(false);
        jPanel25.add(btnNuevoApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 130, 50));

        btnAbrirCaja.setBackground(new java.awt.Color(43, 147, 72));
        btnAbrirCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnAbrirCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/open-removebg-preview.png"))); // NOI18N
        btnAbrirCaja.setText("Abrir");
        btnAbrirCaja.setFocusPainted(false);
        jPanel25.add(btnAbrirCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, -1, 50));

        btnCerrarCaja.setBackground(new java.awt.Color(43, 147, 72));
        btnCerrarCaja.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnCerrarCaja.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/closed-removebg-preview.png"))); // NOI18N
        btnCerrarCaja.setText("Cerrar");
        btnCerrarCaja.setFocusPainted(false);
        jPanel25.add(btnCerrarCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 140, 50));
        jPanel25.add(txtBuscarApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 90, 30));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/lupa.png"))); // NOI18N
        jPanel25.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 30, 30));

        jPanel17.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 450, 200));

        tableApertura.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableApertura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha Apertura", "Fecha Cierre", "Mto. Inicial", "Total General Ventas (Del Usuario)", "Cant. Ventas", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableApertura.setRowHeight(23);
        jScrollPane16.setViewportView(tableApertura);
        if (tableApertura.getColumnModel().getColumnCount() > 0) {
            tableApertura.getColumnModel().getColumn(0).setMinWidth(145);
            tableApertura.getColumnModel().getColumn(0).setPreferredWidth(145);
            tableApertura.getColumnModel().getColumn(0).setMaxWidth(145);
            tableApertura.getColumnModel().getColumn(1).setMinWidth(145);
            tableApertura.getColumnModel().getColumn(1).setPreferredWidth(145);
            tableApertura.getColumnModel().getColumn(1).setMaxWidth(145);
            tableApertura.getColumnModel().getColumn(2).setMinWidth(80);
            tableApertura.getColumnModel().getColumn(2).setPreferredWidth(80);
            tableApertura.getColumnModel().getColumn(2).setMaxWidth(80);
            tableApertura.getColumnModel().getColumn(4).setMinWidth(100);
            tableApertura.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableApertura.getColumnModel().getColumn(4).setMaxWidth(100);
            tableApertura.getColumnModel().getColumn(5).setMinWidth(125);
            tableApertura.getColumnModel().getColumn(5).setPreferredWidth(125);
            tableApertura.getColumnModel().getColumn(5).setMaxWidth(125);
        }

        jPanel17.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 950, 280));

        jTabbedPane1.addTab("Apertura y Cierre", jPanel17);

        jPanel27.setBackground(new java.awt.Color(235, 235, 235));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel28.setBackground(new java.awt.Color(250, 251, 253));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cambiar Contraseña", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCambiarContra.setBackground(new java.awt.Color(43, 147, 72));
        btnCambiarContra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnCambiarContra.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnCambiarContra.setText("Cambiar");
        btnCambiarContra.setFocusPainted(false);
        btnCambiarContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContraActionPerformed(evt);
            }
        });
        jPanel28.add(btnCambiarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 150, 35));

        jLabel73.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(43, 147, 72));
        jLabel73.setText("Confirmar Contraseña");
        jPanel28.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, 30));

        jLabel74.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(43, 147, 72));
        jLabel74.setText("Nueva Contraseña");
        jPanel28.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, 30));
        jPanel28.add(nuevaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 160, 35));
        jPanel28.add(confirmarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 160, 35));

        btnLimpiarContra.setBackground(new java.awt.Color(43, 147, 72));
        btnLimpiarContra.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnLimpiarContra.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnLimpiarContra.setText("Nuevo");
        jPanel28.add(btnLimpiarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 120, 35));
        jPanel28.add(logoCambiarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 46, 170, 120));

        jPanel27.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 320, 430));

        jTabbedPane1.addTab("Cambiar Contraseña", jPanel27);

        jPanel26.setBackground(new java.awt.Color(235, 235, 235));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel29.setBackground(new java.awt.Color(255, 102, 102));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/catBlanco.png"))); // NOI18N
        jPanel29.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, 30));

        jLabel67.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Productos");
        jPanel29.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 80, -1));

        labeltotalproductos.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labeltotalproductos.setForeground(new java.awt.Color(255, 255, 255));
        labeltotalproductos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel29.add(labeltotalproductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 30));

        jPanel26.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 140, 110));

        jPanel30.setBackground(new java.awt.Color(102, 102, 255));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Clientes");
        jPanel30.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 20));

        jLabel76.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/clientesBlancos.png"))); // NOI18N
        jPanel30.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 40, 40));

        labeltotalclientes.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labeltotalclientes.setForeground(new java.awt.Color(255, 255, 255));
        labeltotalclientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel30.add(labeltotalclientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 30));

        jPanel26.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 140, 110));

        jPanel31.setBackground(new java.awt.Color(102, 255, 102));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Compras");
        jPanel31.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 70, -1));

        labeltotalcompras.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labeltotalcompras.setForeground(new java.awt.Color(255, 255, 255));
        labeltotalcompras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel31.add(labeltotalcompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 30));

        jLabel77.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/comprasBlancas.png"))); // NOI18N
        jPanel31.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 40, 40));

        jPanel26.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 140, 110));

        jPanel32.setBackground(new java.awt.Color(255, 255, 102));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Ventas");
        jPanel32.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 60, -1));

        labeltotalventas.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        labeltotalventas.setForeground(new java.awt.Color(255, 255, 255));
        labeltotalventas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel32.add(labeltotalventas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 60, 30));

        jLabel68.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ventasBlancas.png"))); // NOI18N
        jPanel32.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 40, 40));

        jPanel26.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 140, 110));

        javax.swing.GroupLayout panelbarrasLayout = new javax.swing.GroupLayout(panelbarras);
        panelbarras.setLayout(panelbarrasLayout);
        panelbarrasLayout.setHorizontalGroup(
            panelbarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelbarrasLayout.setVerticalGroup(
            panelbarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel26.add(panelbarras, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 500, 300));

        javax.swing.GroupLayout panelpieLayout = new javax.swing.GroupLayout(panelpie);
        panelpie.setLayout(panelpieLayout);
        panelpieLayout.setHorizontalGroup(
            panelpieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelpieLayout.setVerticalGroup(
            panelpieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel26.add(panelpie, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, 500, 300));

        prueba.setBackground(new java.awt.Color(43, 147, 72));
        prueba.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        prueba.setForeground(new java.awt.Color(255, 255, 255));
        prueba.setText("Generar Gráficos");
        prueba.setFocusPainted(false);
        prueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pruebaActionPerformed(evt);
            }
        });
        jPanel26.add(prueba, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 140, 170, 40));

        jTabbedPane1.addTab("Estadística", jPanel26);

        jPanel33.setBackground(new java.awt.Color(235, 235, 235));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel34.setBackground(new java.awt.Color(250, 251, 253));
        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cambiar Contraseña", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Rounded MT Bold", 1, 24), new java.awt.Color(43, 147, 72))); // NOI18N
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCambiarContraGeneral.setBackground(new java.awt.Color(43, 147, 72));
        btnCambiarContraGeneral.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnCambiarContraGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarContraGeneral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnCambiarContraGeneral.setText("Cambiar");
        btnCambiarContraGeneral.setFocusPainted(false);
        btnCambiarContraGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContraGeneralActionPerformed(evt);
            }
        });
        jPanel34.add(btnCambiarContraGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 150, 35));

        jLabel87.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(43, 147, 72));
        jLabel87.setText("Confirmar Contraseña");
        jPanel34.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, 30));

        labelUsuarioSeleccionado.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        labelUsuarioSeleccionado.setForeground(new java.awt.Color(43, 147, 72));
        labelUsuarioSeleccionado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel34.add(labelUsuarioSeleccionado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 240, 30));
        jPanel34.add(nuevaContraGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 160, 35));
        jPanel34.add(confirmarContraGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 160, 35));

        btnLimpiarContraGeneral.setBackground(new java.awt.Color(43, 147, 72));
        btnLimpiarContraGeneral.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnLimpiarContraGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarContraGeneral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btnLimpiarContraGeneral.setText("Limpiar");
        jPanel34.add(btnLimpiarContraGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 120, 35));
        jPanel34.add(logoCambiarContraGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 46, 170, 120));
        jPanel34.add(txtIDUserGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 50, 30));

        jLabel89.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(43, 147, 72));
        jLabel89.setText("Nueva Contraseña");
        jPanel34.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, 30));

        jPanel33.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 320, 470));

        tableUsersGenerales.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        tableUsersGenerales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Usuario", "Nombre", "Contraseña", "Rol", "Estado"
            }
        ));
        tableUsersGenerales.setRowHeight(23);
        jScrollPane17.setViewportView(tableUsersGenerales);
        if (tableUsersGenerales.getColumnModel().getColumnCount() > 0) {
            tableUsersGenerales.getColumnModel().getColumn(0).setMinWidth(50);
            tableUsersGenerales.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableUsersGenerales.getColumnModel().getColumn(0).setMaxWidth(50);
            tableUsersGenerales.getColumnModel().getColumn(1).setMinWidth(90);
            tableUsersGenerales.getColumnModel().getColumn(1).setPreferredWidth(90);
            tableUsersGenerales.getColumnModel().getColumn(1).setMaxWidth(90);
            tableUsersGenerales.getColumnModel().getColumn(2).setMinWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(2).setPreferredWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(2).setMaxWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(4).setMinWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(4).setPreferredWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(4).setMaxWidth(125);
            tableUsersGenerales.getColumnModel().getColumn(5).setMinWidth(115);
            tableUsersGenerales.getColumnModel().getColumn(5).setPreferredWidth(115);
            tableUsersGenerales.getColumnModel().getColumn(5).setMaxWidth(115);
        }

        jPanel33.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 680, 470));

        jTabbedPane1.addTab("Cambiar Todas Contras", jPanel33);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 1150, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreCortoMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCortoMedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCortoMedActionPerformed

    private void txtPrecioNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioNVActionPerformed

    private void txtPrecioNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioNCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioNCActionPerformed

    private void JMenuReingresarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuReingresarUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenuReingresarUserActionPerformed

    private void txtBuscarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarUserActionPerformed

    private void txtIDCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDCliActionPerformed

    private void JMenuEliminarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuEliminarProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenuEliminarProvActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnModificarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarProvActionPerformed

    private void txtIDUsuarioRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDUsuarioRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDUsuarioRolActionPerformed

    private void btnaycActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaycActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaycActionPerformed

    private void txtIDEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDEmpresaActionPerformed

    private void txtPagarConNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPagarConNCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagarConNCActionPerformed

    private void pruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pruebaActionPerformed
        //Grafico de barras
        int n1 = Integer.parseInt(labeltotalproductos.getText());
        int n2 = Integer.parseInt(labeltotalclientes.getText());
        int n3 = Integer.parseInt(labeltotalcompras.getText());
        int n4 = Integer.parseInt(labeltotalventas.getText());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(n1, "Productos", "");
        dataset.addValue(n2, "Clientes", "");
        dataset.addValue(n3, "Compras", "");
        dataset.addValue(n4, "Ventas", "");

        JFreeChart grafico_barras = ChartFactory.createBarChart3D(
                "Estadísticas de la Empresa",
                "",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel panel = new ChartPanel(grafico_barras);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400, 295));

        panelbarras.setLayout(new BorderLayout());
        panelbarras.add(panel, BorderLayout.NORTH);

        pack();
        repaint();

        //Grafico de Pie
        int num1 = Integer.parseInt(labeltotalproductos.getText());
        int num2 = Integer.parseInt(labeltotalclientes.getText());
        int num3 = Integer.parseInt(labeltotalcompras.getText());
        int num4 = Integer.parseInt(labeltotalventas.getText());

        DefaultPieDataset dataset1 = new DefaultPieDataset();

        dataset1.setValue("Productos", num1);
        dataset1.setValue("Clientes", num2);
        dataset1.setValue("Compras", num3);
        dataset1.setValue("Ventas", num4);

        JFreeChart grafico_circular = ChartFactory.createPieChart(
                "Estadísticas de la Empresa Circular",
                dataset1,
                true,
                true,
                false
        );

        ChartPanel panel2 = new ChartPanel(grafico_circular);
        panel2.setMouseWheelEnabled(true);
        panel2.setPreferredSize(new Dimension(400, 295));

        panelpie.setLayout(new BorderLayout());
        panelpie.add(panel2, BorderLayout.NORTH);

        pack();
        repaint();
    }//GEN-LAST:event_pruebaActionPerformed

    private void btnCambiarContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarContraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCambiarContraActionPerformed

    private void btnModificarEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarEmpresaActionPerformed

    private void txtIDprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDprovActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDprovActionPerformed

    private void txtTelefonoProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProvActionPerformed

    private void txtCorreoProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoProvActionPerformed

    private void btnCambiarContraGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarContraGeneralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCambiarContraGeneralActionPerformed

    private void btnUsers1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsers1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsers1ActionPerformed

    private void btnModoOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModoOscuroActionPerformed

    }//GEN-LAST:event_btnModoOscuroActionPerformed

    private void txtTotalNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalNVActionPerformed

    private void txtProductoNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductoNCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductoNCActionPerformed

    private void btnAbrirModalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirModalActionPerformed
        
    }//GEN-LAST:event_btnAbrirModalActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanelAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem JMenuEliminarCaja;
    public javax.swing.JMenuItem JMenuEliminarCat;
    public javax.swing.JMenuItem JMenuEliminarCli;
    public javax.swing.JMenuItem JMenuEliminarMedida;
    public javax.swing.JMenuItem JMenuEliminarPro;
    public javax.swing.JMenuItem JMenuEliminarProv;
    public javax.swing.JMenuItem JMenuEliminarUser;
    public javax.swing.JMenuItem JMenuReingresarCaja;
    public javax.swing.JMenuItem JMenuReingresarCat;
    public javax.swing.JMenuItem JMenuReingresarCli;
    public javax.swing.JMenuItem JMenuReingresarMedida;
    public javax.swing.JMenuItem JMenuReingresarPro;
    public javax.swing.JMenuItem JMenuReingresarProv;
    public javax.swing.JMenuItem JMenuReingresarUser;
    private javax.swing.JPopupMenu JPopupClientes;
    private javax.swing.JPopupMenu JPopupMenuCajas;
    private javax.swing.JPopupMenu JPopupMenuCategorias;
    private javax.swing.JPopupMenu JPopupMenuMedidas;
    private javax.swing.JPopupMenu JPopupMenuProductos;
    private javax.swing.JPopupMenu JPopupProveedores;
    private javax.swing.JPopupMenu JPopupUsuarios;
    public javax.swing.JButton btnAbrirCaja;
    public javax.swing.JButton btnAbrirModal;
    public javax.swing.JButton btnCambiarContra;
    public javax.swing.JButton btnCambiarContraGeneral;
    public javax.swing.JButton btnCerrarCaja;
    public javax.swing.JButton btnGenerarCompra;
    public javax.swing.JButton btnGenerarVenta;
    public javax.swing.JButton btnHistorialCompra;
    public javax.swing.JButton btnHistorialVentas;
    public javax.swing.JButton btnLimpiarContra;
    public javax.swing.JButton btnLimpiarContraGeneral;
    public javax.swing.JButton btnLimpiarTablaTemporal;
    public javax.swing.JButton btnLimpiarTablaTemporalCompras;
    public javax.swing.JButton btnModificarCaja;
    public javax.swing.JButton btnModificarCat;
    public javax.swing.JButton btnModificarCli;
    public javax.swing.JButton btnModificarEmpresa;
    public javax.swing.JButton btnModificarMedida;
    public javax.swing.JButton btnModificarPro;
    public javax.swing.JButton btnModificarProv;
    public javax.swing.JButton btnModificarUser;
    private javax.swing.JToggleButton btnModoOscuro;
    public javax.swing.JButton btnNuevoApertura;
    public javax.swing.JButton btnNuevoCaja;
    public javax.swing.JButton btnNuevoCat;
    public javax.swing.JButton btnNuevoCli;
    public javax.swing.JButton btnNuevoCliV;
    public javax.swing.JButton btnNuevoMedida;
    public javax.swing.JButton btnNuevoPro;
    public javax.swing.JButton btnNuevoProv;
    public javax.swing.JButton btnNuevoUser;
    public javax.swing.JButton btnRegistrarCaja;
    public javax.swing.JButton btnRegistrarCat;
    public javax.swing.JButton btnRegistrarCli;
    public javax.swing.JButton btnRegistrarCliV;
    public javax.swing.JButton btnRegistrarMedida;
    public javax.swing.JButton btnRegistrarPro;
    public javax.swing.JButton btnRegistrarProv;
    public javax.swing.JButton btnRegistrarUser;
    private javax.swing.JButton btnUsers;
    public javax.swing.JButton btnUsers1;
    public javax.swing.JButton btnayc;
    public javax.swing.JButton btncompras;
    public javax.swing.JButton btnventas;
    public javax.swing.JComboBox<Object> cbxCajaUser;
    public javax.swing.JComboBox<Object> cbxCatPro;
    public javax.swing.JComboBox<Object> cbxClienteVentas;
    public javax.swing.JComboBox<Object> cbxMedidaPro;
    public javax.swing.JComboBox<Object> cbxProveedorNC;
    public javax.swing.JComboBox<Object> cbxProveedorPro;
    public javax.swing.JComboBox<String> cbxRolUser;
    public javax.swing.JPasswordField confirmarContra;
    public javax.swing.JPasswordField confirmarContraGeneral;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel13;
    public javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    public javax.swing.JPanel jPanel16;
    public javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JLabel labelCaja;
    public javax.swing.JLabel labelCategorias;
    public javax.swing.JLabel labelClientes;
    public javax.swing.JLabel labelConfig;
    public javax.swing.JLabel labelDolar;
    private javax.swing.JLabel labelDolarAnterior;
    public javax.swing.JLabel labelDolarVenta;
    private javax.swing.JLabel labelDolarVentaAnterior;
    public javax.swing.JLabel labelEstadisticas;
    private javax.swing.JLabel labelEstadoDolar;
    private javax.swing.JLabel labelEstadoDolarCompra;
    public javax.swing.JLabel labelMedidas;
    public javax.swing.JLabel labelNuevaCompra;
    public javax.swing.JLabel labelNuevaVenta;
    public javax.swing.JLabel labelProductos;
    public javax.swing.JLabel labelProveedores;
    public javax.swing.JLabel labelRolUsuarioConectado;
    public javax.swing.JLabel labelTotalCompra;
    public javax.swing.JLabel labelTotalCompraDolares;
    public javax.swing.JLabel labelTotalPagar;
    public javax.swing.JLabel labelTotalPagarDolares;
    public javax.swing.JLabel labelUsers;
    public javax.swing.JLabel labelUsuarioSeleccionado;
    public javax.swing.JLabel labeltotalclientes;
    public javax.swing.JLabel labeltotalcompras;
    public javax.swing.JLabel labeltotalproductos;
    public javax.swing.JLabel labeltotalventas;
    public javax.swing.JLabel logoCambiarContra;
    public javax.swing.JLabel logoCambiarContraGeneral;
    public javax.swing.JPasswordField nuevaContra;
    public javax.swing.JPasswordField nuevaContraGeneral;
    public javax.swing.JPanel panelCaja;
    public javax.swing.JPanel panelCategorias;
    public javax.swing.JPanel panelClientes;
    public javax.swing.JPanel panelConfig;
    public javax.swing.JPanel panelMedidas;
    public javax.swing.JPanel panelNuevaCompra;
    public javax.swing.JPanel panelNuevaVenta;
    public javax.swing.JPanel panelProductos;
    public javax.swing.JPanel panelProveedores;
    public javax.swing.JPanel panelUsers;
    public javax.swing.JPanel panelbarras;
    public javax.swing.JPanel panelpie;
    public javax.swing.JButton prueba;
    public javax.swing.JTable tableApertura;
    public javax.swing.JTable tableCaja;
    public javax.swing.JTable tableCat;
    public javax.swing.JTable tableClientes;
    public javax.swing.JTable tableCompras;
    public javax.swing.JTable tableMedida;
    public javax.swing.JTable tableNuevaCompra;
    public javax.swing.JTable tableNuevaVenta;
    public javax.swing.JTable tableProductos;
    public javax.swing.JTable tableProveedor;
    public javax.swing.JTable tableUsers;
    public javax.swing.JTable tableUsersGenerales;
    public javax.swing.JTable tableVentas;
    public javax.swing.JTextField txtBuscarApertura;
    public javax.swing.JTextField txtBuscarCaja;
    public javax.swing.JTextField txtBuscarCat;
    public javax.swing.JTextField txtBuscarCli;
    public javax.swing.JTextField txtBuscarCompra;
    public javax.swing.JTextField txtBuscarMed;
    public javax.swing.JTextField txtBuscarProducto;
    public javax.swing.JTextField txtBuscarProv;
    public javax.swing.JTextField txtBuscarUser;
    public javax.swing.JTextField txtBuscarVenta;
    public javax.swing.JTextField txtCantNC;
    public javax.swing.JTextField txtCantNV;
    public javax.swing.JPasswordField txtClaveUser;
    public javax.swing.JComboBox<Object> txtCodNC;
    public javax.swing.JComboBox<Object> txtCodNV;
    public javax.swing.JTextField txtCodigoPro;
    public javax.swing.JTextField txtCorreoCli;
    public javax.swing.JTextField txtCorreoCliV;
    public javax.swing.JTextField txtCorreoEmpresa;
    public javax.swing.JTextField txtCorreoProv;
    public javax.swing.JTextField txtDescripcionPro;
    public javax.swing.JTextPane txtDireccionCli;
    public javax.swing.JTextPane txtDireccionCliV;
    public javax.swing.JTextPane txtDireccionEmpresa;
    public javax.swing.JTextPane txtDireccionProv;
    public javax.swing.JTextField txtIDCaja;
    public javax.swing.JTextField txtIDCli;
    public javax.swing.JTextField txtIDCompra;
    public javax.swing.JTextField txtIDEmpresa;
    public javax.swing.JTextField txtIDNC;
    public javax.swing.JTextField txtIDNV;
    public javax.swing.JTextField txtIDUserGeneral;
    public javax.swing.JTextField txtIDUsuarioRol;
    public javax.swing.JTextField txtIDVentas;
    public javax.swing.JTextField txtIDcat;
    public javax.swing.JTextField txtIDmed;
    public javax.swing.JTextField txtIDpro;
    public javax.swing.JTextField txtIDprov;
    public javax.swing.JTextField txtIDuser;
    public javax.swing.JTextField txtIGEmpresa;
    public javax.swing.JTextPane txtMensaje;
    public javax.swing.JTextField txtMontoInicial;
    public javax.swing.JTextField txtNombreCaja;
    public javax.swing.JTextField txtNombreCat;
    public javax.swing.JTextField txtNombreCli;
    public javax.swing.JTextField txtNombreCliV;
    public javax.swing.JTextField txtNombreCortoMed;
    public javax.swing.JTextField txtNombreEmpresa;
    public javax.swing.JTextField txtNombreMedida;
    public javax.swing.JTextField txtNombreProv;
    public javax.swing.JTextField txtNombreUser;
    public javax.swing.JTextField txtPagarConNC;
    public javax.swing.JTextField txtPagarConNV;
    public javax.swing.JTextField txtPrecioCompraPro;
    public javax.swing.JTextField txtPrecioNC;
    public javax.swing.JTextField txtPrecioNV;
    public javax.swing.JTextField txtPrecioVentaPro;
    public javax.swing.JComboBox<Object> txtProductoNC;
    public javax.swing.JComboBox<Object> txtProductoNV;
    public javax.swing.JTextField txtRucEmpresa;
    public javax.swing.JTextField txtRucProveedor;
    public javax.swing.JTextField txtStockNV;
    public javax.swing.JTextField txtTelefonoCli;
    public javax.swing.JTextField txtTelefonoCliV;
    public javax.swing.JTextField txtTelefonoEmpresa;
    public javax.swing.JTextField txtTelefonoProv;
    public javax.swing.JTextField txtTotalNC;
    public javax.swing.JTextField txtTotalNV;
    public javax.swing.JTextField txtUsuarioUser;
    public javax.swing.JTextField txtVueltoNC;
    public javax.swing.JTextField txtVueltoNV;
    public javax.swing.JDialog ventanaRegCliente;
    // End of variables declaration//GEN-END:variables
}