package Controllers;

import Models.ProductosDao;
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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class VentasControllers implements KeyListener, ActionListener, MouseListener{
    private Ventas ven;
    private VentasDao venDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public VentasControllers(Ventas ven, VentasDao venDao, PanelAdmin views) {
        this.ven = ven;
        this.venDao = venDao;
        this.views = views;
        this.views.txtBuscarVenta.addKeyListener(this);
        this.views.btnHistorialVentas.addActionListener(this);
        this.views.tableVentas.addMouseListener(this);
        this.views.labelEstadisticas.addMouseListener(this);
    }
    
    public void listarVentas(){
        List<Ventas> lista = venDao.ListaVentas(views.txtBuscarVenta.getText());
        modelo = (DefaultTableModel) views.tableVentas.getModel();
        Object[] ob= new Object[4];
        for(int i=0; i<lista.size(); i++){
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
    
    public void limpiarTable(){
        for(int i=0; i<modelo.getRowCount(); i++){
            modelo.removeRow(i);
            i=i-1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == views.txtBuscarVenta){
            limpiarTable();
            listarVentas();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnHistorialVentas){
            if(views.txtIDVentas.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id_venta = Integer.parseInt(views.txtIDVentas.getText());
                ProductosDao proDao= new ProductosDao();
                try {
                    proDao.generarReporteVenta(id_venta);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.tableVentas){
            int fila = views.tableVentas.rowAtPoint(e.getPoint());
            views.txtIDVentas.setText(views.tableVentas.getValueAt(fila,0).toString());
        }else if(e.getSource() == views.labelEstadisticas){
            views.jTabbedPane1.setSelectedIndex(14);
            int total = venDao.contarVentas();
            views.labeltotalventas.setText(""+ total);
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
}
