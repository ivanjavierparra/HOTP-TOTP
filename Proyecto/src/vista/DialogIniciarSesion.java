/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import encriptacion.Hash;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JOptionPane;
import a2f.ManagerHOTP;
import a2f.ManagerTOTP;
import a2f.SecretGenerator;
import modelo.Configuracion;
import modelo.ManagerConfiguracionDB;
import modelo.ManagerUsuarioDB;
import principal.Main;

/**
 *
 * @author ivancho
 */
public class DialogIniciarSesion extends javax.swing.JDialog {
    GridBagLayout layout = new GridBagLayout();
    PanelLogin panelLogin;
    PanelCodigo panelCodigo;
    /**
     * Creates new form NewJDialog
     */
    public DialogIniciarSesion(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicializarDialog();
    }

    
    /**
     * Configura atributos del JDialog.
     * Agrega 2 paneles: PanelLogin y PanelCOdigo.
     */
    private void inicializarDialog(){
        panelLogin = new PanelLogin();
        panelCodigo = new PanelCodigo();
        pnlBody.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelLogin,c);
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelCodigo,c);
        panelLogin.setVisible(true);
        panelCodigo.setVisible(false);
        
        this.setLocationRelativeTo(null);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlFooter = new javax.swing.JPanel();
        btnSiguiente = new javax.swing.JButton();
        pnlBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iniciar Sesión");

        pnlHeader.setBackground(java.awt.Color.black);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 234, 12));
        jLabel1.setText("Iniciar Sesión");
        pnlHeader.add(jLabel1);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.PAGE_START);

        pnlFooter.setBackground(java.awt.Color.black);
        pnlFooter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSiguiente.setBackground(java.awt.Color.black);
        btnSiguiente.setForeground(new java.awt.Color(123, 234, 12));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        pnlFooter.add(btnSiguiente);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        pnlBody.setBackground(java.awt.Color.darkGray);

        javax.swing.GroupLayout pnlBodyLayout = new javax.swing.GroupLayout(pnlBody);
        pnlBody.setLayout(pnlBodyLayout);
        pnlBodyLayout.setHorizontalGroup(
            pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );
        pnlBodyLayout.setVerticalGroup(
            pnlBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        getContentPane().add(pnlBody, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
        if(panelLogin.isVisible()) validarUsuario();
        else validarCodigo();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    
    private void validarUsuario(){
        // Obtenemos los datos.
        String email = panelLogin.getEmail();
        String password = panelLogin.getPassword();
        
        //Validamos las variables en PanelLogin.java.
        String mensaje_validacion = panelLogin.validarTxt();
        if (mensaje_validacion.compareToIgnoreCase("")!=0){
            JOptionPane.showMessageDialog(this,mensaje_validacion,"Mensaje",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //encripto password con SHA1
        String password_encriptado = Hash.getHash(password, "SHA1");
        
        //consultamos a la bd
        ManagerUsuarioDB mdb = new ManagerUsuarioDB();
        Main.usuario = mdb.consultarRegistro(email, password_encriptado);
        
        
        // Valido usuario y password, Verifico si tiene activado a2f.
        if(Main.usuario.getNombre().compareToIgnoreCase("")==0)JOptionPane.showMessageDialog(this,"Email o Password incorrectos","Error en la BD", JOptionPane.ERROR_MESSAGE);
        else{ // El email y el password son correctos.
            if(Main.usuario.isA2f_activado()){ // el usuario tiene activado la doble autenticación.
                panelCodigo.setVisible(true);
                panelCodigo.setFocusCodigo();
                panelLogin.setVisible(false);
            }
            else{ // el usuario tiene desactivado la doble autenticación.
                iniciarSesion();
            }
        }
        
    }
    
    private void iniciarSesion(){
        JOptionPane.showMessageDialog(this,"Inicio de Sesión correcto!","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        Main.pantallaPrincipal.mostrarSesion();
        this.dispose();
    }
    
    private void validarCodigo(){
        // Obtenemos el código ingresado por el usuario.
        String codigo = panelCodigo.getCodigo();
        
        //Validamos codigo
        String mensaje_validacion = panelCodigo.validarTxt();
        if(mensaje_validacion.compareToIgnoreCase("")!=0){
            JOptionPane.showMessageDialog(this,mensaje_validacion,"Mensaje",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Obtenemos la clave secreta del usuario.
        byte[] secreto = SecretGenerator.fromBase32(Main.usuario.getClave_secreta());
        
        System.out.println("El secreto es: " + Main.usuario.getClave_secreta());
        
        // Consultamos la bd y obtenemos la configuracion del usuario.
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        Configuracion configuracion = mcdb.consultarRegistro(Main.usuario.getEmail());
        if(configuracion.getEmail().compareToIgnoreCase("noexisto")==0) { //no he encontrado el registro de configuracion del usuario.
            JOptionPane.showMessageDialog(this,"Se ha encontrado un problema en la BD: no se encuentra registrado en la tabla configuraciona2f","Error en la BD", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //he encontrado el registro. Consultamos que tipo de validación tiene: HOTP/TOTP.
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0){ // El usuario ha elegido HOTP.
            System.out.println("Estoy en HOTP.");
            // Acá seteamos los atributos HOTP que estan en el objeto "configuracion" en el objeto ManagerHOTP.
            ManagerHOTP manager = new ManagerHOTP();
            //manager.setContador(configuracion.getContador());
            
                    //... manager = new ManagerHOTP(configuracion.getAlgoritmo(),configuracion.getContador_hotp(),configuracion.getDigitos());
            
            System.out.println("El valor del contador en var configuracion es: "+configuracion.getContador_hotp());
            int valid = manager.validar(secreto, configuracion.getContador_hotp(), codigo);
            actualizarContador(valid);
            if(valid==-1) bloquearCuenta(valid);
            else iniciarSesion();
            
            
            
        }
        else{ // El usuario ha elegido TOTP.
            ManagerTOTP manager = new ManagerTOTP();
            
            // Acá seteamos los atributos TOTP que estan en el objeto "configuracion". manager.set....etc.
            
                    //... manager = new ManagerTOTP(configuracion.getAlgoritmo(),configuracion.getDigitos(),configuracion.getTiempo_totp());
                    
            
            // Validamos.
            boolean valid = manager.validar(secreto, codigo);
            if(!valid)JOptionPane.showMessageDialog(this,"Código incorrecto.","Error", JOptionPane.ERROR_MESSAGE);
            else iniciarSesion();
                
            
        }
    }
    
    
    private void bloquearCuenta(int contador){
        JOptionPane.showMessageDialog(this,"Problema de sincronización con el contador. Contador = " + contador,"Error", JOptionPane.ERROR_MESSAGE);
        this.dispose();
    }
    
    
    private void actualizarContador(int counter){
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        mcdb.actualizarRegistro(Main.usuario.getEmail(), counter);
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(DialogIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogIniciarSesion dialog = new DialogIniciarSesion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    // End of variables declaration//GEN-END:variables
}