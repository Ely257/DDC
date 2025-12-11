package Models;

import javax.swing.JComponent;

public class ComponenteRelativo {
    public JComponent componente;
    public int xOriginal, yOriginal;
    public int widthOriginal, heightOriginal;

    public ComponenteRelativo(JComponent componente, int xOriginal, int yOriginal, int widthOriginal, int heightOriginal) {
        this.componente = componente;
        this.xOriginal = xOriginal;
        this.yOriginal = yOriginal;
        this.widthOriginal = widthOriginal;
        this.heightOriginal = heightOriginal;
    }
}

