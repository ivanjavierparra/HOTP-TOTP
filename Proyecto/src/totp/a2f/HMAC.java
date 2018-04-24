/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totp.a2f;

import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ivanj
 */
public class HMAC {
    
    private Mac mac;
    private SecretKeySpec spec;
    private String algoritmo; //HmacSHA1, HmacSHA256, HmacSHA512
    
    
    
    /**
         * This method uses the JCE to provide the crypto algorithm.
         * HMAC computes a Hashed Message Authentication Code with the
         * crypto hash algorithm that is in the variable "algoritmo".
         * @param key: the bytes to use for the HMAC key.
         * @param text: the message or text to be authenticated.
         * @return the Hmac as byte[].
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
