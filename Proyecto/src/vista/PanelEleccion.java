/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.ButtonGroup;

/**
 *
 * @author ivancho
 */
public class PanelEleccion extends javax.swing.JPanel {

    /**
     * Creates new form PanelCodigo
     */
    public PanelEleccion() {
        initComponents();
        inicializarPanel();
    }

   private void inicializarPanel(){
       ButtonGroup grupo = new ButtonGroup();
       grupo.add(rbtnHOTP);
       grupo.add(rbtnTOTP);
       rbtnHOTP.setSelected(true);
   }
    
   public String getEleccion(){
       if(rbtnHOTP.isSelected()) return "HOTP";
       else return "TOTP";
   }
   
   
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rbtnHOTP = new javax.swing.JRadioButton();
        rbtnTOTP = new javax.swing.JRadioButton();

        setBackground(java.awt.Color.darkGray);

        jLabel3.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 234, 12));
        jLabel3.setText("Seleccione el Algoritmo de Autenticación:");

        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/codigo3.png"))); // NOI18N

        rbtnHOTP.setBackground(java.awt.Color.darkGray);
        rbtnHOTP.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        rbtnHOTP.setForeground(new java.awt.Color(123, 234, 12));
        rbtnHOTP.setText("HOTP");

        rbtnTOTP.setBackground(java.awt.Color.darkGray);
        rbtnTOTP.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        rbtnTOTP.setForeground(new java.awt.Color(123, 234, 12));
        rbtnTOTP.setText("TOTP");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbtnHOTP))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbtnTOTP)))
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(130, 130, 130)
                    .addComponent(lblImagen)
                    .addContainerGap(354, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(rbtnHOTP)
                .addGap(28, 28, 28)
                .addComponent(rbtnTOTP)
                .addContainerGap(111, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(165, 165, 165)
                    .addComponent(lblImagen)
                    .addContainerGap(211, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JRadioButton rbtnHOTP;
    private javax.swing.JRadioButton rbtnTOTP;
    // End of variables declaration//GEN-END:variables
}