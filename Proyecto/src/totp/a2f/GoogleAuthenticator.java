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

	
	public static final String getQRUrl(String username, String host, String secret) {
		String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";
		return String.format(format, username, host, secret);
                //String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s?secret=%s";
		//return String.format(format, "kari@mail.com", secret);
	}
        
        public static final String getQRUrl(String email, String secret) {
                String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s?secret=%s";
		return String.format(format, email, secret);
	}
        
        public static final String getQRUrlHOTP(String email, String secret) {
                //String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://hotp/%s?secret=%s&counter=%s";
                String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+email+"%3Fsecret="+secret+"%26counter=893";
                //String format2 = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+email+"?secret="+secret+"&counter=893";
		//return String.format(format, email, secret);
                return format;
                //https://chart.googleapis.com/chart?chs=400x400&chld=M|0&cht=qr&chl=otpauth://hotp/S000011%3Fsecret=TF2M6VZ7E666I57VILOI4XV324DMHUXA%26counter=893
                //otpauth://totp/TOTP00017410?secret=O6LVCAVTS2IJ25NKXKOOGCNTJIOFNUXA&counter=1&digits=6&issuer=privacyIDEA
                // otpauth://hotp/Name%20of%20Secret?secret=<secret>&issuer=Company&counter=123456&algorithm=SHA512&digits=6

	}
        
        public static final String getQRUrlHOTP(String email, String secret, int contador) {
            String format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+email+"%3Fsecret="+secret+"%26counter="+contador;
            return format;
        }
        
        
        public static final String getQRUrlConfiguracion(String secret,Configuracion configuracion){
            String format="";
            String algoritmo = getAlgoritmo(configuracion);
            if(configuracion.getTipo().compareToIgnoreCase("HOTP")==0)format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+configuracion.getEmail()+"%3Fsecret="+secret+"%26algorithm="+configuracion.getAlgoritmo()+"%26digits="+configuracion.getDigitos()+"%26counter="+configuracion.getContador_hotp();
            else format = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/"+configuracion.getEmail()+"%3Fsecret="+secret+"%26algorithm="+algoritmo+"%26digits="+configuracion.getDigitos()+"%26period="+configuracion.getTiempo_totp();
            return format;
        }
        
        
        private static String getAlgoritmo(Configuracion configuracion){
            if(configuracion.getAlgoritmo().compareToIgnoreCase("HmacSHA1")==0)return "SHA1";
            else if(configuracion.getAlgoritmo().compareToIgnoreCase("HmacSHA256")==0)return "SHA256";
            else return "SHA512";
        }
	
        
}
