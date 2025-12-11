package Models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Tables extends DefaultTableCellRenderer{
    
    private Color fondoInactivo = Color.RED;
    private Color fondoActivo = Color.WHITE;
    private Color letraInactiva = Color.WHITE;
    private Color letraActiva = Color.BLACK;
    
    public Tables(){
        
    }
    
    public Tables(Color fondoInactivo, Color letraInactiva, Color fondoActivo, Color letraActiva){
        this.fondoInactivo = fondoInactivo;
        this.letraInactiva = letraInactiva;
        this.fondoActivo = fondoActivo;
        this.letraActiva = letraActiva;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int col) {
        super.getTableCellRendererComponent(jtable, o, bln, bln1, row, col);
        if(jtable.getValueAt(row, col).toString().equals("Inactivo")){
            setBackground(fondoInactivo);
            setForeground(letraInactiva);
        }else{
            setBackground(fondoActivo);
            setForeground(letraActiva);
        }
        return this;
    }
}
