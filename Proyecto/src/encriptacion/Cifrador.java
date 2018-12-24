package encriptacion;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author Iván Javier Parra
 * Ver: https://es.stackoverflow.com/questions/54098/que-algoritmo-de-cifrado-se-puede-usar-para-guardar-datos-en-java
 */


public class Cifrador {
    public static SecureRandom sr = new SecureRandom();

    /**
     * Encripta un mensaje a partir de una clave, con el algoritmo AES.
     * @param clave
     * @param iv
     * @param value
     * @return 
     */
    public static String encriptar(String clave, byte[] iv, String value) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] encriptado = cipher.doFinal(value.getBytes());
            return DatatypeConverter.printBase64Binary(encriptado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /**
     * Desencripta una clave, usando la clave.
     * @param clave
     * @param iv
     * @param encriptado
     * @return 
     */
    public static String desencriptar(String clave, byte[] iv, String encriptado) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] dec = cipher.doFinal(DatatypeConverter.parseBase64Binary(encriptado));
            return new String(dec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    
    /**
     * Test de los métodos de la clase Cifrador.
     * @param args 
     */
    public static void main(String[] args) {
        String clave = "FooBar1234567890"; // 128 bit
        byte[] iv = new byte[16];
        sr.nextBytes(iv);
        String encriptado = encriptar(clave, iv, "Demasiados Secretos!");
        System.out.println(String.format("encriptado: %s", encriptado));
        System.out.println(desencriptar(clave, iv, encriptado));
        
        
        String base64 = "7IXQR7RPOPS6B4CNNFGIFKBFYIQNWTAL";
        System.out.println("Clave Secreta en BASE64: " + base64);
        String e = encriptar(clave, iv, base64);
        System.out.println(String.format("encriptado: %s", e));
        System.out.println("desencriptado: " + desencriptar(clave, iv, e));
    }
}
