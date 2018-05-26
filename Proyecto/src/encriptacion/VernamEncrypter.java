package encriptacion;


/**
 * Encripta una cadena en base a la t�cnica de Vernam original. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La clave usada para encriptar (por 
 * razones de simplicidad) es la tabla de transposici�n almacenada internamente por la clase 
 * TranspositionEncrypter. De esa tabla, se usan tantos caracteres a modo de clave como sea el largo 
 * de la cadena a encriptar (de acuerdo a la idea de Vernam), y se hace un XOR entre cada caracter 
 * de la cadena a encriptar y cada caracter de la clave. Notar que en este caso, el mensaje encriptado
 * puede contener caracteres que no correspondan al alfabeto base (representado por la constante 
 * Encrypter.ALFABETO). 
 * Patr�n aplicado: Strategy.
 * 
 *
 */
public class VernamEncrypter extends TranspositionEncrypter
{
       /**
        *  Inicia un encriptador con t�cnica de Vernam. El mensaje a encriptar ser� inicializado 
        *  como la cadena vac�a ("") y el programador deber� cambiar luego ese valor mediante setOpenMessage().
        */
       public VernamEncrypter( )
       {
           this("");
       }
    
       /**
        *  Inicia un encriptador con t�cnica de Vernam. El mensaje a encriptar se inicializa 
        *  con el valor del par�metro mens. Si ese par�metro es null, el mensaje a encriptar se inicia como 
        *  la cadena vac�a ("") y luego el programador deber� cambiar ese valor con setOpenMessage().
        *  
        *  @param mens el mensaje abierto que ser� encriptado.
        */
       public VernamEncrypter( String mens )
       {
           super(mens);
       }
       
       /**
        * Retorna la clave usada por el encriptador. El m�todo est� disponible s�lo para
        * efectos did�cticos... Si el mensaje a encriptar es la cadena vac�a (""), el m�todo
        * retornar� null. La t�cnica es simple: se hace un XOR entre el mensaje abierto y el 
        * mensaje encriptado...
        * 
        * @return la clave usada por el encriptador.
        */
       public String getKey()
       {
           if ( mensaje == "" || encriptado == null || encriptado == "" ) return null;
           return doXOR( mensaje, encriptado );
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, seg�n la t�cnica de Vernam original. Retorna null 
        * si el proceso de encriptaci�n no pudo hacerse por haber caracteres extra�os en el mensaje abierto. 
        * Notar que el mensaje encriptado puede contener caracteres que no correspondan al alfabeto base 
        * (representado por la constante Encrypter.ALFABETO), ya que en el encriptado se aplica un XOR entre
        * los caractes del mensaje abierto y los caracteres de la clave.
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         alg�n caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          encriptado = doXOR(mensaje, transposicion);
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la t�cnica de Vernam original.
        *  El m�todo PUEDE CAMBIAR el valor del mensaje abierto almacenado en la clase (lo cual ocurrir� si se 
        *  invoca a setOpenMessage() y luego se invoca a decode() sin invocar previamente a code()). Retorna null 
        *  si el proceso de desencriptaci�n no pudo hacerse (lo que ocurrir� si el mensaje encriptado era null 
        *  o era la cadena vac�ac("")). 
        *  @return una cadena con el mensaje desencriptado, o null si la cadena no pudo desencriptarse.
        */
       public String decode ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( encriptado == null || encriptado == "" ) return null;
          mensaje = doXOR(encriptado, transposicion);
          return mensaje;
       }
       
       /**
        * Retorna una cadena con informaci�n general sobre el encriptador.
        * @return un cadena con informaci�n del encriptador.
        */
       public String toString()
       {
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptaci�n conocida: " + encriptado;
           return res + "\nT�cnica: Cifrado de Vernam" + "\nClave: " + getKey();
       }
       
       private String doXOR( String m1, String m2 )
       {
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < m1.length(); i++) 
          {
              char actual = m1.charAt(i);
              char clave  = m2.charAt(i);
              char cripto = (char)(actual ^ clave);             
              b.append( cripto );
          }
          return b.toString();
       }
}
