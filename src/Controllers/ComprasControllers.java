package Controllers;

import Models.Compras;
import Models.ComprasDao;
import Models.ProductosDao;
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

public class ComprasControllers implements KeyListener, ActionListener, MouseListener{
    private Compras comp;
    private ComprasDao compDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public ComprasControllers(Compras comp, ComprasDao compDao, PanelAdmin views) {
        this.comp = comp;
        this.compDao = compDao;
        this.views = views;
        this.views.txtBuscarCompra.addKeyListener(this);
        this.views.btnHistorialCompra.addActionListener(this);
        this.views.tableCompras.addMouseListener(this);
        this.views.labelEstadisticas.addMouseListener(this);
    }
    
    public void listarCompras(){
        List<Compras> lista = compDao.ListaCompras(views.txtBuscarCompra.getText());
        modelo = (DefaultTableModel) views.tableCompras.getModel();
        Object[] ob= new Object[4];
        for(int i=0; i<lista.size(); i++){
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
        if(e.getSource() == views.txtBuscarCompra){
            limpiarTable();
            listarCompras();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnHistorialCompra){
            if(views.txtIDCompra.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id_compra = Integer.parseInt(views.txtIDCompra.getText());  
                ProductosDao proDao= new ProductosDao();
                proDao.generarReporte(id_compra);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.tableCompras){
            int fila = views.tableCompras.rowAtPoint(e.getPoint());
            views.txtIDCompra.setText(views.tableCompras.getValueAt(fila,0).toString());
        }else if(e.getSource() == views.labelEstadisticas){
            views.jTabbedPane1.setSelectedIndex(14);
            int total = compDao.contarCompras();
            views.labeltotalcompras.setText(""+ total);
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
