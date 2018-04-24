package totp.a2f;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase que permite crear y validar códigos TOTP.
 * 
 * @author ivancho
 *
 */




public class ManagerTOTP {

        /**
         * Esto es el mod_divisor, es decir, si mi código es de 6 caracteres, elijo el 6 (1000000).
         * Ver: https://github.com/jchambers/java-otp/blob/master/src/main/java/com/eatthepath/otp/HmacOneTimePasswordGenerator.java    
        */
        private static final int[] DIGITS_POWER
        // 0  1   2    3     4      5       6        7         8
        = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    
    
        /**
         * Algoritmo por defecto (Compatible con Google Authenticator).
         * En este caso el algoritmo por defecto es HmacSHA1.
         * The crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512).
         */
        public static final String DEFAULT_ALGORITHM = "HmacSHA1";

    
    

        /**
         * Intervalo de tiempo por defecto (en segundos) durante el cuál la clave será válida.
         */
        public static final int DEFAULT_INTERVAL = 30;
	
    
    
    
        /**
         * Default time interval steps to check into past for validity. 
         * If required, a time correction can be specified to compensate of an out of sync local clock.
         * When an OTP is generated at the end of a
         * time-step window, the receiving time most likely falls into the next
         * time-step window.
         * 
         * RFC 6238 Section 5.2 defines the recommended conditions for accepting a TOTP validation code. 
         * The exact text in the RFC is "We RECOMMEND that at most one time step is
         * allowed as the network delay."
         * 
         * Explicación: https://github.com/johnnymongiat/oath/blob/master/oath-otp/src/main/java/com/lochbridge/oath/otp/TOTPValidator.java
         * 
         * Es un tiempo en segundos usado para sincronización entre el cliente y el servidor.
         * 
         * Problema: el cliente genera un código y lo envía al servidor, 
         * pero cuando llega al servidor, hay un desfasaje de tiempo, por ejemplo, yo cliente 
         * genero el código a las 2:00 pm y llega ese código al servidor a las 2:10 pm,
         * entonces cuando el servidor calcule su código para contrastarlo con el cliente, resulta
         * que el horario no es el mismo, por lo tanto la validación del código será errónea.
         * 
         * Solución: con el step lo que hacemos es ir disminuyendo el tiempo del servidor para 
         * llegar al horario del cliente. El valor del step en este caso es de 1 segundo, por
         * lo que al tiempo del servidor sólo se le restará 1 segundo.
         * 
         * DEFAULT_STEPS se trata de una ventana, un time delay. 
         *          * 
         * Ver: https://bitbucket.org/devinmartin/otp-sharp/wiki/TOTP
         * 
         * var window = new VerificationWindow(previous:1, future:1);
         * This means that the current step, and 1 step prior to the current will be allowed in the match. 
         * If you wanted to accept 5 steps backward (not recommended in the RFC) then you would change 
         * the previous parameter to 5.
         * There is also a parameter called future that allows you to match future codes. This might be 
         * useful if the client is ahead of the server by just enough 
         * that the code provided in slightly ahead.
         */
        public static final int DEFAULT_STEPS = 1; //DEFAULT_DELAY_WINDOW
	
    
    
    
	/**
	 * Número de dígitos del código HOTP.     
	 */
	public static final int DEFAULT_LENGTH = 6;
	
        
        
	/**
	 * Default time 0 for the interval
	 */
	public static final int DEFAULT_T0 = 0;
        
        
        
	
	private final String algoritmo;
	
	private final int intervaloTiempo;
	
	private final int longitudCodigo;
	
	private final int steps;
	
	private final int t0;
	
	
        
        /**
	 * Crea una instancia TOTP por defecto que es compatible con Google Authenticator.
	 */
	public ManagerTOTP() {
		this(DEFAULT_ALGORITHM, DEFAULT_INTERVAL, DEFAULT_LENGTH, DEFAULT_STEPS, DEFAULT_T0);
	}
	
        
	
