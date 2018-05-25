/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qr;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author ivanj
 */
public class QRGenerador {
    private String texto;
    private int ancho;
    private int alto;
    
    public QRGenerador(String texto, int ancho, int alto){
        this.texto = texto;
        this.ancho = ancho;
        this.alto = alto;
    }
    
    /**
     * Método que utiliza la librería zxing para generar un código qr.
     * @return un arreglo de bytes, que representa el qr. Quien reciba este arreglo deberá convertirlo
     * en un ícono para mostrarlo como una Imagen.
     */
    public byte[] generar(){
        byte[] pngData = null;
        try{
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, com.google.zxing.BarcodeFormat.QR_CODE, ancho, alto);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            pngData = pngOutputStream.toByteArray();
        }catch(Exception e){
            System.out.println("Error al generar el QR: " + e.getMessage());
        }
        
        return pngData;
    }
}
