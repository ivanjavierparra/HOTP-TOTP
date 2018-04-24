/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totp.gui;

import javax.swing.ButtonGroup;

/**
 *
 * @author ivancho
 */
public class PanelHOTPConfig extends javax.swing.JPanel {

    /**
     * Creates new form PanelCodigo
     */
    public PanelHOTPConfig() {
        initComponents();
        inicializarPanel();
    }

    private void inicializarPanel(){
       ButtonGroup grupo = new ButtonGroup();
       grupo.add(rbtnSHA1);
       grupo.add(rbtnSHA256);
       grupo.add(rbtnSHA512);
       rbtnSHA1.setSelected(true);
   }
    
    public String getAlgoritmo(){
        if(rbtnSHA1.isSelected())return "HmacSHA1";
        else if(rbtnSHA256.isSelected()) return "HmacSHA256";
        else return "HmacSHA512";
    }
   
    public int getDigitos(){
        return (Integer) spnDigitos.getValue();
    }
    
    public int getContador(){
        return (Integer) spnContador.getValue();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rbtnSHA1 = new javax.swing.JRadioButton();
        rbtnSHA256 = new javax.swing.JRadioButton();
        rbtnSHA512 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        spnContador = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        spnDigitos = new javax.swing.JSpinner();

        setBackground(java.awt.Color.darkGray);

        jLabel4.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(123, 234, 12));
        jLabel4.setText("Algoritmo");

        jLabel3.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 234, 12));
        jLabel3.setText("Configuración HOTP");

        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        rbtnSHA1.setBackground(java.awt.Color.darkGray);
        rbtnSHA1.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        rbtnSHA1.setForeground(new java.awt.Color(123, 234, 12));
        rbtnSHA1.setText("SHA1");

        rbtnSHA256.setBackground(java.awt.Color.darkGray);
        rbtnSHA256.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        rbtnSHA256.setForeground(new java.awt.Color(123, 234, 12));
        rbtnSHA256.setText("SHA256");

        rbtnSHA512.setBackground(java.awt.Color.darkGray);
        rbtnSHA512.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        rbtnSHA512.setForeground(new java.awt.Color(123, 234, 12));
        rbtnSHA512.setText("SHA512");

        jLabel5.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(123, 234, 12));
        jLabel5.setText("Contador");

        spnContador.setModel(new javax.swing.SpinnerNumberModel(0, 0, 500, 1));

        jLabel6.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(123, 234, 12));
        jLabel6.setText("Cantidad de dígitos");

        spnDigitos.setModel(new javax.swing.SpinnerNumberModel(6, 6, 8, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rbtnSHA512)
                                    .addComponent(rbtnSHA1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbtnSHA256, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spnDigitos, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                    .addComponent(spnContador))))))
                .addContainerGap(281, Short.MAX_VALUE))
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
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSHA1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSHA256)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnSHA512)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(spnDigitos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(spnContador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(165, 165, 165)
                    .addComponent(lblImagen)
                    .addContainerGap(211, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private boolean isNumber(char ch){
        return ch >= '6' && ch <= '8';
    }

 
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JRadioButton rbtnSHA1;
    private javax.swing.JRadioButton rbtnSHA256;
    private javax.swing.JRadioButton rbtnSHA512;
    private javax.swing.JSpinner spnContador;
    private javax.swing.JSpinner spnDigitos;
    // End of variables declaration//GEN-END:variables
}
