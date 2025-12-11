package Models;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Degradado extends JPanel {
    
    private Color color1;
    private Color color2;
    private boolean vertical;

    public Degradado(Color color1, Color color2, boolean vertical) {
        this.color1 = color1;
        this.color2 = color2;
        this.vertical = vertical;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint degradado;
        if (vertical) {
            degradado = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        } else {
            degradado = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
        }

        g2d.setPaint(degradado);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
