package totp.a2f;

import java.util.Scanner;

/**
 * Clase que intenta testear los métodos de la clase TOTPManager. 
 * 
 * @author ivancho
 */


public class MainTOTP {

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
