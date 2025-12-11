package Controllers;

import Models.Combo;
import Models.Medidas;
import Models.MedidasDao;
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

public class MedidasControllers implements ActionListener, MouseListener, KeyListener{
    private Medidas med;
    private MedidasDao medDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public MedidasControllers(Medidas med, MedidasDao medDao, PanelAdmin views) {
        this.med = med;
        this.medDao = medDao;
        this.views = views;
        this.views.btnRegistrarMedida.addActionListener(this);
        this.views.btnModificarMedida.addActionListener(this);
        this.views.btnNuevoMedida.addActionListener(this);
        this.views.tableMedida.addMouseListener(this);
        this.views.JMenuEliminarMedida.addActionListener(this);
        this.views.JMenuReingresarMedida.addActionListener(this);
        this.views.txtBuscarMed.addKeyListener(this);
        this.views.labelMedidas.addMouseListener(this);
        listarMedidas();
        llenarMedida();
        AutoCompleteDecorator.decorate(views.cbxMedidaPro);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnRegistrarMedida){
            if(views.txtNombreMedida.getText().equals("") || views.txtNombreCortoMed.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                med.setNombre(views.txtNombreMedida.getText());               
                med.setNombre_corto(views.txtNombreCortoMed.getText());  
                
                String respuesta = medDao.registrar(med);
                switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Nombre de la Medida debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Registrado":
                        limpiarTable();
                        listarMedidas();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Medida Registrada", "Registrar Medida", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al registrar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        }else if(e.getSource() == views.btnModificarMedida){
            if(views.txtIDmed.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione la fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                if(views.txtNombreMedida.getText().equals("") || views.txtNombreCortoMed.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    med.setNombre(views.txtNombreMedida.getText());
                    med.setNombre_corto(views.txtNombreCortoMed.getText());
                    med.setId(Integer.parseInt(views.txtIDmed.getText()));
                    
                    String respuesta = medDao.modificar(med);
                    switch (respuesta) {
                    case "Existe":
                        JOptionPane.showMessageDialog(null, "El Nombre de la Medida debe ser único", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                    case "Modificado":
                        limpiarTable();
                        listarMedidas();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Medida Modificada", "Modificar Medida", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error al modificar", "ERROR", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                }
            }
        }else if(e.getSource() == views.JMenuEliminarMedida){
            if(views.txtIDmed.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id = Integer.parseInt(views.txtIDmed.getText());
                if(medDao.accion("Inactivo", id)){
                    limpiarTable();
                    listarMedidas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Medida eliminada", "Eliminar Medida", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar medida", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else if(e.getSource() == views.JMenuReingresarMedida){
            if(views.txtIDmed.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                int id = Integer.parseInt(views.txtIDmed.getText());
                if(medDao.accion("Activo", id)){
                    limpiarTable();
                    listarMedidas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Medida reingresada", "Reingresar Medida", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Error al reingresar medida", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            limpiar();
        }
    }
    
    public void limpiarTable(){
        for(int i=0; i<modelo.getRowCount(); i++){
            modelo.removeRow(i);
            i=i-1;
        }
    }
    
    public void listarMedidas(){
        Tables color = new Tables();
        views.tableMedida.setDefaultRenderer(views.tableMedida.getColumnClass(0), color);
        
        List<Medidas> lista = medDao.ListaMedidas(views.txtBuscarMed.getText());
        modelo = (DefaultTableModel) views.tableMedida.getModel();
        Object[] ob= new Object[4];
        for(int i=0; i<lista.size(); i++){
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getNombre_corto();
            ob[3] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        
        views.tableMedida.setModel(modelo);
        JTableHeader header = views.tableMedida.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(43, 147, 72));
        header.setForeground(Color.white);
        
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < views.tableMedida.getColumnCount(); i++) {
            views.tableMedida.getColumnModel().getColumn(i).setCellRenderer(centroRender);
        }
    }
    
    public void limpiar(){
        views.txtBuscarMed.setText("");
        views.txtNombreMedida.setText("");
        views.txtNombreCortoMed.setText("");
        views.txtIDmed.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == views.tableMedida){
            int fila = views.tableMedida.rowAtPoint(e.getPoint());
            views.txtIDmed.setText(views.tableMedida.getValueAt(fila,0).toString());
            views.txtNombreMedida.setText(views.tableMedida.getValueAt(fila,1).toString());
            views.txtNombreCortoMed.setText(views.tableMedida.getValueAt(fila,2).toString());
        }else if(e.getSource() == views.labelMedidas){
            views.jTabbedPane1.setSelectedIndex(5);
            
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
        if(e.getSource() == views.txtBuscarMed){
            limpiarTable();
            listarMedidas();
        }
    }
    
    private void llenarMedida(){
        List<Medidas> lista = medDao.ListaMedidas(views.txtBuscarMed.getText());
        for(int i=0; i<lista.size(); i++){
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            views.cbxMedidaPro.addItem(new Combo(id, nombre));
        }
    }
}
