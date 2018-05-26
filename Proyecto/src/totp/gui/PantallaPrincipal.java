/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totp.gui;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import totp.bd.ManagerConfiguracionDB;
import totp.bd.ManagerUsuarioDB;
import totp.bd.Usuario;
import totp.principal.Main;

/**
 *
 * @author ivancho
 */
public class PantallaPrincipal extends javax.swing.JFrame {

    
    
    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
        inicializarFrame();
    }

    private void inicializarFrame(){
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int width = pantalla.width;
        int height = pantalla.height;
        setSize(900,900);		
        setLocationRelativeTo(null);
        
        this.lblUsuario.setVisible(false);
        itemsInicializar();
    }
    
    private void itemsInicializar(){
        menuItemVerQR.setVisible(false);
        menuItemActivarA2F.setVisible(false);
        menuItemDesactivarA2F.setVisible(false);
        menuItemCerrar.setVisible(false);
    }

    public void mostrarSesion(){
        this.lblUsuario.setText("Bienvenid@ " + Main.usuario.getNombre() + "!");
        menuItemCerrar.setVisible(true);
        menuItemIniciar.setVisible(false);
        if(Main.usuario.isA2f_activado()){
            menuItemVerQR.setVisible(true);
            menuItemDesactivarA2F.setVisible(true);
        }
        else{
            menuItemActivarA2F.setVisible(true);
        }
        this.lblUsuario.setVisible(true);
    }

    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFooter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemRegistrar = new javax.swing.JMenuItem();
        menuItemIniciar = new javax.swing.JMenuItem();
        menuItemVerQR = new javax.swing.JMenuItem();
        menuItemActivarA2F = new javax.swing.JMenuItem();
        menuItemDesactivarA2F = new javax.swing.JMenuItem();
        menuItemCerrar = new javax.swing.JMenuItem();
        menuItemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Exámen Final - Administración de Redes y Seguridad - HOTP - TOTP");
        setBackground(java.awt.Color.darkGray);
        setExtendedState(6);
        setIconImage(getIconImage());

        pnlFooter.setBackground(java.awt.Color.black);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 234, 12));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ivàn Javier Parra - 2018");
        pnlFooter.add(jLabel1);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.PAGE_END);

        jPanel2.setBackground(java.awt.Color.darkGray);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 100));

        lblUsuario.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(123, 234, 12));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/totp/imagenes/sesioniniciada.png"))); // NOI18N
        lblUsuario.setText("Bienvenido Usuario!!!");
        lblUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUsuario.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel2.add(lblUsuario);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Archivo");

        menuItemRegistrar.setText("Registrar Usuario");
        menuItemRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRegistrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemRegistrar);

        menuItemIniciar.setText("Iniciar Sesión");
        menuItemIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemIniciarActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemIniciar);

        menuItemVerQR.setText("Ver QR");
        menuItemVerQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerQRActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemVerQR);

        menuItemActivarA2F.setText("Activar autenticación multifactor");
        menuItemActivarA2F.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActivarA2FActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemActivarA2F);

        menuItemDesactivarA2F.setText("Desactivar autenticación multifactor");
        menuItemDesactivarA2F.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDesactivarA2FActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemDesactivarA2F);

        menuItemCerrar.setText("Cerrar Sesión");
        menuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemCerrar);

        menuItemSalir.setText("Salir");
        menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSalir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemIniciarActionPerformed
        // TODO add your handling code here:
        DialogIniciarSesion dis = new DialogIniciarSesion(this, true);
        dis.setVisible(true);
    }//GEN-LAST:event_menuItemIniciarActionPerformed

    private void menuItemRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRegistrarActionPerformed
        // TODO add your handling code here:
        DialogRegistrarUsuario dru = new DialogRegistrarUsuario(this, true);
        dru.setVisible(true);
    }//GEN-LAST:event_menuItemRegistrarActionPerformed

    private void menuItemVerQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVerQRActionPerformed
        // TODO add your handling code here:
        if( Main.usuario.getEmail().compareToIgnoreCase("")==0 )JOptionPane.showMessageDialog(this,"Error: no has iniciado sesión.","Error", JOptionPane.ERROR_MESSAGE);
        else{
            DialogClaveSecreta dcs = new DialogClaveSecreta(this, true);
            dcs.setVisible(true);
        }
    }//GEN-LAST:event_menuItemVerQRActionPerformed

    private void menuItemActivarA2FActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemActivarA2FActionPerformed
        // TODO add your handling code here:
        Dialog2AF d2af = new Dialog2AF(this, true);
        d2af.setVisible(true);
    }//GEN-LAST:event_menuItemActivarA2FActionPerformed

    private void menuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarActionPerformed
        // TODO add your handling code here:
        Main.usuario = new Usuario();
        itemsCerrar();
        this.lblUsuario.setVisible(false);
        JOptionPane.showMessageDialog(this,"Sesión cerrada.","Sesión",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menuItemCerrarActionPerformed

    
    private void itemsCerrar(){
        menuItemVerQR.setVisible(false);
        menuItemActivarA2F.setVisible(false);
        menuItemDesactivarA2F.setVisible(false);
        menuItemCerrar.setVisible(false);
        menuItemIniciar.setVisible(true);
        menuItemRegistrar.setVisible(true);
        menuItemVerQR.setVisible(false);
    }
    
    private void menuItemDesactivarA2FActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDesactivarA2FActionPerformed
        // TODO add your handling code here:
        ManagerUsuarioDB mudb = new ManagerUsuarioDB();
        mudb.actualizarA2F(Main.usuario.getEmail(), false);
        
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        mcdb.eliminarRegistro(Main.usuario.getEmail());
                
        JOptionPane.showMessageDialog(this,"Se ha desactivado correctamente!","Autenticación A2F",JOptionPane.INFORMATION_MESSAGE);
        itemsDesactivarA2F();
    }//GEN-LAST:event_menuItemDesactivarA2FActionPerformed

    
    private void itemsDesactivarA2F(){
        menuItemDesactivarA2F.setVisible(false);
        menuItemActivarA2F.setVisible(true);
        menuItemVerQR.setVisible(false);
    }
    
    private void menuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalirActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this,"Chau!","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }//GEN-LAST:event_menuItemSalirActionPerformed

    public void ocultarItemActivar(){
        menuItemActivarA2F.setVisible(false);
        menuItemDesactivarA2F.setVisible(true);
        menuItemVerQR.setVisible(true);
    }
    
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("totp/imagenes/icono.png"));


        return retValue;
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
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem menuItemActivarA2F;
    private javax.swing.JMenuItem menuItemCerrar;
    private javax.swing.JMenuItem menuItemDesactivarA2F;
    private javax.swing.JMenuItem menuItemIniciar;
    private javax.swing.JMenuItem menuItemRegistrar;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JMenuItem menuItemVerQR;
    private javax.swing.JPanel pnlFooter;
    // End of variables declaration//GEN-END:variables
}

//dialog = new dialog