package a2f;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * Clase que permite crear y validar códigos TOTP.
 * 
 * @author Iván Javier Parra
 *
 */


public class ManagerTOTP {
            
                    /* ********** INICIO CONSTANTES ********** */
                    /* --------------------------------------- */
    
        /**
         * Esto es el mod_divisor, es decir, si mi código es de 6 caracteres, elijo el 6 (1000000).
         * Ver: https://github.com/jchambers/java-otp/blob/master/src/main/java/com/eatthepath/otp/HmacOneTimePasswordGenerator.java    
        */
        private static final int[] DIGITS_POWER
        // 0  1   2    3     4      5       6        7         8
        = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};

    
        /**
         * Algoritmo HMAC por defecto.
         * En este caso el algoritmo por defecto es HmacSHA1.
         * Algoritmos disponibles: { HmacSHA1, HmacSHA256, HmacSHA512 }.
         */
        public static final String DEFAULT_ALGORITHM = "HmacSHA1";

    
        /**
         * Duración del time-step (en segundos) durante el cuál la clave será válida.
         * La traducción de "time-steps" es "intervalo de tiempo".
         */
        public static final int DEFAULT_INTERVAL = 30;
	
    
        /**
         * Cantidad de time-steps que voy a aceptar para validar en el pasado.
         * La RFC recomienda un 1 sola validación, por si se presenta retardos en la red.
         * Explicación: https://github.com/johnnymongiat/oath/blob/master/oath-otp/src/main/java/com/lochbridge/oath/otp/TOTPValidator.java
         * Ver: https://bitbucket.org/devinmartin/otp-sharp/wiki/TOTP
         * 
         */
        public static final int DEFAULT_STEPS = 1; //DEFAULT_DELAY_WINDOW
	
    
    
	/**
	 * Número de dígitos del código TOTP.     
	 */
	public static final int DEFAULT_LENGTH = 6;
	
        
	/**
	 * Default time 0 for the interval
	 */
	public static final int DEFAULT_T0 = 0;
        
        
        /**
         * Define la cantidad de intentos de validación, para evitar ataques de fuerza bruta.
         */
        public static final int DEFAULT_INTENTOS_VALIDACION = 5;
        
        
        
                    /* *********** FIN CONSTANTES ************ */
                    /* --------------------------------------- */
	
        
        
        
                    /* ********** INICIO VARIABLES ********** */
                    /* --------------------------------------- */
	
        private final String algoritmo;
	
	private final int intervaloTiempo;
	
	private final int longitudCodigo;
	
	private final int steps;
	
	private final int t0;
        
	
                    /* ************* FIN VARIABLES ********** */
                    /* --------------------------------------- */
    
    
                    /* ************* INICIO CONSTRUCTORES ********** */
                    /* --------------------------------------------- */
        
        /**
	 * Crea una instancia TOTP por defecto.
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
	
        
        /**
         * Crea una instancia TOTP con configuración propia: cantidad de digitos y periodo.
         * @param algoritmo
         * @param digitos
         * @param tiempo 
         */
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
        
        
                    /* ************* FIN CONSTRUCTORES ********** */
                    /* ------------------------------------------ */
               
        
        
        
        
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
	 * @return el steps que está siendo usado.
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
	 * Valida el código TOTP para el intervalo de tiempo actual y, si es
         * necesario, con el intevalo de tiempo anterior (por si hay retardos en la red).
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
		
                // Se realiza una validación con el time-step actual
                // y si no coinciden, se realiza una validación con
                // el time-step anterior.
                for (int i = 0; i <= steps; i++) {
                    // intervalo_tiempo - 0 = intervalo de tiempo actual.
                    // intervalo_tiempo - 1 = intervalo de tiempo anterior.
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
		byte[] hmac_result = hmac.getShaHash(secreto, message);
                
                // Paso 2: Truncamiento. 
                // offset va a tener el valor que está en la última posición de hmac_result.
                // el valor del offset me va a indicar a partir de que byte de hmac_result voy a formar el binary_code.
		int offset = hmac_result[hmac_result.length-1] & 0xf;
                /* Sacamos 4 bytes del hmac_result a partir del valor de offset.
                Por ejemplo, como está en la RFC, binary_code=0x50ef7f19
                */
		int binary_code = ((hmac_result[offset] & 0x7f) << 24) | ((hmac_result[offset + 1] & 0xff) << 16) | ((hmac_result[offset + 2] & 0xff) << 8) | (hmac_result[offset + 3] & 0xff);

                // Paso 3: Calcular el código TOTP y asegurarse de que contenga la cantidad de dígitos configurados.
                // Por ejemplo, si binary_code=0x50ef7f19 (hexadecimal), en decimal es 1357872921, lo divido por 1.000.000 y el resto es hotp="872921".
		int otp = binary_code % DIGITS_POWER[getLongitudCodigo()];
		String totp = Integer.toString(otp);
                // Si el String "totp" tiene una longitud menor a "digitos", agrego ceros a la izquierda en "totp" para completar.
		while (totp.length() < getLongitudCodigo()) {
			totp = "0" + totp;
		}
		return totp;
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
         * Current_Unix_Time=59; T0=0; X=30  ===> T=1
         * Current_Unix_Time=60; T0=0; X=30  ===> T=2
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
         */
        public static void main(String[] args) {
        
            // Genero una instancia de TOTP.
            ManagerTOTP manager = new ManagerTOTP();

            // Creo la clave secreta.
            byte[] secreto = SecretGenerator.generar();

            // Genero el QR Code
            String secretoCodificado = SecretGenerator.toBase32(secreto);
            String qr = URIGenerator.getQRUrlTOTP("alissa", "example.com", secretoCodificado);
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
                        byte[] secret = SecretGenerator.generar(SecretGenerator.Size.LARGE);
                        String secretC = SecretGenerator.toBase32(secret);
                        System.out.println(secretC);
                        break;
                    }
                    case 4:{
                        long tiempo_actual = System.currentTimeMillis();
                        long intervalo_tiempo = (tiempo_actual/1000) / 30;
                        for (int i = 0; i<=1;i++){
                            System.out.println("Intervalo de Tiempo: " + (intervalo_tiempo - i));
                        }
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