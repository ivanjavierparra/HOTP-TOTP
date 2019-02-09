package modelo;

/**
 * Clase que representa un Usuario de una aplicación.
 * 
 * @author Iván Javier Parra
 */

public class Usuario {
    
    private String nombre;
    private String email;
    private String password;
    private String clave_secreta;
    private boolean a2f_activado;
    private boolean cuenta_activada;

    
    public Usuario(String nombre, String email, String password, String clave_secreta) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.clave_secreta = clave_secreta;
        this.a2f_activado = false;
        this.cuenta_activada = true;
    }
    
   
    public Usuario(){
        this.nombre = "";
        this.email = "";
        this.password = "";
        this.clave_secreta = "";
        this.a2f_activado = false;
        this.cuenta_activada = true;
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getClave_secreta() {
        return clave_secreta;
    }

    
    public void setClave_secreta(String clave_secreta) {
        this.clave_secreta = clave_secreta;
    }

    
    public boolean isA2f_activado() {
        return a2f_activado;
    }

    
    public void setA2f_activado(boolean a2f_activado) {
        this.a2f_activado = a2f_activado;
    }

    
    public boolean isCuenta_activada() {
        return cuenta_activada;
    }

    
    public void setCuenta_activada(boolean cuenta_activada) {
        this.cuenta_activada = cuenta_activada;
    }
    
    
    
}
