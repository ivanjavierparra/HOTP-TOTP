package totp.principal;

import totp.bd.Usuario;
import totp.gui.PantallaPrincipal;

/**
 *
 * @author ivancho
 */
public class Main {
    
    public static Usuario usuario;
    public static PantallaPrincipal pantallaPrincipal;
    
    public static void main(String[] args) {
        usuario = new Usuario();
        pantallaPrincipal = new PantallaPrincipal();
        pantallaPrincipal.setVisible(true);
    }
}
