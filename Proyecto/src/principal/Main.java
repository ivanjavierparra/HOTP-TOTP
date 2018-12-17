package principal;

import modelo.Usuario;
import vista.PantallaPrincipal;

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
