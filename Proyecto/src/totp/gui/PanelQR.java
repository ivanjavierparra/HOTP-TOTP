/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totp.gui;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import qr.QRGenerador;
import static sun.applet.AppletResourceLoader.getImage;
import totp.a2f.GoogleAuthenticator;
import totp.bd.Configuracion;
import totp.bd.ManagerConfiguracionDB;
import totp.bd.ManagerUsuarioDB;
import totp.bd.Usuario;
import totp.principal.Main;
//import totp.ui.MostrarQR;
//import totp.ui.PantallaPrincipal;

/**
 *
 * @author ivancho
 */
public class PanelQR extends javax.swing.JPanel {

    /**
     * Creates new form PanelQR
     */
    public PanelQR() {
        initComponents();
        mostrarQR();
    }
    
    public PanelQR(Configuracion configuracion){
        initComponents();
        mostrarQR(configuracion);
    }
    
    
    /**
     * Genera un QR solamente con los parámetros que acepta Google Authenticator y DUO.
     * https://github.com/google/google-authenticator/wiki/Key-Uri-Format.
     */
    public final void mostrarQR(){
        //Obtengo de la BD el objeto configuracion
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        Configuracion configuracion = mcdb.consultarRegistro(Main.usuario.getEmail());
        
        String text = "";
        
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0) {
            //obtengo url de configuracion
            text = GoogleAuthenticator.getQRUrlHOTP(configuracion.getEmail(),Main.usuario.getNombre(),Main.usuario.getClave_secreta(),configuracion.getContador_hotp());
        }
        else {
            //obtengo url de configuracion
            text = GoogleAuthenticator.getQRUrlTOTP(configuracion.getEmail(),Main.usuario.getNombre(),Main.usuario.getClave_secreta());
        }
        
        //genero el qr con los datos de la configuracion
        byte [] qr;
        QRGenerador qr_generador = new QRGenerador(text, 200, 200);
        qr = qr_generador.generar();
        
        //agrego el qr al lblImagen y pongo la clave en base32 en el txtClaveSecreta
        ImageIcon icono = new ImageIcon(qr);
        lblImagen.setIcon(icono);
        txtClaveSecreta.setText(Main.usuario.getClave_secreta());
    }
    
    
    
    
    /**
     * Genera un QR con todos los parámetros de configuración, aunque algunos de ellos no son reconocidos por Google Authenticator ni por DUO.
     * @param configuracion 
     */
    public final void mostrarQR(Configuracion configuracion){
        //obtengo url de configuracion 
        String text = GoogleAuthenticator.getQRUrlConfiguracion(Main.usuario.getNombre(),Main.usuario.getClave_secreta(),configuracion);
        
        //genero el qr con los datos de la configuracion
        byte [] qr;
        QRGenerador qr_generador = new QRGenerador(text, 200, 200);
        qr = qr_generador.generar();
        
        //agrego el qr al lblImagen y pongo la clave en base32 en el txtClaveSecreta
        ImageIcon icono = new ImageIcon(qr);
        lblImagen.setIcon(icono);
        txtClaveSecreta.setText(Main.usuario.getClave_secreta());
        
    }
    
    
    
    
    
    
    


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtClaveSecreta = new javax.swing.JTextArea();

        setBackground(java.awt.Color.darkGray);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 234, 12));
        jLabel1.setText("Escanear el siguiente código QR con Google Authenticator:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 234, 12));
        jLabel3.setText("Nota: si no puedes escanear el código, ingrésalo manualmente:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jPanel1.setBackground(java.awt.Color.darkGray);

        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblImagen);

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 390, 230));

        txtClaveSecreta.setBackground(java.awt.Color.lightGray);
        txtClaveSecreta.setColumns(20);
        txtClaveSecreta.setFont(new java.awt.Font("Monospaced", 1, 15)); // NOI18N
        txtClaveSecreta.setRows(5);
        txtClaveSecreta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveSecretaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClaveSecretaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtClaveSecreta);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 390, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void txtClaveSecretaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveSecretaKeyTyped
        // TODO add your handling code here:
        evt.consume();
    }//GEN-LAST:event_txtClaveSecretaKeyTyped

    private void txtClaveSecretaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveSecretaKeyPressed
        // TODO add your handling code here:
        evt.consume();
    }//GEN-LAST:event_txtClaveSecretaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTextArea txtClaveSecreta;
    // End of variables declaration//GEN-END:variables
}
