package totp.a2f;

import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase que permite crear y validar códigos HOTP.
 * 
 * Ver:
 *      https://github.com/picketbox/picketbox/blob/master/security-spi/spi/src/main/java/org/jboss/security/otp/HOTP.java
 *      https://github.com/johnnymongiat/oath/blob/master/oath-otp/src/main/java/com/lochbridge/oath/otp/HOTPBuilder.java
 *      https://github.com/link-nv/oath/blob/master/src/main/java/net/link/oath/HOTP.java
 * Ejemplo funcionando: http://asecuritysite.com/encryption/hotp
 * 
 * @author ivancho
 */


public class ManagerHOTP {
    
    /**
     * Usado para calcular el checksum.
     */
    private static final int[] DOUBLE_DIGITS = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
    
    
    
    /**
     * Esto es el mod_divisor, es decir, si mi código es de 6 caracteres, elijo el 6 (1000000).
     * Ver: https://github.com/jchambers/java-otp/blob/master/src/main/java/com/eatthepath/otp/HmacOneTimePasswordGenerator.java    
     */
                                             // 0  1   2    3     4      5       6        7         8
    private static final int[] DIGITS_POWER = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };
   
    
    
    /**
     * Algoritmo por defecto (Compatible con Google Authenticator).
     * En este caso el algoritmo por defecto es HmacSHA1.
     * The crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512).
     */
    public static final String DEFAULT_ALGORITHM = "HmacSHA1";
    
    
    
    /**
     * Es el contador de HOTP, el cual cambia según el uso. 
     * Por defecto, el contador arranca en 0.
     */
    public static final int DEFAULT_COUNT = 0;
    
    
    
    /**
     * Es el número de dígitos del código OTP.     
     */
    public static final int DEFAULT_CODE_DIGITS = 6;
    
    
    
    /**
     * Es una bandera que indica si un dígito de suma de comprobación (checksum),
     * debería añadirse al OTP.
     * Por defecto está en false.
     */
    public static final boolean DEFAULT_ADD_CHECKSUM = false;
    
    
    
    /**
     * The offset into the MAC result to begin truncation.
     * If this value is out of the range of 0 ... 15, then dynamic truncation will be used.
     * Dynamic truncation is when the last 4 bits of the last byte of the MAC are used to 
     * determine the start offset.
     */
    //public static final int DEFAULT_TRUNCATION_OFFSET = 0;
    
    
    
    
    /**
     * Esta constante indica cuántos OTP adicionales se pueden generar antes de que la validación falle.
     * Este parámetro trata de lidiar con la desincronización de contadores.
     * Yo establecí una ventana de 10, es decir, genero 10 códigos consecutivos y espero que el cliente ingrese un código que esté dentro de esos 10 para que la validación sea exitosa.
     * Explicación: http://mx.thirdvisit.co.uk/2014/01/02/getting-the-otpkeyprov-hotp-plug-in-to-work-with-google-authenitcator/
     */
    public static final int DEFAULT_WINDOW = 10;
    
    
    
       
    
    private final String algoritmo;
    
    private final int contador;
    
    private final int nroDigitos;
    
    private final boolean addChecksum;
    
    //private final int truncationOffset;
    
    private final int window;
    
    
    
    
    /**
     * Crea una instancia HOTP por defecto, la cual es compatible con Google Authenticator.     
     */
    public ManagerHOTP(){
        this.algoritmo = DEFAULT_ALGORITHM;
        this.contador = DEFAULT_COUNT;
        this.nroDigitos = DEFAULT_CODE_DIGITS;
        this.addChecksum = DEFAULT_ADD_CHECKSUM;
        this.window = DEFAULT_WINDOW;
    }
    
    
    /* Constructor de Prueba */
    public ManagerHOTP(String algoritmo,int contador,int digitos){
        this.algoritmo = algoritmo;
        this.contador = contador;
        this.nroDigitos = digitos;
        this.addChecksum = DEFAULT_ADD_CHECKSUM;
        this.window = DEFAULT_WINDOW;
    }
    
    
    
    
    /**
     * Genera un código HOTP para el contador actual.
     * @param secreto: es la clave secreta compartida entre el cliente y el servidor.
     * @return el código HOTP.
     */
    public final String generar(byte[] secreto) {
        return generarOTP(secreto, this.contador);
    }
    
    
    
    /**
     * Genera un código HOTP para el contador pasado como parámetro.
     * @param secreto: es la clave secreta compartida entre el cliente y el servidor.
     * @param counter: es el contador dado.
     * @return el código HOTP.
     */
    public final String generar(byte[] secreto, int counter) {
        return generarOTP(secreto, counter);
    }
    
    
    
    
    /**
     * Genera el código HOTP a partir del secreto y el contador.
     * @param secreto: es la clave secreta compartida entre el cliente y el servidor.
     * @param counter: es el contador dado.
     * @return el código HOTP.
     */
    final String generarOTP(byte[] secreto, int counter) {
        
        int digitos = this.addChecksum ? (this.nroDigitos + 1) : this.nroDigitos; //si addChekSum es verdadero entonces incrementame nroDigitos, sino devolvemos nroDigitos como esta.
        
        // el valor del counter (pasado como parametro) se convierte en un arreglo de bytes.
        byte[] text = new byte[8];
        for (int i = text.length - 1; i >= 0; i--) {
            text[i] = (byte) (counter & 0xff);
            counter >>= 8; // arithmetic shift right
        }
        
        // Paso 1: Generar el hash HMAC-SHA-1.
        byte[] hash = getShaHash(secreto, text);

        // Paso 2: Truncamiento dinámico según la sección 5.3 de RFC 4226.
        int offset = hash[hash.length - 1] & 0xf;
        /*
            if ((0 <= this.truncationOffset) &&
                (this.truncationOffset < (hash.length - 4))) {
            offset = truncationOffset;
        }
        */
        int binary =
                ((hash[offset] & 0x7f) << 24)
                        | ((hash[offset + 1] & 0xff) << 16)
                        | ((hash[offset + 2] & 0xff) << 8)
                        | (hash[offset + 3] & 0xff);
        
        
        
        
        // Paso 3: Calcular el valor de HOTP y asegurarse de que contenga la cantidad de dígitos configurados.
        int otp = binary % DIGITS_POWER[nroDigitos];
        if (addChecksum) {
            otp = (otp * 10) + calcularChecksum(otp, nroDigitos);
        }
        
        // Si el String "resultado" tiene una longitud menor a "digitos", agrego ceros a "resultado" para completar.
        String resultado = Integer.toString(otp);
        while (resultado.length() < digitos) {
            resultado = "0" + resultado;
        }
        //Todo el código anterior se puede reemplazar por:
        //String hotp = Strings.padStart(Integer.toString(otp), digits, '0');
        
        return resultado;
    }
    
    
    
    /**
     * Valida si el código ingresado se corresponde con el secreto y el contador que está en el servidor.
     * Se valida para un window=10, es decir, el usuario ingresa hasta 10 codigos.
     * @param secreto: valor del secreto que fue guardado por el service provider.
     * @param counter; valor del contador que fue guarado por el service provider.
     * @param codigo: el valor OTP que fue provisto por el cliente.
     * @return: retorna -1 si no hay coincidencia, sino retorna el contador incrementado tantos lugares como se ha saltado dentro de la ventana.
     */
    /*public final int validar(byte[] secreto, int counter, String codigo) {
        //EXPLICADO EN: https://github.com/speakeasyjs/speakeasy/wiki/General-Usage-for-Counter-Based-Token
        int i = 0;
        while (i++ <= window) {
            if (generar(secreto,counter).equals(codigo)) return counter + i; //¿Será así?
        }
        return -1;
    }*/
    
    //     ^                              ^
    //     |   ver el codigo de arriba    |
    //
    
    
    
    /**
     * Valida si el código ingresado se corresponde con el secreto y el contador que está en el servidor.
     * Se valida para un window=10, es decir, el usuario ingresa hasta 10 codigos.
     * @param secreto: valor del secreto que fue guardado por el service provider.
     * @param counter; valor del contador que fue guarado por el service provider.
     * @param codigo: el valor OTP que fue provisto por el cliente.
     * @return: retorna -1 si no hay coincidencia, sino retorna el contador incrementado tantos lugares como se ha saltado dentro de la ventana.
     */
    public final int validar(byte[] secreto, int counter, String codigo) {
        //EXPLICADO EN: https://github.com/speakeasyjs/speakeasy/wiki/General-Usage-for-Counter-Based-Token
        int i = 0;
        while (i++ <= window) {
            if (generar(secreto,counter).equals(codigo)) return counter + 1; //¿Será así?
            else counter = counter + 1;
        }
        return -1;
    }
    
    
    
    
    
    /**
     * This method uses the JCE to provide the crypto algorithm.
     * HMAC computes a Hashed Message Authentication Code with the
     * crypto hash algorithm that is in the variable "algoritmo".
     * @param key: the bytes to use for the HMAC key.
     * @param text: the message or text to be authenticated.
     * @return the Hmac as byte[].
     */
    
    public byte[] getShaHash(byte[] key, byte[] text) {
        try {
                Mac hmac = Mac.getInstance(this.algoritmo);
                SecretKeySpec macKey = new SecretKeySpec(key, "RAW");
                hmac.init(macKey);
                return hmac.doFinal(text);
        } catch (GeneralSecurityException e) {
                throw new IllegalStateException(e);
        }
    }
    
    
    
    
    /**
     * Calculates the checksum using the credit card algorithm.
     * This algorithm has the advantage that it detects any single
     * mistyped digit and any single transposition of
     * adjacent digits.
     * 
     * @param num: the number to calculate the checksum for.
     * @param digits: number of significant places in the number.
     * @return: the checksum of num. 
     */
    private static int calcularChecksum(long num, int digits) {
        boolean doubleDigit = true;
        int total = 0;
        while (0 < digits--) {
            int digit = (int) (num % 10);
            num /= 10;
            if (doubleDigit) {
                digit = DOUBLE_DIGITS[digit];
            }
            total += digit;
            doubleDigit = !doubleDigit;
        }
        int result = total % 10;
        if (result > 0) {
            result = 10 - result;
        }
        return result;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public int getContador() {
        return contador;
    }

    public int getNroDigitos() {
        return nroDigitos;
    }

    public boolean isAddChecksum() {
        return addChecksum;
    }

    public int getWindow() {
        return window;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
