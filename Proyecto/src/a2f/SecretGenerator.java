package a2f;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.codec.binary.Base32;

/**
 * Clase que genera claves secretas para HOTP y TOTP.
 * 
 * @author Iván Javier Parra
 */


public abstract class SecretGenerator {

	private static final Random RAND = new Random();
	
	public enum Size {
		DEFAULT(20),// Seed for HMAC-SHA1 - 20 bytes
		MEDIUM(32),// Seed for HMAC-SHA256 - 32 bytes
		LARGE(64);// Seed for HMAC-SHA512 - 64 bytes
		
		private int size;
		
		Size(int size) {
			this.size = size;
		}
		
		public int getSize() {
			return size;
		}
	}
	
	
        /**
	 * Genera una clave secreta aleatoria de 20 bytes.
         * 
	 * @return la clave secreta.
	 */
	public static final byte[] generar() {
		return generar(Size.DEFAULT);
	}
	
        
	/**
	 * Genera una clave secreta aleatoria cuyo tamaño es pasado por paràmetro.
         * 
         * @param size
	 * @return la clave secreta.
	 */
	public static final byte[] generar(Size size) {
		byte[] b = new byte[size.getSize()];
		RAND.nextBytes(b);
		return Arrays.copyOf(b, size.getSize());
	}
	
        
        
	/**
	 * Codifica la clave secreta a Base32.
         * 
	 * @param claveSecreta: la clave secreta.
	 * @return la clave secreta codificada en Base32.
	 * @see Base32
	 */
	public static final String toBase32(byte[] claveSecreta) {
		return new String(new Base32().encode(claveSecreta));
	}
	
        
	/**
	 * Decodifica la clave secreta que está en Base32 a bytes.
         * 
	 * @param base32: la clave secreta codificada en Base32. 
	 * @return la clave secreta decodificada.
	 * @see Base32
	 */
	public static final byte[] fromBase32(String base32) {
		return new Base32().decode(base32);
	}
	
        
        /**
         * Codifica la clave secreta que esta en Byte[] a Hexa.
         * @param claveSecreta
         * @return la clave secreta codificada en Hexa.
         */
	public static final String toHex(byte[] claveSecreta) {
		return String.format("%x", new BigInteger(1, claveSecreta));
	}
	
        
        /**
         * Decodifica la clave secreta que está en Hexa a Byte[].
         * @param hex
         * @return 
         */
	public static final byte[] fromHex(String hex) {
            // Adding one byte to get the right conversion
            // Values starting with "0" can be converted
            byte[] bArray = new BigInteger("10" + hex,16).toByteArray();

            // Copy all the REAL bytes, not the "first"
            byte[] ret = new byte[bArray.length - 1];
            for (int i = 0; i < ret.length; i++)
                ret[i] = bArray[i+1];
            return ret;
	}
	
}
