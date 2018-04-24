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
     * @param username
     * @param host
     * @param secret
     * @return 
     */
    public static final String getQRUrlTOTP(String username, String host, String secret) {
        String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";
        return String.format(format, username, host, secret);
    }
        
    
    /**
     * Genera un QR para el algoritmo hOTP, sólo con los parámetros que acepta Google Authenticator.
     * @param username
     * @param host
     * @param secret
     * @return 
     */
    public static final String getQRUrlHOTP(String email, String secret, int contador) {
        String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+email+"%3Fsecret="+secret+"%26counter="+contador;
        return format;
    }


    /**
     * Genera un QR para el algoritmo HOTP/TOTP, con TODOS los parámetros posibles, 
     * pero algunos de ellos no son reconocidos por Google Authenticator.
     * Nota: NO funciona con DUO, y Google Authenticator me toma los parámetros que reconce según https://github.com/google/google-authenticator/wiki/Key-Uri-Format
     * @param username
     * @param host
     * @param secret
     * @return 
     */
    public static final String getQRUrlConfiguracion(String secret,Configuracion configuracion){
        String format="";
        String algoritmo = getAlgoritmo(configuracion);
        if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0)format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+configuracion.getEmail()+"%3Fsecret="+secret+"%26algorithm="+configuracion.getAlgoritmo()+"%26digits="+configuracion.getDigitos()+"%26counter="+configuracion.getContador_hotp();
        else format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/"+configuracion.getEmail()+"%3Fsecret="+secret+"%26algorithm="+algoritmo+"%26digits="+configuracion.getDigitos()+"%26period="+configuracion.getTiempo_totp();
        return format;
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
