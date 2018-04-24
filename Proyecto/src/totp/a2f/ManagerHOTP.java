package totp.a2f;

import java.security.GeneralSecurityException;
import java.util.Scanner;
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
     * Es el número de dígitos del código HOTP.     
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
     * Constructor: Crea una instancia HOTP por defecto, la cual es compatible con Google Authenticator.     
     */
    public ManagerHOTP(){
        this.algoritmo = DEFAULT_ALGORITHM;
        this.contador = DEFAULT_COUNT;
        this.nroDigitos = DEFAULT_CODE_DIGITS;
        this.addChecksum = DEFAULT_ADD_CHECKSUM;
        this.window = DEFAULT_WINDOW;
    }
    
    
    /* Constructor: Crea una instancia HOTP con atributos específicos. */
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
        HMAC hmac = new HMAC();
        hmac.setAlgoritmo(this.getAlgoritmo());
        byte[] hash = hmac.getShaHash(secreto, text);

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
    
    
    
    
    
    /**
    *  Se testean los métodos de la clase HOTPManager.
    * 
    * @author ivancho
    */
    public static void main(String[] args) {
        
        //creo instancia de HOTPManager.
        ManagerHOTP manager = new ManagerHOTP();
        
        //Creo la clave unica.
        byte[] secreto = Secreto.generar();
        
        // genero el código QR compatible con Google Authenticator.
        String secretoCodificado = Secreto.toBase32(secreto);
        String qr = GoogleAuthenticator.getQRUrlHOTP("hotpexample@mail.com", secretoCodificado,893);
        System.out.println(qr); //Imprime el enlace al codigo QR.
        //NOTA: Copiando el "secretoCodificado" que aparece en el QR, lo ponemos todo en 
        //minuscula en google authenticator manualmente y funciona, sin tener que 
        //leer el QR.
        //http://www.asaph.org/2016/04/google-authenticator-2fa-java.html
        
               
        //Creo un Menú.
        int opcion;
        String codigo = "";
        
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("1.Ingresar codigo.");
            System.out.println("2.Mostrar codigos.");
            System.out.println("3.Jugando con Google Authenticator.");
            System.out.println("5.Jugando con HOTP.");
            System.out.println("5.Salir.");
            opcion = scanner.nextInt();
            switch(opcion){
                case 1:{
                    System.out.println(qr); //Imprime el enlace al código QR.

                    // itero hasta que el usuario ingrese un codigo.
                    while(codigo.compareToIgnoreCase("")==0){
                        System.out.println("Codigo: ");
                        codigo = scanner.nextLine();
                    }
                    
                    //verifico si el código es correcto.
                    int valid = manager.validar(secreto,893,codigo);
                    if(valid!=-1)System.out.println("exito!" + " Contador="+valid );
                    else System.out.println("fracaso!");
                    
                    codigo = "";
                    break;
                }
                case 2:{
                    //genero varios códigos consecutivos, y veo si Google AUthenticator está sincronizado con el servidor.
                    System.out.println("Contador: " + 0 + ", Codigo: " + manager.generar(secreto, 0));
                    System.out.println("Contador: " + 1 + ", Codigo: " + manager.generar(secreto, 1));
                    System.out.println("Contador: " + 893 + ", Codigo: " + manager.generar(secreto, 893));
                    System.out.println("Contador: " + 894 + ", Codigo: " + manager.generar(secreto, 894));
                    System.out.println("Contador: " + 895 + ", Codigo: " + manager.generar(secreto, 895));
                    System.out.println("Contador: " + 896 + ", Codigo: " + manager.generar(secreto, 896));
                    System.out.println("Contador: " + 897 + ", Codigo: " + manager.generar(secreto, 897));
                    System.out.println("Contador: " + 898 + ", Codigo: " + manager.generar(secreto, 898));
                    break;
                }
                case 3:{
                    String email="ejemplo@mail.com";
                    byte[] secret = Secreto.generar(Secreto.Size.MEDIUM); 
                    String secretoCod = Secreto.toBase32(secret);
                    String algoritmo = "SHA256";
                    int digits = 7;
                    int contador = 894;
                    String qr2 = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://hotp/"+email+"%3Fsecret="+secretoCod+"%26algorithm="+algoritmo+"%26digits="+digits+"%26counter="+contador;
                    System.out.println(qr2);
                    //qr2 = "https://chart.googleapis.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/"+"ejemplo@mail.com"+"%3Fsecret="+"LMMLFRAJS3V6PBFYJLARHC3BUHHSSEKB"+"%26algorithm="+"SHA256"+"%26digits="+7+"%26period="+35;
                    //System.out.println(qr2);
                    ManagerHOTP managerhotp = new ManagerHOTP("HmacSHA256",contador,digits);
                    System.out.println("Contador: " + 0 + ", Codigo: " + managerhotp.generar(secreto, 0));
                    System.out.println("Contador: " + 1 + ", Codigo: " + managerhotp.generar(secreto, 1));
                    System.out.println("Contador: " + 893 + ", Codigo: " + managerhotp.generar(secreto, 893));
                    System.out.println("Contador: " + 894 + ", Codigo: " + managerhotp.generar(secreto, 894));
                    System.out.println("Contador: " + 895 + ", Codigo: " + managerhotp.generar(secreto, 895));
                    System.out.println("Contador: " + 896 + ", Codigo: " + managerhotp.generar(secreto, 896));
                    System.out.println("Contador: " + 897 + ", Codigo: " + managerhotp.generar(secreto, 897));
                    System.out.println("Contador: " + 898 + ", Codigo: " + managerhotp.generar(secreto, 898));
                    break;
                }
                case 4:{
                    int counter = 8;
                    byte[] text = new byte[8];
                    for (int i = text.length - 1; i >= 0; i--) {
                        text[i] = (byte) (counter & 0xff);
                        counter >>= 8;
                    }
                    System.out.println("Secreto: "+secreto);
                    System.out.println("Mensaje: "+text);
                    HMAC hmac = new HMAC();
                    hmac.setAlgoritmo(manager.getAlgoritmo());
                    byte[] hash = hmac.getShaHash(secreto, text);
                    System.out.println("Hash: "+hash);
                    int offset = hash[hash.length - 1] & 0xf;
                    System.out.println("Offset: "+offset);
                    int binary =
                    ((hash[offset] & 0x7f) << 24)
                            | ((hash[offset + 1] & 0xff) << 16)
                            | ((hash[offset + 2] & 0xff) << 8)
                            | (hash[offset + 3] & 0xff);
                    System.out.println("Binary: "+binary);
                    int otp = binary % 1000000;
                    System.out.println("OTP: "+otp);
                    String resultado = Integer.toString(otp);
                    while (resultado.length() < 6) {
                        resultado = "0" + resultado;
                    }
                    System.out.println("Resultado: "+resultado);
                    break;
                }
                case 5:{
                    System.out.println("Chau.");
                    break;
                }
                default:{
                    System.out.println("Opcion incorrecta.");
                    break;
                }
            }
        }while(opcion != 5);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
