package Views;

import Controllers.LoginControllers;
import Models.Degradado;
import Models.Usuarios;
import Models.UsuariosDao;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class FrmLogin extends javax.swing.JFrame {
    Usuarios us = new Usuarios();
    UsuariosDao usDao = new UsuariosDao();
    
    private boolean modoOscuro = true;
    
    private Degradado panelPrincipal;
    private Degradado panel1a;
    private Degradado panel1b;
            
    public FrmLogin() {
        FlatDarkLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        
        initComponents();
        this.setLocationRelativeTo(null);
        LoginControllers users = new LoginControllers(us, usDao, this, true);
        
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
                
                boolean nuevoModoOscuro = !users.isModoOscuroActivo();
                users.setModoOscuroActivo(nuevoModoOscuro);
            }
        });
        
        ImageIcon imagen1 = new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondo1 = new ImageIcon(imagen1.getImage().getScaledInstance(img_ag.getWidth(), img_ag.getHeight(), Image.SCALE_DEFAULT));
        img_ag.setIcon(fondo1);
        this.repaint();
        
        aplicarModoOscuro();
    }
    
    public void aplicarModoOscuro() {
        
        Color negroCasiFull = new Color(40, 40, 40);
        Color grisMenosDeCarbon = new Color(120, 120, 120);
        Color negroMuyMuyFull = new Color(20, 20, 20);
        Color negroBastanteFull = new Color(30, 30, 30);

        Color verde = new Color(43, 147, 72);
        Color panelClaro = new Color(250, 251, 253);

        Color blancoGrisaceo = new Color(230, 230, 230);
        Color grisCarbon = new Color(60, 60, 60);

        Color blancoPotente = new Color(255, 255, 255);
        Color rojoPotente = new Color(153, 0, 0);

        if (modoOscuro) {
            if (panelPrincipal == null) {
                panelPrincipal = new Degradado(negroCasiFull, grisMenosDeCarbon, true);
                jPanel1.setLayout(new BorderLayout());
                jPanel1.add(panelPrincipal, BorderLayout.CENTER);
            }
            if (panel1a == null) {
                panel1a = new Degradado(negroMuyMuyFull, negroBastanteFull, true);
                jPanel2.setLayout(new BorderLayout());
                jPanel2.add(panel1a, BorderLayout.CENTER);
            }
            if (panel1b == null) {
                panel1b = new Degradado(negroMuyMuyFull, negroBastanteFull, true);
                jPanel3.setLayout(new BorderLayout());
                jPanel3.add(panel1b, BorderLayout.CENTER);
            }
        } else {
            if (panelPrincipal != null) {
                jPanel1.remove(panelPrincipal);
                panelPrincipal = null;
            }
            jPanel1.setBackground(panelClaro);

            if (panel1a != null) {
                jPanel2.remove(panel1a);
                panel1a = null;
            }
            jPanel2.setBackground(verde);

            if (panel1b != null) {
                jPanel3.remove(panel1b);
                panel1b = null;
            }
            jPanel3.setBackground(verde);
        }

        Color letraModoOscuro = modoOscuro ? blancoGrisaceo : verde;
        cambiarColorLabels(this.getContentPane(), letraModoOscuro);

        Color letraModoOscuroBotones = modoOscuro ? blancoGrisaceo : blancoPotente;
        Color botonModoOscuroLogin = modoOscuro ? grisCarbon : verde;
        btnlogin.setBackground(botonModoOscuroLogin);
        btnlogin.setForeground(letraModoOscuroBotones);

        Color botonModoOscuroCancelar = modoOscuro ? grisCarbon : rojoPotente;
        btncancelar.setBackground(botonModoOscuroCancelar);
        btncancelar.setForeground(letraModoOscuroBotones);

        ImageIcon imagenOscura = modoOscuro ? new ImageIcon(getClass().getResource("/Img/ddc2.png")) : new ImageIcon(getClass().getResource("/Img/ddc1.png"));
        Icon fondoOscuro = new ImageIcon(imagenOscura.getImage().getScaledInstance(img_ag.getWidth(), img_ag.getHeight(), Image.SCALE_DEFAULT));
        img_ag.setIcon(fondoOscuro);
        
        ImageIcon imagenBotonModo = modoOscuro ? new ImageIcon(getClass().getResource("/Img/luna.png")) : new ImageIcon(getClass().getResource("/Img/sol.png"));
        btnModoOscuro.setIcon(imagenBotonModo);
        
        btnModoOscuro.setBackground(grisCarbon);

        jPanel1.revalidate();
        jPanel1.repaint();
        jPanel2.revalidate();
        jPanel2.repaint();
        jPanel3.revalidate();
        jPanel3.repaint();
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        txtclave = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        btnlogin = new javax.swing.JButton();
        btncancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        img_ag = new javax.swing.JLabel();
        btnModoOscuro = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(250, 251, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(43, 147, 72));
        jLabel1.setText("Usuario:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(43, 147, 72));
        jLabel2.setText("Contraseña:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, -1));

        txtusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusuarioActionPerformed(evt);
            }
        });
        jPanel1.add(txtusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 180, 30));

        txtclave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclaveActionPerformed(evt);
            }
        });
        jPanel1.add(txtclave, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 180, 30));

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(43, 147, 72));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Inicio de Sesión");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 60));

        btnlogin.setBackground(new java.awt.Color(43, 147, 72));
        btnlogin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        btnlogin.setForeground(new java.awt.Color(255, 255, 255));
        btnlogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/check.png"))); // NOI18N
        btnlogin.setText("Login");
        btnlogin.setFocusable(false);
        jPanel1.add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 120, 40));

        btncancelar.setBackground(new java.awt.Color(153, 0, 0));
        btncancelar.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        btncancelar.setForeground(new java.awt.Color(255, 255, 255));
        btncancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btncancelar.setText("Cancelar");
        btncancelar.setFocusable(false);
        jPanel1.add(btncancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 130, 40));

        jPanel2.setBackground(new java.awt.Color(43, 147, 72));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pngtree-arrow-right-flat-white-color-icon-download-no-east-vector-png-image_19939064-removebg-preview.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 30, 30));

        jPanel3.setBackground(new java.awt.Color(43, 147, 72));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pngtree-arrow-right-flat-white-color-icon-download-no-east-vector-png-image_19939064-removebg-preview.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 30, 30));
        jPanel1.add(img_ag, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 180, 130));

        btnModoOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoOscuroActionPerformed(evt);
            }
        });
        jPanel1.add(btnModoOscuro, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 40, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusuarioActionPerformed

    private void txtclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclaveActionPerformed

    private void btnModoOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModoOscuroActionPerformed
    }//GEN-LAST:event_btnModoOscuroActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnModoOscuro;
    public javax.swing.JButton btncancelar;
    public javax.swing.JButton btnlogin;
    private javax.swing.JLabel img_ag;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPasswordField txtclave;
    public javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
