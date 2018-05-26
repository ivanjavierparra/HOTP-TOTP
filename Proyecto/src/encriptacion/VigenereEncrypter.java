package encriptacion;


/**
 * Encripta una cadena en base a la t�cnica de Vigenere. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposici�n es creada 
 * y almacenada internamente por la misma clase. 
 * Patr�n aplicado: Strategy.
 * 
*/
public class VigenereEncrypter extends TranspositionEncrypter
{
       protected String clave;  // contiene la clave que debe usarse junto a la tabla de transposici�n
       
       /**
        *  Inicia un encriptador con t�cnica de Vigenere. El mensaje a encriptar ser� inicializado 
        *  como la cadena vac�a ("") y el programador deber� cambiar luego ese valor mediante setOpenMessage(). 
        *  La clave de desplazamiento a usar, ser� iniciada con la secuencia "ABC". 
        */
       public VigenereEncrypter( )
       {
           this("", "ABC");
       }
          
       /**
        *  Inicia un encriptador con t�cnica de Vigenere. El mensaje a encriptar ser� inicializado 
        *  con el valor del par�metro y el programador podr� cambiar luego ese valor mediante setOpenMessage(). 
        *  La clave de desplazamiento a usar, ser� iniciada con la secuencia "ABC". 
        */
       public VigenereEncrypter( String mens )
       {
           this(mens, "ABC");
       }
          
       /**
        *  Inicia un encriptador con t�cnica de Vigenere. El mensaje a encriptar se inicializa 
        *  con el valor del par�metro mens. Si ese par�metro es null, el mensaje a encriptar se inicia como 
        *  la cadena vac�a ("") y luego el programador deber� cambiar ese valor con setOpenMessage().
        *  La clave de desplazamiento a usar, ser� iniciada con el par�metro key. Si este par�metro es null,
        *  la clave quedar� como "ABC".
        *  @param mens el mensaje abierto que ser� encriptado.
        *  @param key la clave de desplazamiento a usar.
        */
       public VigenereEncrypter( String mens, String key )
       {
           super(mens);
           if (key == null) key = "ABC";
           clave = key;
       }
       
       /**
        * Retorna la clave usada por el encriptador para la tabla de transposici�n. 
        * El m�todo est� disponible s�lo para efectos did�cticos...
        */
       public String getKey()
       {
           return clave;
       }
       
       /**
        * Encripta el mensaje abierto alojado en la clase, seg�n la t�cnica Vigenere. 
        * Retorna null si el proceso de encriptaci�n no pudo hacerse por haber caracteres extra�os en el 
        * mensaje abierto. 
        * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que 
        *         alg�n caracter del mensaje original no figuraba en el alfabeto base aceptado).
        */
       public String code ( )
       {
          // "mensaje" y "encriptado" son atributos protected de la clase Encrypter
          if ( ! isOk( mensaje ) ) return null;
          
          int ic = 0; // para recorrer la clave...
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < mensaje.length(); i++) 
          {
              char actual = mensaje.charAt(i);
              int iactual = ALFABETO.indexOf( actual );
              
              int iclave = ALFABETO.indexOf( clave.charAt(ic) );
              int icripto = ( iactual + iclave ) % ALFABETO.length();

              ic++;
              if ( ic == clave.length() ) ic = 0;
              
              b.append( transposicion.charAt( icripto ) );
          }
          
          encriptado = b.toString();
          return encriptado;
       }
       
       /**
        *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la t�cnica Vigenere.
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
          
          int ic = 0;
          int n = ALFABETO.length();
          StringBuffer b = new StringBuffer(""); 
          for (int i = 0; i < encriptado.length(); i++) 
          {
              char actual = encriptado.charAt(i);
              int iactual = transposicion.indexOf( actual );
              
              int iclave = ALFABETO.indexOf( clave.charAt(ic) );             
              int idecripto = ( iactual - iclave + n ) % n;
              
              ic++;
              if ( ic == clave.length() ) ic = 0;              
              
              b.append( ALFABETO.charAt( idecripto ) );
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
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptaci�n conocida: " + encriptado;
           return res + "\nT�cnica: Cifrado de Vigenere" + "\nTabla usada: " + transposicion + "\nClave: " + clave;
       }
}