        /**
         * Crea una instancia TOTP con algortimo e intervalo propios.
         * 
	 * @param algoritmo: el algoritmo a usar (HmacSHA1, HmacSHA256, HmacSHA512).
	 * @param intervalo: el intervalo de tiempo en segundos a usar.
	 */
	public ManagerTOTP(String algoritmo, int intervalo) {
		this(algoritmo, intervalo, DEFAULT_LENGTH, DEFAULT_STEPS, DEFAULT_T0);
	}
	
        
        
        
	/**
	 * Crea una instancia TOTP con intervalo de tiempo propio.
	 * 
	 * @param intervalo the time interval to use
	 */
	public ManagerTOTP(int intervalo) {
		this(DEFAULT_ALGORITHM, intervalo, DEFAULT_LENGTH, DEFAULT_STEPS, DEFAULT_T0);
	}

        
        
        
	/**
         * Crea una instancia TOTP con intervalo de tiempo propio, longitud de còdigo y steps.
         * 
	 * @param intervalo: el intervalo de tiempo a usar (en segundos).
	 * @param longitud: la longitud del código a usar, debe estar entre 1 y 8.
	 * @param steps the steps in history to validate code
	 */
	public ManagerTOTP(int intervalo, int longitud, int steps) {
		this(DEFAULT_ALGORITHM, intervalo, longitud, steps, DEFAULT_T0);
	}
	
        
        
        
	/**
	 * Crea una instancia TOTP con configuración propia.
         * 
	 * 
	 * @param algoritmo: el algoritmo a usar (HmacSHA1, HmacSHA256, HmacSHA512).
	 * @param intervalo el intervalo de tiempo a usar (en segundos).
	 * @param longitud la longitud del código; debe estar entre 1 y 8.
	 * @param steps the steps in history to validate code
	 * @param t0 the time 0 to be used for interval
	 */
	public ManagerTOTP(String algoritmo, int intervalo, int longitud, int steps, int t0) {
		this.algoritmo = algoritmo;
		this.intervaloTiempo = Math.abs(intervalo);
		this.longitudCodigo = Math.abs(longitud);
		this.steps = Math.abs(steps);
		this.t0 = Math.abs(t0);
		
		if (longitud > DIGITS_POWER.length || longitud < 6) {
			throw new IllegalArgumentException("Longitud debe estar entre 6 y 8");
		}
	}
	
        
        public ManagerTOTP(String algoritmo,int digitos,int tiempo){
            this.algoritmo = algoritmo;
            this.longitudCodigo = digitos;
            this.intervaloTiempo = tiempo;
            this.steps = DEFAULT_STEPS;
            this.t0 = DEFAULT_T0;
            
            if (digitos > DIGITS_POWER.length || digitos < 6) {
			throw new IllegalArgumentException("Longitud debe estar entre 6 y 8");
		}
        }
        
        
	/**
	 * @return el algoritmo usado.
	 */
	public String getAlgoritmo() {
		return algoritmo;
	}
	
	/**
	 * @return el intervalo de tiempo usado.
	 */
	public int getIntervaloTiempo() {
		return intervaloTiempo;
	}
	
	/**
	 * @return la longitud del código usada.
	 */
	public int getLongitudCodigo() {
		return longitudCodigo;
	}
	
	/**
	 * @return the steps being used
	 */
	public int getSteps() {
		return steps;
	}
	
	/**
	 * @return the time 0 to be used for interval
	 */
	public int getT0() {
		return t0;
	}
	
        
        
        
        
	/**
	 * Genera el código TOTP para el intervalo de tiempo actual.
         * 
	 * @param secreto: clave secreta.
	 * @return el código TOTP.
	 * 
	 */
	public final String generar(byte[] secreto) {
		return generarOTP(secreto, getCurrentTimeInterval());
	}
	
        
        
	/**
	 * Genera el código TOTP para el tiempo dado.
	 * 
	 * @param secreto: clave secreta. 
	 * @param tiempo: el tiempo en milisegundos para generar el código.
	 * @return el código TOTP.
	 * 
	 */
	public final String generar(byte[] secreto, long tiempo) {
		return generarOTP(secreto, getTimeInterval(tiempo));
	}
	
        
        
	/**
	 * Valida el código TOTP para el intervalo de tiempo actual.
	 * 
	 * @param secreto: la clave secreta.
	 * @param codigo: el código a validar.
	 * @return true si el código es válido.
	 * 
	 */
	public final boolean validar(byte[] secreto, String codigo) {
		return validar(secreto, codigo, System.currentTimeMillis());
	}
	
        
        
	/**
	 * Valida el código TOTP para el intervalo de tiempo actual.
         * 
	 * @param secreto: la clave secreta.
	 * @param codigo: el código a validar.
	 * @param tiempo: el tiempo en milisegundos que se tiene en cuenta para generar el código y contrastarlo con el pasado por parámetro.
	 * @return verdadero si el código es válido.
	 * 
	 */
	public final boolean validar(byte[] secreto, String codigo, long tiempo) {
		int steps = getSteps();
		long intervalo_tiempo = getTimeInterval(tiempo);
		
		for (int i = 0; i <= steps; i++) {
			boolean result = validarOTP(secreto, intervalo_tiempo - i, codigo);
			if (result) {
				return true;
			}
		}

		return false;
	}
	
        
        
        
        
