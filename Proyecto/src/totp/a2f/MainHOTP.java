package totp.a2f;

import java.util.Scanner;




/**
 * Clase que intenta testear los métodos de la clase HOTPManager.
 * 
 * @author ivancho
 */


public class MainHOTP {

    
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
            System.out.println("4.Salir.");
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
                    System.out.println("Chau.");
                    break;
                }
                default:{
                    System.out.println("Opcion incorrecta.");
                    break;
                }
            }
        }while(opcion != 4);
    }
    
}
