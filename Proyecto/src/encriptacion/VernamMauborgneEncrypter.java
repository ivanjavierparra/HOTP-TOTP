package encriptacion;

/**
 * Encripta una cadena en base a la t�cnica de Vernam - Mauborgne. El alfabeto base de los mensajes 
 * a encriptar viene dado por la constante Encriptador.ALFABETO. La tabla de transposici�n es creada 
 * y almacenada internamente por la misma clase. La clave de desplazamiento es creada por la propia 
 * clase, del mismo largo que el mensaje a encriptar (de acuerdo a la idea de Vernam), y cada uno de
 * los caracteres de esa clave se elige en forma aleatoria desde los s�mbolos de alfabeto base (de 
 * acuerdo a la idea de Mauborgne).
 * Patr�n aplicado: Strategy.
 * 
 * @author Iván Javier Parra
 */
public class VernamMauborgneEncrypter extends VigenereEncrypter
{
       /**
        *  Inicia un encriptador con t�cnica de Vernam-Mauborgne. El mensaje a encriptar ser� inicializado 
        *  como la cadena vac�a ("") y el programador deber� cambiar luego ese valor mediante setOpenMessage(). 
        */
       public VernamMauborgneEncrypter( )
       {
           this(null);
       }
                    
       /**
        *  Inicia un encriptador con t�cnica de Vernam-Mauborgne. El mensaje a encriptar se inicializa 
        *  con el valor del par�metro mens. Si ese par�metro es null, el mensaje a encriptar se inicia como 
        *  la cadena vac�a ("") y luego el programador deber� cambiar ese valor con setOpenMessage().
        *  @param mens el mensaje abierto que ser� encriptado.
        */
       public VernamMauborgneEncrypter( String mens )
       {
           super(mens);
           clave = null;
           if ( mensaje.equals("") ) return;
           createKey();
       }
       
    /**
     *  Cambia el mensaje a encriptar. Si el nuevo mensaje es null, el mensaje a encriptar se inicia como cadena 
     *  vac�a (""), lo que deber�a provocar que una invocaci�n a code() retorne null. En todos los casos, tambi�n 
     *  se inicia como cadena vac�a el mensaje encriptado, lo que deber�a provocar que una nueva invocaci�n a decode() 
     *  retorne null. La clave de desplazamiento a usar, se vuelve a crear en forma aleatoria, y se ajusta al tama�o
     *  del nuevo mensaje.
     *  @param mens el nuevo mensaje a encriptar.
     */
    public void setOpenMessage( String mens )
    {
        super.setOpenMessage( mens );
        createKey();
    }
       
       /**
        * Retorna una cadena con informaci�n general sobre el encriptador.
        * @return un cadena con informaci�n del encriptador.
        */
       public String toString()
       {
           String res = "Ultimo mensaje abierto: " + mensaje + "  -  Ultima encriptaci�n conocida: " + encriptado;
           return res + "\nT�cnica: Cifrado de Vernam - Mauborgne" + "\nTabla usada: " + transposicion + "\nClave: " + clave;
       }

       // crea una clave del mismo largo que el mensaje, combinando n s�mbolos aleatoriamente 
       // elegidos del alfabeto base.
       private void createKey ()
       {
           int n = mensaje.length();
           int na = ALFABETO.length();
           StringBuffer key = new StringBuffer(n);
           for( int i = 0; i < n; i++ )
           {
                int ind = (int) ( Math.random() * 1000 ) % na;
                key.append( ALFABETO.charAt(ind) );
           }
           clave = key.toString();
       }    
}
