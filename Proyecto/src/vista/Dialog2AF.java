package vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JOptionPane;
import modelo.Configuracion;
import modelo.ManagerConfiguracionDB;
import modelo.ManagerUsuarioDB;
import principal.Main;


/**
 *
 * @author Ivan Javier Parra
 */

public class Dialog2AF extends javax.swing.JDialog {
    GridBagLayout layout = new GridBagLayout();
    PanelEleccion panelEleccion;
    PanelHOTPConfig panelHOTPConfig;
    PanelTOTPConfig panelTOTPConfig;
    PanelQR panelQR;
    
    
    public Dialog2AF(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        inicializarDialog();
    }

    
    private void inicializarDialog(){
        panelEleccion = new PanelEleccion();
        panelHOTPConfig = new PanelHOTPConfig();
        panelTOTPConfig = new PanelTOTPConfig();
        panelQR = new PanelQR();
        pnlBody.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelEleccion,c);
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelHOTPConfig,c);
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelTOTPConfig,c);
        c.gridx = 0;
        c.gridy = 0;
        pnlBody.add(panelQR,c);
        panelEleccion.setVisible(true);
        panelHOTPConfig.setVisible(false);
        panelTOTPConfig.setVisible(false);
        panelQR.setVisible(false);
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
        setTitle("Autenticación Multifactor");

        pnlHeader.setBackground(java.awt.Color.black);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 234, 12));
        jLabel1.setText("Autenticación A2F");
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
        if(panelEleccion.isVisible())mostrarPanelConfiguracion();
        else if(panelHOTPConfig.isVisible())crearHOTP();
        else if(panelTOTPConfig.isVisible())crearTOTP();
        else this.dispose();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    
    /**
     * Muestra PanelHOTP o PanelTOTP según la elección del usuario.
     */
    private void mostrarPanelConfiguracion(){
        String algoritmo = panelEleccion.getEleccion();
        panelEleccion.setVisible(false);
        if (algoritmo.compareToIgnoreCase("HOTP")==0)panelHOTPConfig.setVisible(true);
        else panelTOTPConfig.setVisible(true);
    }
    
    
    /**
     * Obtiene parámetros de configuración HOTP.
     */
    private void crearHOTP(){
        String algoritmo = panelHOTPConfig.getAlgoritmo();
        int digitos = panelHOTPConfig.getDigitos();
        int contador = panelHOTPConfig.getContador();
        
        insertarConfiguracion(Main.usuario.getEmail(),algoritmo,"HOTP",digitos,contador,0);
        
    }
    
    
    /**
     * Obtiene parámetros de configuración TOTP.
     */
    private void crearTOTP(){
        String algoritmo = panelTOTPConfig.getAlgoritmo();
        int digitos = panelTOTPConfig.getDigitos();
        int tiempo = panelTOTPConfig.getTiempo();
        
        insertarConfiguracion(Main.usuario.getEmail(),algoritmo,"TOTP",digitos,-1,tiempo);
    }
    
    
    /**
     * Registra un objeto configuración en la BD.
     * @param email
     * @param algoritmo
     * @param tipo
     * @param digitos
     * @param contador
     * @param tiempo 
     */
    private void insertarConfiguracion(String email, String algoritmo, String tipo, int digitos, int contador, int tiempo ){
        Configuracion configuracion = new Configuracion(email,algoritmo,tipo,digitos,contador,tiempo);
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        boolean exito = mcdb.insertarRegistro(configuracion);
        
        if (!exito){
            JOptionPane.showMessageDialog(this,"Fallo la inserción en la BD","Error en la BD", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        else{
            actualizarA2Fusuario();
            
            JOptionPane.showMessageDialog(this,"Configuración registrada correctamente!","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            panelHOTPConfig.setVisible(false);
            panelTOTPConfig.setVisible(false);
            //panelQR.mostrarQR();
            panelQR.mostrarQR(configuracion);
            panelQR.setVisible(true);
            Main.pantallaPrincipal.ocultarItemActivar();
        }
    }
    
    
    /**
     * Activa 2af del usuario en la BD.
     */
    private void actualizarA2Fusuario(){
        ManagerUsuarioDB mudb = new ManagerUsuarioDB();
        mudb.actualizarA2F(Main.usuario.getEmail(), true);
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
            java.util.logging.Logger.getLogger(Dialog2AF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog2AF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog2AF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog2AF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog2AF dialog = new Dialog2AF(new javax.swing.JFrame(), true);
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
