/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encriptacion;

/**
 *
 * @author Asus
 */
public class Hash {
    /**
     * Retorna un hash a partir de un tipo y un texto.
     * @param txt
     * @param hashType: "SHA1", "SHA-256", "SHA-384", "SHA-512", "MD5", "MD2". 
     * @return 
     */
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
 
    /* Retorna un hash MD5 a partir de un texto */
    public static String md5(String txt) {
        return Hash.getHash(txt, "MD5");
    }
 
    /* Retorna un hash SHA1 a partir de un texto */
    public static String sha1(String txt) {
        return Hash.getHash(txt, "SHA1");
    }
    
    public static boolean compararHash(String hash_1, String hash_2) {
        if(hash_1.equals(hash_2)) return true;
        return false;
    }
    
    /* Prueba */
    public static void main(String[] args){
        System.out.println(Hash.md5("Hola Mundo!"));
        String a = Hash.md5("Hola Mundo!");
        String b = Hash.md5("Hola Mundo!");
        if(a.equals(b))System.out.println("Buenisimo");
        System.out.println(Hash.sha1("Hola Mundo!")); 
    }
}
