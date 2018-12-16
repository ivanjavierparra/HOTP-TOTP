package encriptacion;

import totp.a2f.SecretGenerator;

public class Principal
{
    public static void main(String args[])
    {
        
        // Primer ejemplo, usando cifrado de C�sar
        Encrypter enc = new CesarEncrypter("Ataque inminente");
        enc.code();
        enc.decode();
        System.out.println(enc.toString());
        
        // Segundo ejemplo, usando cifrado por tabla de Transposici�n
        enc = new TranspositionEncrypter("Ataque inminente");
        enc.code();
        enc.decode();
        System.out.println("\n" + enc.toString());
        
        // Tercer ejemplo, usando cifrado de Vigenere
        enc = new VigenereEncrypter("Ataque inminente");
        enc.code();
        enc.decode();
        System.out.println("\n" + enc.toString());

        // Cuarto ejemplo, usando cifrado de Vernam
        enc = new VernamEncrypter("Ataque inminente");
        enc.code();
        enc.decode();
        System.out.println("\n" + enc.toString());
        
        // Quinto ejemplo, usando cifrado de Vernam - Mauborgne
        enc = new VernamMauborgneEncrypter("Ataque inminente");
        enc.code();
        enc.decode();
        System.out.println("\n" + enc.toString());
    }
}
