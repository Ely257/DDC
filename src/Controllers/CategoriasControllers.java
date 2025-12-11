package Controllers;

import Models.Categorias;
import Models.CategoriasDao;
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

public class CategoriasControllers implements ActionListener, MouseListener, KeyListener{
    private Categorias cat;
    private CategoriasDao catDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public CategoriasControllers(Categorias cat, CategoriasDao catDao, PanelAdmin views) {
        this.cat = cat;
        this.catDao = catDao;
        this.views = views;
        this.views.btnRegistrarCat.addActionListener(this);
        this.views.btnModificarCat.addActionListener(this);
        this.views.btnNuevoCat.addActionListener(this);
        this.views.tableCat.addMouseListener(this);
        this.views.JMenuEliminarCat.addActionListener(this);
        this.views.JMenuReingresarCat.addActionListener(this);
        this.views.txtBuscarCat.addKeyListener(this);
        this.views.labelCategorias.addMouseListener(this);
        llenarCat();
        AutoCompleteDecorator.decorate(views.cbxCatPro);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnRegistrarCat){
            if(views.txtNombreCat.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                cat.setNombre(views.txtNombreCat.getText());  
                
                String respuesta = catDao.registrar(cat);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Nombre de la Categoría debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarCategorias();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Categoría Registrada", "Registrar Categoría", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        }else if(e.getSource() == views.btnModificarCat){
            if(views.txtIDcat.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione la fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                if(views.txtNombreCat.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    cat.setNombre(views.txtNombreCat.getText());
                    cat.setId(Integer.parseInt(views.txtIDcat.getText()));
                    
                    String respuesta = catDao.modificar(cat);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Nombre de la Categoría debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Modificado":
                        limpiarTable();
                        listarCategorias();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Categoría Modificada", "Modificar Categoría", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                }
            }
        }else if(e.getSource() == views.JMenuEliminarCat){
            if(views.txtIDcat.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id = Integer.parseInt(views.txtIDcat.getText());
                if(catDao.accion("Inactivo", id)){
                    limpiarTable();
                    listarCategorias();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Categoría eliminada", "Eliminar Categoría", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar categoría", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else if(e.getSource() == views.JMenuReingresarCat){
            if(views.txtIDcat.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id = Integer.parseInt(views.txtIDcat.getText());
                if(catDao.accion("Activo", id)){
                    limpiarTable();
                    listarCategorias();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Categoría reingresada", "Reingresar Categoría", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al reingresar categoría", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            limpiar();
        }
    }
    
    public void listarCategorias(){
        Tables color = new Tables();
        views.tableCat.setDefaultRenderer(views.tableCat.getColumnClass(0), color);
        
        List<Categorias> lista = catDao.ListaCategorias(views.txtBuscarCat.getText());
        modelo = (DefaultTableModel) views.tableCat.getModel();
        Object[] ob= new Object[3];
        for(int i=0; i<lista.size(); i++){
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        
        views.tableCat.setModel(modelo);
        JTableHeader header = views.tableCat.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableCat.getColumnCount(); i++) {
            views.tableCat.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }
    
    public void limpiarTable(){
        for(int i=0; i<modelo.getRowCount(); i++){
            modelo.removeRow(i);
            i=i-1;
        }
    }
    
    public void limpiar(){
        views.txtBuscarCat.setText("");
        views.txtIDcat.setText("");
        views.txtNombreCat.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.tableCat){
            int fila = views.tableCat.rowAtPoint(e.getPoint());
            views.txtIDcat.setText(views.tableCat.getValueAt(fila,0).toString());
            views.txtNombreCat.setText(views.tableCat.getValueAt(fila,1).toString());
        }else if(e.getSource() == views.labelCategorias){
            views.jTabbedPane1.setSelectedIndex(4);
            limpiarTable();
            listarCategorias();
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
        if(e.getSource() == views.txtBuscarCat){
            limpiarTable();
            listarCategorias();
        }
    }
    
    private void llenarCat(){
        List<Categorias> lista = catDao.ListaCategorias(views.txtBuscarCat.getText());
        for(int i=0; i<lista.size(); i++){
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxCatPro.addItem(new Combo(id, nombre));
        }
    }
}
