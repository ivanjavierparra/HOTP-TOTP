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
     * Genera un QR solamente con los parámetros que acepta Google Authenticar.
     */
    public void mostrarQR(){
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        Configuracion configuracion = mcdb.consultarRegistro(Main.usuario.getEmail());
        String qr;
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0) {
            qr = GoogleAuthenticator.getQRUrlHOTP(configuracion.getEmail(),Main.usuario.getClave_secreta(),configuracion.getContador_hotp());
        }
        else {
            qr = GoogleAuthenticator.getQRUrlTOTP(Main.usuario.getNombre(), Main.usuario.getEmail(), Main.usuario.getClave_secreta());
        }
        lblClaveSecreta.setText(Main.usuario.getClave_secreta());
        try {
            Image img=getImage(new URL(qr));
            lblImagen.setIcon(new ImageIcon(img));
            repaint();
        } catch (MalformedURLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
    
    
    /**
     * Genera un QR con todos los parámetros de configuración, aunque algunos de ellos no son reconocidos por Google Authenticator.
     * @param configuracion 
     */
    public void mostrarQR(Configuracion configuracion){
        String qr = GoogleAuthenticator.getQRUrlConfiguracion(Main.usuario.getClave_secreta(),configuracion);
        lblClaveSecreta.setText(Main.usuario.getClave_secreta());
        try {
            Image img=getImage(new URL(qr));
            lblImagen.setIcon(new ImageIcon(img));
            repaint();
        } catch (MalformedURLException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
    
    
    
    
    
    
    


    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblClaveSecreta = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 234, 12));
        jLabel1.setText("Escanear el siguiente código QR con Google Authenticator:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 234, 12));
        jLabel3.setText("Nota: si no puedes escanear el código, ingrésalo manualmente:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        lblClaveSecreta.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        lblClaveSecreta.setForeground(java.awt.Color.white);
        lblClaveSecreta.setText("RQCS4KLYL7HJFP54JI4YZQPU5U442PVD");
        lblClaveSecreta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lblClaveSecreta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblClaveSecreta;
    private javax.swing.JLabel lblImagen;
    // End of variables declaration//GEN-END:variables
}
