package totp.a2f;

import totp.bd.Configuracion;



/**
 * Clase que genera una QR code URL que puede ser ingresado en un navegador y escaneado por la app Google Authenticator. 
 * Ver: https://www.npmjs.com/package/otp
 *      https://github.com/google/google-authenticator/wiki/Key-Uri-Format
 *      https://github.com/wstrange/GoogleAuth
 * @author ivancho
 */



public abstract class GoogleAuthenticator {

	
    /**
     * Genera un QR para el algoritmo TOTP, sólo con los parámetros que acepta Google Authenticator.
     * @param email
     * @param nombre
     * @param clave_secreta
     * @return 
     */
    public static final String getQRUrlTOTP(String email, String nombre, String clave_secreta) {
        String cadena = "otpauth://totp/%s?secret=%s&issuer=%s";
        return String.format(cadena, email, clave_secreta, nombre);
    }
        
    
    /**
     * Genera un QR para el algoritmo HOTP, sólo con los parámetros que acepta Google Authenticator.
     * @param email
     * @param nombre
     * @param clave_secreta: en base32
     * @param contador
     * @return 
     */
    public static final String getQRUrlHOTP(String email, String nombre, String clave_secreta, int contador) {
        String cadena = "otpauth://hotp/%s?secret=%s&issuer=%s&counter=%s";
        return String.format(cadena, email, clave_secreta, nombre, contador);
    }


    /**
     * Genera un QR para el algoritmo HOTP/TOTP, con TODOS los parámetros posibles, 
     * pero algunos de ellos no son reconocidos por Google Authenticator ni por DUO.
     * https://github.com/google/google-authenticator/wiki/Key-Uri-Format
     * @param nombre
     * @param secret
     * @param configuracion
     * @return 
     */
    public static final String getQRUrlConfiguracion(String nombre, String secret, Configuracion configuracion){
        String cadena="";
        String algoritmo = getAlgoritmo(configuracion);
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0){
            cadena = "otpauth://hotp/%s?secret=%s&issuer=%s&counter=%s&algorithm=%s&digits=%s";
            cadena = String.format(cadena,configuracion.getEmail(),secret,nombre,configuracion.getContador_hotp(),algoritmo,configuracion.getDigitos());
        }
        else {
            cadena = "otpauth://totp/%s?secret=%s&issuer=%s&algorithm=%s&digits=%s&period=%s";
            cadena = String.format(cadena,configuracion.getEmail(),secret,nombre,algoritmo,configuracion.getDigitos(),configuracion.getTiempo_totp());
        }
        return cadena;
    }
        
        
    /**
     * Devuelve un String que representa el algoritmo (SHA-1,SHA-256,SHA-512) que está 
     * en el objeto "configuración" pasado como parámetro.
     * @param configuracion
     * @return 
     */
    private static String getAlgoritmo(Configuracion configuracion){
        if(configuracion.getAlgoritmo().compareToIgnoreCase("HmacSHA1")==0)return "SHA1";
        else if(configuracion.getAlgoritmo().compareToIgnoreCase("HmacSHA256")==0)return "SHA256";
        else return "SHA512";
    }
	
        
}