	/**
	 * Genera el código TOTP para el intervalo de tiempo pasado como parámetro.
	 * 
	 * @param secreto: la clave secreta. 
	 * @param intervalo_tiempo: el intervalo de tiempo a usar (en milisegundos).
	 * @return el código TOTP.
	 * 
	 */
	final String generarOTP(byte[] secreto, long intervalo_tiempo) {
		// Paso 1: Generar el hash HMAC-SHA-1.
                byte[] message = ByteBuffer.allocate(8).putLong(intervalo_tiempo).array();
		
                HMAC hmac = new HMAC();
                hmac.setAlgoritmo(this.getAlgoritmo());
		byte[] hash = hmac.getShaHash(secreto, message);
                
                // Paso 2: Truncamiento. 
		int off = hash[hash.length-1] & 0xf;
		int bin = ((hash[off] & 0x7f) << 24) | ((hash[off + 1] & 0xff) << 16) | ((hash[off + 2] & 0xff) << 8) | (hash[off + 3] & 0xff);

                // Paso 3: Calcular el código TOTP y asegurarse de que contenga la cantidad de dígitos configurados.
		int otp = bin % DIGITS_POWER[getLongitudCodigo()]; // código TOTP = TOTP mod 10^d, where d is the desired number of digits of the one-time password.
		String result = Integer.toString(otp);
		while (result.length() < getLongitudCodigo()) {
			result = "0" + result;
		}
		return result;
	}
	
        
        
        /**
         * Genera el código TOTP a partir de "secreto" y "intervalo_tiempo" y lo compara con "codigo".
         * 
         * @param secreto: clave secreta.
         * @param intervalo_tiempo: tiempo usado para generar el código.
         * @param codigo: código TOTP a ser validado.
         * @return true si los códigos son iguales, falso sino. 
         */
	final boolean validarOTP(byte[] secreto, long intervalo_tiempo, String codigo) {
		String hash = generarOTP(secreto, intervalo_tiempo);
		return hash.equals(codigo);
	}
	
        
        
	
	
        
        
        /**
         * Esta fórmula surge de RFC 6238. 
         * T = (Current Unix time - T0) / X
         * For example, with T0 = 0 and Time Step X = 30, T = 1 if the current
         * Unix time is 59 seconds, and T = 2 if the current Unix time is
         * 60 seconds.
         * @param time: el tiempo en milisegundos.
         * @return intervalo de tiempo en segundos.
         */
	long getTimeInterval(long time) {
		return ((time / 1000) - getT0()) / getIntervaloTiempo();
	}
	
	
        
        /**
         * Devuelve el intervalo de tiempo actual en segundos.
         * @return tiempo actual en segundos.
         */
        long getCurrentTimeInterval() {
		return getTimeInterval(System.currentTimeMillis());
	}
	
        
        
        
        
        /**
         * Se testean los métodos de la clase TOTPManager. 
         * @param args 
         */
        public static void main(String[] args) {
        
        // Genero una instancia de TOTP.
        ManagerTOTP manager = new ManagerTOTP();
        
        // Creo la clave secreta.
        byte[] secreto = Secreto.generar();
        
        // Genero el Google Authenticator QR Code
        String secretoCodificado = Secreto.toBase32(secreto);
        String qr = GoogleAuthenticator.getQRUrl("alissa", "example.com", secretoCodificado);
        System.out.println(qr); //Imprime el enlace al codigo QR.
        //NOTA: Copiando el "encoded" que aparece en el QR, lo ponemos todo en 
        //minuscula en google authenticator manualmente y funciona, sin tener que 
        //leer el QR.
        //http://www.asaph.org/2016/04/google-authenticator-2fa-java.html
        
        
        
        // Creo el Menú.
        int opcion = 0;
        String codigo = "";
        
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("1.Ingresar codigo.");
            System.out.println("2.Ver time.");
            System.out.println("3.Salir.");
            opcion = scanner.nextInt();
            switch(opcion){
                case 1:{
                    System.out.println(qr); //Imprime el enlace al codigo QR.

                    // itero hasta que el código tenga un valor.
                    while(codigo.compareToIgnoreCase("")==0){
                        System.out.println("Codigo: ");
                        codigo = scanner.nextLine();
                    }
                    
                    // Valido si el código es correcto.
                    boolean valid = manager.validar(secreto, codigo);
                    if(valid)System.out.println("exito!");
                    else System.out.println("fracaso!");
                    
                    codigo = "";
                    break;
                }
                case 2:{
                    long time = (System.currentTimeMillis()/1000)/30;
                    System.out.println(""+time);
                    break;
                }
                case 3:{
                    System.out.println("Chau.");
                    break;
                }
                default:{
                    System.out.println("Opcion incorrecta.");
                    break;
                }
            }
        }while(opcion != 3);
    }
        
        
        
        
        
        
        
}