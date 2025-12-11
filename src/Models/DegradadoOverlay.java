package Models;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DegradadoOverlay {
    public static void aplicarDegradado(JPanel panel, Color color1, Color color2, boolean vertical) {
        panel.setOpaque(false); 
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gp = vertical ? new GradientPaint(0, 0, color1, 0, c.getHeight(), color2) : new GradientPaint(0, 0, color1, c.getWidth(), 0, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                g2d.dispose();
                super.update(g, c);
            }
        });
        panel.repaint();
    }
}
