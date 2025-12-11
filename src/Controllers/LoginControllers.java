package Controllers;

import Models.Usuarios;
import Models.UsuariosDao;
import Views.FrmLogin;
import Views.PanelAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class LoginControllers implements ActionListener{
    private Usuarios us;
    private UsuariosDao usDao;
    private FrmLogin views;
    
    private boolean modoOscuroActivo;

    public LoginControllers(Usuarios us, UsuariosDao usDao, FrmLogin views, boolean modoOscuroActivo) {
        this.us = us;
        this.usDao = usDao;
        this.views = views;
        this.views.btnlogin.addActionListener(this);
        this.views.btncancelar.addActionListener(this);  
        this.views.setLocationRelativeTo(null);
        
        this.modoOscuroActivo = modoOscuroActivo;
        
        views.txtclave.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ejecutarLogin();
                }
            }
        });
        views.txtusuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ejecutarLogin();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == views.btnlogin){
            ejecutarLogin();
        }else{
            int pregunta= JOptionPane.showConfirmDialog(null, "Está seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(pregunta ==0){
                System.exit(0);
            }
        }
    }
    
    //Setter
    public void setModoOscuroActivo(boolean activo) {
        this.modoOscuroActivo = activo;
    }
    //Getter
    public boolean isModoOscuroActivo() {
        return modoOscuroActivo;
    }
    
    public void ejecutarLogin(){
        if(views.txtusuario.getText().equals("") || String.valueOf(views.txtclave.getPassword()).equals("")){
            JOptionPane.showMessageDialog(null, "Hay campos vacíos!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            String Usuario = views.txtusuario.getText();
            String Clave = String.valueOf(views.txtclave.getPassword());
            us = usDao.login(Usuario, Clave);
            if(us.getUsuario()!=null){
                PanelAdmin admin = new PanelAdmin(us.getId(), us.getNombre(), us.getRol(), us.getEstado(), modoOscuroActivo);
                String estado = us.getEstado();
                if(estado.equals("Activo")){
                    admin.setVisible(true);
                    this.views.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Su Usuario está Inactivo", "Usuario Inactivo", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
