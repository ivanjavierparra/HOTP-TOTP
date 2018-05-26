package encriptacion;


/**
 * Encripta una cadena en base a la encriptaci�n de C�sar. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. 
 * Patr�n aplicado: Strategy.
 * 
 * @author Iván
 * 
 */
public class CesarEncrypter extends Encrypter
{
   private int k;   // el factor de desplazamiento a usar   

   /**
    *  Inicia un encriptador con t�cnica de C�sar, con factor de desplazamiento 3. El mensaje a encriptar 
    *  ser� inicializado como la cadena vac�a ("") y el programador deber� cambiar luego ese valor mediante
    *  setOpenMessage().
    */
   public CesarEncrypter( )
   {
       this("", 3);
   }

   /**
    *  Inicia un encriptador con t�cnica de C�sar, con factor de desplazamiento 3. El mensaje a encriptar 
    *  se inicializa con el valor del par�metro. Si ese par�metro es null, el mensaje a encriptar se inicia
    *  como la cadena vac�a ("") y luego el programador deber� cambiar ese valor con setOpenMessage().
    *  @param mens el mensaje abierto que ser� encriptado.
    */
   public CesarEncrypter( String mens )
   {
       this(mens, 3);
   }
   
   /**
    *  Inicia un encriptador con t�cnica de C�sar, con factor de desplazamiento igual a desp.
    *  Si el valor informado en desp es menor a cero, el factor de desplazamiento se ajustar� 
    *  como igual a 3. El mensaje a encriptar se inicializa con el valor del par�metro mens. Si ese par�metro
    *  es null, el mensaje a encriptar se inicia como la cadena vac�a ("") y luego el programador deber� 
    *  cambiar ese valor con setOpenMessage().
    *  @param mens el mensaje abierto que ser� encriptado.
    *  @param desp el valor del factor de desplazamiento a usar.
    */
   public CesarEncrypter( String mens, int desp )
   {
       super(mens);
       if (desp < 0) desp = 3;
       k = desp;
   }
   
   /**
    * Retorna el factor de desplazamiento usado por el encriptador.
    * @return el factor de desplazamiento usado en esta instancia.
    */
   public int getDisplacement()
   { 
       return k;
   }

   /**
    * Encripta el mensaje abierto alojado en la clase, seg�n la t�cnica de C�sar. Retorna null si el proceso de 
    * encriptaci�n no pudo hacerse por haber caracteres extra�os en el mensaje abierto. El factor de desplazamiento 
    * a usar, fue informado a la clase mediante alguno de sus constructores o se ajust� a k = 3 por default y puede 
    * obtenerse mediante getDisplacement().  
    * @return una cadena con el mensaje encriptado, o null si la cadena no pudo encriptarse (debido a que alg�n caracter
    *          del mensaje original no figuraba en el alfabeto base aceptado).
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
          
          int icripto = ( iactual + k ) % Encrypter.ALFABETO.length();
          b.append( Encrypter.ALFABETO.charAt( icripto ) );
      }
      
      encriptado = b.toString();
      return encriptado;
   }
   
   /**
    *  Desencripta un mensaje encriptado (alojado en la clase), siguiendo la t�cnica de C�sar. El m�todo PUEDE CAMBIAR 
    *  el valor del mensaje abierto almacenado en la clase (lo cual ocurrir� si se invoca a setOpenMessage() y luego se 
    *  invoca a decode() sin invocar previamente a code()). Retorna null si el proceso de desencriptaci�n no pudo 
    *  hacerse por haber caracteres extra�os en el mensaje encriptado. El factor de desplazamiento a usar, fue 
    *  informado a la clase mediante alguno de sus constructores o se ajust� a k = 3 por default y puede obtenerse 
    *  mediante getDisplacement(). 
    *  @return una cadena con el mensaje desencriptado, o null si la cadena no pudo desencriptarse (debido a 
    *          que alg�n caracter del mensaje encriptado no era v�lido).
    */
   public String decode ( )
   {
      // "encriptado" es atributo protected de la clase Encrypter
      if ( ! isOk( encriptado ) ) return null;
      
      int n = Encrypter.ALFABETO.length();
      StringBuffer b = new StringBuffer(""); 
      for (int i = 0; i < encriptado.length(); i++) 
      {
          char actual = encriptado.charAt(i);
          int iactual = Encrypter.ALFABETO.indexOf( actual );
          
          int idecripto = ( iactual - k + n ) % n;
          b.append( Encrypter.ALFABETO.charAt( idecripto ) );
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
       return super.toString() + "\nT�cnica: Cifrado de C�sar - Factor de desplazamiento: " + k;
   }
}
