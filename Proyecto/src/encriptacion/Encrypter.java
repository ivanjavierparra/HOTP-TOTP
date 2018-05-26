package encriptacion;


/**
 * Define el comportamiento que es exigible para una clase que realice trabajos de 
 * encriptaci�n en base a m�todos tradicionales. Se supone un "alfabeto" compuesto 
 * de ciertos caracteres, y ese alfabeto est� almacenado en la constante ALFABETO
 * provista por esta clase.
 * 
 * Patr�n aplicado: Strategy.
 * 
 * @author Iván
 * 
 */
public abstract class Encrypter
{
    /**
     *  El alfabeto base aceptado.
     */
    public static final String ALFABETO = " ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz.,;:?!0123456789";
    
    protected String mensaje;     // el mensaje abierto (mensaje a encriptar)
    protected String encriptado;  // el mensaje encriptado
    
    /**
     *  Inicializa el encriptador con un mensaje abierto tomado como par�metro. Si el par�metro es null, el 
     *  mensaje a encriptar se inicializa como una cadena vac�a (""), lo cual deber�a provocar que el m�todo 
     *  code() retorne null. El constructor tambi�n inicia como cadena vac�a el mensaje encriptado (que se 
     *  obtiene con getEncrypted()) y esto tambi�n deber�a provocar que el m�todo decode() retorne null.
     */
    public Encrypter ( String mens )
    {
        if ( mens == null ) mens = "";
        mensaje = mens;
        encriptado = "";
    }   
    
    /**
     *  Retorna el �ltimo mensaje abierto que deber�a ser usado la pr�xima vez que se invoque a code().
     *  @return el mensaje abierto.
     */
    public String getOpenMessage()
    {
        return mensaje;
    }
    
    /**
     *  Cambia el mensaje a encriptar. Si el nuevo mensaje es null, el mensaje a encriptar se inicia como cadena 
     *  vacía (""), lo que debería provocar que una invocación a code() retorne null. En todos los casos, también 
     *  se inicia como cadena vacía el mensaje encriptado, lo que debería provocar que una nueva invocación a decode() 
     *  retorne null.
     *  @param mens el nuevo mensaje a encriptar.
     */
    public void setOpenMessage( String mens )
    {
        if ( mens == null ) mens = "";
        mensaje = mens;
        encriptado = "";
    }
    
    /**
     * Retorna el último mensaje encriptado. Si el valor retornado es null, es que la última invocación a code() 
     * no pudo completar el proceso (posiblemente por caracteres extraños en el mensaje abierto). Si el valor retornado
     * es la cadena vacía (""), es que nunca se invocó code() o bien es que el mensaje abierto fue cambiado (con 
     * setOpenMessage()) desde la última vez que se invocó a code().
     * @return el último mensaje encriptado.
     */
    public String getEncrypted()
    {
        return encriptado;
    }
    
    /**
     *  Retorna una cadena con el último mensaje abierto trabajado por el encriptador y la �ltima encriptaci�n
     *  conocida para ese mensaje (que puede ser null o la cadena vac�a si el proceso de encriptaci�n fall� o 
     *  nunca se activ�).
     *  @return una cadena con el �ltimo mensaje abierto y la �ltima encriptaci�n conocida para �l.
     */
    public String toString()
    {
        return "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptación conocida: " + encriptado;
    }
    
    /**
     *  Encripta un mensaje formado por los s�mbolos del alfabeto base, siguiendo alguna t�cnica
     *  de encriptaci�n que ser� provista por las clases derivadas. El m�todo retorna una cadena con el mensaje
     *  encriptado (o null si el proceso no pudo realizarse por haber caracteres extra�os en el mensaje abierto),
     *  pero de todos modos el mensaje encriptado queda almacenado en la clase y puede recuperarse directamente 
     *  usando getEncrypted().
     *  @return una cadena con el mensaje encriptado, o null si el mensaje abierto no pudo encriptarse (debido 
     *          a que alg�n caracter de ese mensaje original no figuraba en el alfabeto base aceptado).
     */
    public abstract String code ( );
    
    /**
     *  Desencripta un mensaje, siguiendo alguna t�cnica de encriptaci�n que ser� provista por las 
     *  clases derivadas. El mensaje encriptado se supone creado con la misma t�cnica que ser� usada 
     *  para desencriptar, y el mensaje obtenido contendr� exclusivamente caracteres del alfabeto base aceptado.
     *  El m�todo retorna una cadena con el mensaje abierto (o null si el proceso no pudo realizarse por haber 
     *  caracteres extra�os en el mensaje encriptado), pero de todos modos el mensaje abierto queda almacenado en 
     *  la clase y puede recuperarse directamente usando getOpenMessage().
     *  @return una cadena con el mensaje desencriptado, o null el proceso no pudo hacerse (debido a que alg�n 
     *          caracter del mensaje encriptado no era v�lido).
     */
    public abstract String decode ( );
    
    /**
     * Comprueba si la cadena tomada como par�metro est� formada exclusivamente por caracteres del alfabeto base 
     * aceptado por el encriptador.
     * @param cad la cadena a comprobar
     * @return true si todos los caracteres son v�lidos, o false en caso contrario (o si cad es null).  
     */
    protected boolean isOk ( String cad )
    {
      if (cad == null) return false;
      
      for (int i = 0; i < cad.length(); i++) 
      {
          if ( Encrypter.ALFABETO.indexOf( cad.charAt(i) ) == -1 ) return false;
      }    
      return true;
    }
}
