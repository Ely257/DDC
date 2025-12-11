package Controllers;

import Models.Configuracion;
import Models.UsuariosDao;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfigControllers implements MouseListener, ActionListener {

    private Configuracion cof;
    private UsuariosDao Cdao;
    private PanelAdmin views;
    
    private boolean modoOscuroActivo;
    
    Color colorHoverClaro = new Color(249, 247, 201);
    Color colorFondoClaro = new Color(250, 251, 253);

    Color colorHoverOscuro = new Color(60, 60, 60);
    Color colorFondoOscuro = new Color(30, 30, 30);

    public ConfigControllers(Configuracion cof, UsuariosDao Cdao, PanelAdmin views, boolean modoOscuroActivo) {
        this.cof = cof;
        this.Cdao = Cdao;
        this.views = views;
        this.views.labelCategorias.addMouseListener(this);
        this.views.labelClientes.addMouseListener(this);
        this.views.labelConfig.addMouseListener(this);
        this.views.labelMedidas.addMouseListener(this);
        this.views.labelNuevaCompra.addMouseListener(this);
        this.views.labelNuevaVenta.addMouseListener(this);
        this.views.labelProductos.addMouseListener(this);
        this.views.labelProveedores.addMouseListener(this);
        this.views.labelUsers.addMouseListener(this);
        this.views.labelCaja.addMouseListener(this);
        this.views.btnModificarEmpresa.addActionListener(this);
        
        this.modoOscuroActivo = modoOscuroActivo;
        
        cof = Cdao.getConfig();
        
        views.txtIDEmpresa.setText("" + cof.getId());
        views.txtRucEmpresa.setText(cof.getRuc());
        views.txtTelefonoEmpresa.setText(cof.getTelefono());
        views.txtNombreEmpresa.setText(cof.getNombre());
        views.txtDireccionEmpresa.setText(cof.getDireccion());
        views.txtMensaje.setText(cof.getMensaje());
        views.txtIGEmpresa.setText(cof.getIg());
        views.txtCorreoEmpresa.setText(cof.getCorreo());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.labelConfig) {
            views.jTabbedPane1.setSelectedIndex(9);
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
        if (e.getSource() == views.labelCategorias) {
            aplicarEstiloPanel(views.panelCategorias, true);
        } else if (e.getSource() == views.labelClientes) {
            aplicarEstiloPanel(views.panelClientes, true);
        } else if (e.getSource() == views.labelNuevaVenta) {
            aplicarEstiloPanel(views.panelNuevaVenta, true);
        } else if (e.getSource() == views.labelConfig) {
            aplicarEstiloPanel(views.panelConfig, true);
        } else if (e.getSource() == views.labelMedidas) {
            aplicarEstiloPanel(views.panelMedidas, true);
        } else if (e.getSource() == views.labelNuevaCompra) {
            aplicarEstiloPanel(views.panelNuevaCompra, true);
        } else if (e.getSource() == views.labelProductos) {
            aplicarEstiloPanel(views.panelProductos, true);
        } else if (e.getSource() == views.labelProveedores) {
            aplicarEstiloPanel(views.panelProveedores, true);
        } else if (e.getSource() == views.labelUsers) {
            aplicarEstiloPanel(views.panelUsers, true);
        } else {
            aplicarEstiloPanel(views.panelCaja, true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.labelCategorias) {
            aplicarEstiloPanel(views.panelCategorias, false);
        } else if (e.getSource() == views.labelClientes) {
            aplicarEstiloPanel(views.panelClientes, false);
        } else if (e.getSource() == views.labelNuevaVenta) {
            aplicarEstiloPanel(views.panelNuevaVenta, false);
        } else if (e.getSource() == views.labelConfig) {
            aplicarEstiloPanel(views.panelConfig, false);
        } else if (e.getSource() == views.labelMedidas) {
            aplicarEstiloPanel(views.panelMedidas, false);
        } else if (e.getSource() == views.labelNuevaCompra) {
            aplicarEstiloPanel(views.panelNuevaCompra, false);
        } else if (e.getSource() == views.labelProductos) {
            aplicarEstiloPanel(views.panelProductos, false);
        } else if (e.getSource() == views.labelProveedores) {
            aplicarEstiloPanel(views.panelProveedores, false);
        } else if (e.getSource() == views.labelUsers) {
            aplicarEstiloPanel(views.panelUsers, false);
        } else {
            aplicarEstiloPanel(views.panelCaja, false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnModificarEmpresa) {
            if (views.txtIDEmpresa.getText().equals("") || views.txtRucEmpresa.getText().equals("")
                    || views.txtTelefonoEmpresa.getText().equals("") || views.txtNombreEmpresa.getText().equals("")
                    || views.txtDireccionEmpresa.getText().equals("") || views.txtMensaje.getText().equals("")
                    || views.txtIGEmpresa.getText().equals("") || views.txtCorreoEmpresa.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                cof.setId(Integer.parseInt(views.txtIDEmpresa.getText()));
                cof.setRuc(views.txtRucEmpresa.getText());
                cof.setTelefono(views.txtTelefonoEmpresa.getText());
                cof.setNombre(views.txtNombreEmpresa.getText());
                cof.setDireccion(views.txtDireccionEmpresa.getText());
                cof.setMensaje(views.txtMensaje.getText());
                cof.setIg(views.txtIGEmpresa.getText());
                cof.setCorreo(views.txtCorreoEmpresa.getText());
                
                if(Cdao.modificarDatosEmpresa(cof)){
                    JOptionPane.showMessageDialog(null, "Datos Modificados", "Modificar Datos", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    //Setter
    public void setModoOscuroActivo(boolean activo) {
        this.modoOscuroActivo = activo;
        actualizarColoresPaneles();
    }
    //Getter
    public boolean isModoOscuroActivo() {
        return modoOscuroActivo;
    }
    
    private void actualizarColoresPaneles() {
        Color fondoColor = modoOscuroActivo ? colorFondoOscuro : colorFondoClaro;

        aplicarEstiloPanel(views.panelCategorias, false);
        aplicarEstiloPanel(views.panelClientes, false);
        aplicarEstiloPanel(views.panelNuevaVenta, false);
        aplicarEstiloPanel(views.panelConfig, false);
        aplicarEstiloPanel(views.panelMedidas, false);
        aplicarEstiloPanel(views.panelNuevaCompra, false);
        aplicarEstiloPanel(views.panelProductos, false);
        aplicarEstiloPanel(views.panelProveedores, false);
        aplicarEstiloPanel(views.panelUsers, false);
        aplicarEstiloPanel(views.panelCaja, false);

        views.repaint();
    }
    
    private void aplicarEstiloPanel(JPanel panel, boolean esHover) {
        if (esHover) {
            Color color = modoOscuroActivo ? colorHoverOscuro : colorHoverClaro;
            panel.setOpaque(true);
            panel.setBackground(color);
        } else {
            panel.setOpaque(false); 
            panel.setBackground(new Color(0, 0, 0, 0));
        }
        panel.repaint();
    }
}
