package a2f;

import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Iván Javier Parra
 */
public class HMAC {
    
    private Mac mac;
    private SecretKeySpec spec;
    private String algoritmo; //HmacSHA1, HmacSHA256, HmacSHA512
    
    
    
    /**
         * HMAC computa una MAC con la función hash
         * que está en el atributo "algoritmo".
         * @param key: representa la clave secreta.
         * @param text: representa el mensaje a ser autenticado.
         * @return el HMAC como arreglo de bytes.
    */
    public byte[] getShaHash(byte[] key, byte[] text) {
            try {
                    mac = Mac.getInstance(getAlgoritmo());
                    spec = new SecretKeySpec(key, "RAW");
                    mac.init(spec);
                    return mac.doFinal(text);
            } catch (GeneralSecurityException e) {
                    throw new IllegalStateException(e);
            }
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }
    
            
}
