package encriptacion;

/**
 * Encripta una cadena en base a una tabla de transposici�n. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposici�n es creada 
 * y almacenada internamente por la misma clase.
 * Patr�n aplicado: Strategy.
 * 
 * @author Iván Javier Parra
 */
public class TranspositionEncrypter extends Encrypter
{
       // la tabla de transposicion...
       protected static final String transposicion = "xyzDEFGH.,;IJKLMN�oPQRSTU56789VWXYZ abcdefgh:?!ijklmn�Opqrstu01234vwABC";
  
       /**
        *  Inicia un encriptador con t�cnica de tabla de transposici�n. El mensaje a encriptar ser� inicializado 
        *  como la cadena vac�a ("") y el programador deber� cambiar luego ese valor mediante setOpenMessage().
        */
       public TranspositionEncrypter( )
       {
           this("");
       }
    
       /**
        *  Inicia un encriptador con t�cnica de tabla de transposici�n. El mensaje a encriptar se inicializa 
        *  con el valor del par�metro mens. Si ese par�metro es null, el mensaje a encriptar se inicia como 
        *  la cadena vac�a ("") y luego el programador deber� cambiar ese valor con setOpenMessage().
        *  @param mens el mensaje abierto que ser� encriptado.
        */
       public TranspositionEncrypter( String mens )
       {
           super(mens);
       }
       
       /**
        * Retorna la tabla de transposici�n usada por el encriptador. El m�todo est� disponible s�lo para
        * efectos did�cticos...
        */
       public static String getTranspositionTable()
       {
           return transposicion;
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, seg�n la t�cnica de tabla de transposici�n. 
        * Retorna null si el proceso de encriptaci�n no pudo hacerse por haber caracteres extra�os en el 
        * mensaje abierto. 
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         alg�n caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < mensaje.length(); i++) 
          {
              char actual = mensaje.charAt(i);
              int iactual = Encrypter.ALFABETO.indexOf( actual );
              
              b.append( transposicion.charAt( iactual ) );
          }
          
          encriptado = b.toString();
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la t�cnica de tabla de transposici�n.
        *  El m�todo PUEDE CAMBIAR el valor del mensaje abierto almacenado en la clase (lo cual ocurrir� si se 
        *  invoca a setOpenMessage() y luego se invoca a decode() sin invocar previamente a code()). Retorna null 
        *  si el proceso de desencriptaci�n no pudo hacerse por haber caracteres extra�os en el mensaje encriptado. 
        *  @return una cadena con el mensaje desencriptado, o null si la cadena no pudo desencriptarse (debido a 
        *          que alg�n caracter del mensaje encriptado no era v�lido).
        */
       public String decode ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( encriptado ) ) return null;
          
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < encriptado.length(); i++) 
          {
              char actual = encriptado.charAt(i);
              int iactual = transposicion.indexOf( actual );
              
              b.append( Encrypter.ALFABETO.charAt( iactual ) );
          }
          
          mensaje = b.toString();
          return mensaje;
       }
       
       /**
        * Retorna una cadena con informaci�n general sobre el encriptador.
        * @return un cadena con informaci�n del encriptador.
        */
       public String toString()
       {
           return super.toString() + "\nT�cnica: Tabla de Transposici�n" + "\nTabla usada: " + transposicion;
       }
}
