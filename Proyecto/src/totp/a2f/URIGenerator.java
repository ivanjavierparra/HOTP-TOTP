package totp.a2f;

import totp.bd.Configuracion;



/**
 * Clase que genera una URI capaz de ser leída por la app Google Authenticator, DUO, Latch, entre otros. 
 * Ver: https://www.npmjs.com/package/otp
 *      https://github.com/google/google-authenticator/wiki/Key-Uri-Format
 *      https://github.com/wstrange/GoogleAuth
 * @author ivancho
 */



public abstract class URIGenerator {

	
    /**
     * Genera una URI para el algoritmo TOTP, sólo con los parámetros que acepta Google Authenticator.
     * @param email
     * @param nombre
     * @param clave_secreta
     * @return 
     */
    public static final String getQRUrlTOTP(String email, String nombre, String clave_secreta) {
        String url = "otpauth://totp/%s?secret=%s&issuer=%s";
        return String.format(url, email, clave_secreta, nombre);
    }
        
    
    /**
     * Genera una URI para el algoritmo HOTP, sólo con los parámetros que acepta Google Authenticator.
     * @param email
     * @param nombre
     * @param clave_secreta: en base32
     * @param contador
     * @return 
     */
    public static final String getQRUrlHOTP(String email, String nombre, String clave_secreta, int contador) {
        String url = "otpauth://hotp/%s?secret=%s&issuer=%s&counter=%s";
        return String.format(url, email, clave_secreta, nombre, contador);
    }


    /**
     * Genera una URI para el algoritmo HOTP/TOTP, con TODOS los parámetros posibles, 
     * pero algunos de ellos no son reconocidos por Google Authenticator ni por DUO.
     * https://github.com/google/google-authenticator/wiki/Key-Uri-Format
     * @param nombre
     * @param secret
     * @param configuracion
     * @return 
     */
    public static final String getQRUrlConfiguracion(String nombre, String secret, Configuracion configuracion){
        String url="";
        String algoritmo = configuracion.getAlgoritmoEncriptacion();
        
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0){
            url = "otpauth://hotp/%s?secret=%s&issuer=%s&counter=%s&algorithm=%s&digits=%s";
            url = String.format(url,configuracion.getEmail(),secret,nombre,configuracion.getContador_hotp(),algoritmo,configuracion.getDigitos());
        }
        else {
            url = "otpauth://totp/%s?secret=%s&issuer=%s&algorithm=%s&digits=%s&period=%s";
            url = String.format(url,configuracion.getEmail(),secret,nombre,algoritmo,configuracion.getDigitos(),configuracion.getTiempo_totp());
        }
        return url;
    }
	
        
}
