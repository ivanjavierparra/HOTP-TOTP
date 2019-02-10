package modelo;

import encriptacion.CesarEncrypter;
import encriptacion.Cifrador;
import encriptacion.Encrypter;
import java.sql.*;


/**
 * Esta clase permite manejar objetos Usuario sobre una BD.
 * @author Iván Javier Parra
 */

public class ManagerUsuarioDB {
   
    private Statement st;
    private String mensaje_error;
    
    // Clave Secreta que sirve para encriptar/desencriptar usando AES.
    public static final String CLAVE_SIMETRICA = "FooBar1234567890"; // 128 bits
    public static final byte[] IV = new byte[16];
    // Nota: Las claves de cifrado AES podrían ser de 128, 192 y 256 bits.
    
   
    /**
     * Almacena un objeto Usuario en la BD.
     * https://es.stackoverflow.com/questions/54098/que-algoritmo-de-cifrado-se-puede-usar-para-guardar-datos-en-java
     * @param nombre
     * @param email
     * @param password
     * @param clavesecreta
     * @return True si la transacción fue exitosa, False sino.
     */
    public boolean insertarRegistro(String nombre,String email,String password,String clavesecreta){
        
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        if(mensaje_error.compareToIgnoreCase("")!=0) return false;
        
        String clave_secreta_encriptada = encriptar_clave_secreta(clavesecreta);
        //String clave_secreta_encriptada = encriptar_AES(clavesecreta);
        
        String s = "insert into usuario (nombre, email, password, clavesecreta, a2f, activada) ";
        s = s + " values ('"+nombre+"','"+email+"','"+password+"','"+clave_secreta_encriptada+"','false','true')";

        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );

        }catch( SQLException e ){
            System.out.println("Error al intentar agregar un Usuario a la BD: " + e.getMessage());  
            mensaje_error = "Error al intentar agregar un Usuario en la BD.";
            return false;
        }

        mdb.cerrarConexion();
        
        return true;
        
    }
    
    
    /**
     * Busca en la tabla "usuario" el usuario con email
     * pasado como parámetro.
     * @param email
     * @param password
     * @return 
     */
    public Usuario consultarRegistro_NO_SEGURO(String email,String password){
      
        Usuario usuario = new Usuario();
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        
        //Validar conexion y consulta a la bd
        
        ResultSet res = null;
        String s = "select * from usuario where email='"+email+"' and password='"+password+"'"; 
        
        try{
            st = mdb.getConexion().createStatement();
            st.execute( s );
            res = st.getResultSet();
            if( res.next() )
            {
               //existe = true;
               //res.getString("ColumnaX");
               usuario.setNombre(res.getString("nombre"));
               usuario.setEmail(email);
               usuario.setPassword(password);
               
               String clave_secreta_desencriptada = desencriptar_clave_secreta(res.getString("clavesecreta"));
               
               usuario.setClave_secreta(clave_secreta_desencriptada);
               usuario.setA2f_activado(res.getBoolean("a2f"));
               usuario.setCuenta_activada(res.getBoolean("activada"));
               System.out.println("Existe el usuario " + email + " en la BD.");
               
               /*
                    //Para recorrer todo hacer:
                       while (res.next()){
                            nombre = res.getString("nombre");
                       }
               */

            }
        }catch( SQLException e ){
            System.out.println("Error al buscar un Usuario en la Base: " + e.getMessage()); 
        }
        
        mdb.cerrarConexion();
        return usuario;
        
    }
    
    
    /**
     * Hace lo mismo que consultarRegistro_NO_SEGURO(), solamente que este método
     * evita sql injection.
     * @param email
     * @param password
     * @return 
     */
    public Usuario consultarRegistro(String email,String password){
      
        Usuario usuario = new Usuario();
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        
        try{
            PreparedStatement stmt = mdb.getConexion().prepareStatement("SELECT * FROM usuario WHERE email=? AND password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet res = stmt.executeQuery();
           
            if( res.next() )
            {
               usuario.setNombre(res.getString("nombre"));
               usuario.setEmail(email);
               usuario.setPassword(password);
               
               String clave_secreta_desencriptada = desencriptar_clave_secreta(res.getString("clavesecreta"));
               //String clave_secreta_desencriptada = desencriptar_AES(res.getString("clavesecreta"));
               
               usuario.setClave_secreta(clave_secreta_desencriptada);
               usuario.setA2f_activado(res.getBoolean("a2f"));
               usuario.setCuenta_activada(res.getBoolean("activada"));
               System.out.println("Existe el usuario " + email + " en la BD.");

            }
        }catch( SQLException e ){
            System.out.println("Error al buscar un Usuario en la Base: " + e.getMessage()); 
        }
        
        mdb.cerrarConexion();
        return usuario;
    }
    
      
    /**
     * Actualiza el campo a2f del registro con email especifícado 
     * por parámetro en la tabla "usuario".
     * @param email
     * @param activar
     * @return True si se pudo ejecutar la transacción, False sino.
     */
    public boolean actualizarA2F(String email, boolean activar){
        boolean exito = true;
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        
        String s;
        if(activar)s = "UPDATE usuario set a2f='true' where email = '"+email+"' "; 
        else s = "UPDATE usuario set a2f='false' where email = '"+email+"' "; 
        
        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );

        }catch( SQLException e ){
            System.out.println("Error al intentar actualizar estado A2F: " + e.getMessage());  
            mensaje_error = "Error al intentar actualizar estado A2F.";
            exito = false;
        }

        mdb.cerrarConexion();
        
        return exito;
    }
    
    
    /**
     * Permite activar o desactivar la cuenta asociada al usuario con email pasado
     * como parámetro.
     * @param email
     * @param activar
     * @return 
     */
    public boolean actualizar_activacion(String email, boolean activar){
        boolean exito = true;
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        
        String s;
        if(activar)s = "UPDATE usuario set activada='true' where email = '"+email+"' "; 
        else s = "UPDATE usuario set activada='false' where email = '"+email+"' "; 
        
        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );

        }catch( SQLException e ){
            System.out.println("Error al intentar cambiar el estado de la cuenta del usuario: " + e.getMessage());  
            mensaje_error = "Error al intentar cambiar estado de la cuenta en BD.";
            exito = false;
        }

        mdb.cerrarConexion();
        
        return exito;
    }
    
    
    /**
     * Permite encriptar un mensaje a través del cifrado clásico (Hoy en día en desuso). 
     * @param clave_secreta
     * @return 
     */
    private String encriptar_clave_secreta(String clave_secreta){
        System.out.println("Clave sin encriptar: " + clave_secreta);  
        Encrypter enc = new CesarEncrypter(clave_secreta);
        return enc.code();
    }
    
    
    /**
     * Permite desencriptar un mensaje a través del cifrado clásico (Hoy en día en desuso). 
     * @param clave_secreta_encriptada
     * @return 
     */
    private String desencriptar_clave_secreta(String clave_secreta_encriptada){
        System.out.println("Clave encriptada: " + clave_secreta_encriptada);  
        Encrypter enc = new CesarEncrypter();
        enc.setEncriptado(clave_secreta_encriptada);
        return enc.decode();
    }
    
    
    /**
     * Permite cifrado simétrico usando AES.
     * @param clave_secreta
     * @return 
     */
    public String encriptar_AES(String clave_secreta){
        System.out.println("Clave sin encriptar: " + clave_secreta);  
        return Cifrador.encriptar(CLAVE_SIMETRICA, IV, clave_secreta);
    }
    
    
    /**
     * Permite descifrado simétrico usando AES.
     * @param clave_encriptada
     * @return 
     */
    public String desencriptar_AES(String clave_encriptada){
        System.out.println("Clave encriptada: " + clave_encriptada);  
        return Cifrador.desencriptar(CLAVE_SIMETRICA, IV, clave_encriptada);
    }
    
    
    public String getMensaje_error() {
        return mensaje_error;
    }

    
    public void setMensaje_error(String mensaje_error) {
        this.mensaje_error = mensaje_error;
    }
    
    
    /* Test de los métodos de la clase ManagerUsuarioDB */
    public static void main(String[] args) {
        ManagerUsuarioDB mdb_usuario = new ManagerUsuarioDB();
        //mdb_usuario.insertarRegistro("pappo","pappo@mail.com","1234","111111");
        
        System.out.println("Clave Secreta: " + "ZJXH6VU572UWZJFU6SIS6AUIKZY6O4ST");
        String mensaje = "ZJXH6VU572UWZJFU6SIS6AUIKZY6O4ST";
        String clave_encriptada = mdb_usuario.encriptar_AES(mensaje);
        System.out.println("Clave Encriptada: " + clave_encriptada);
        String clave_desencriptada = mdb_usuario.desencriptar_AES(clave_encriptada);
        System.out.println("Clave Desencriptada: " + clave_desencriptada);
        System.out.println("--------------------------------------------");
        
        mdb_usuario = new ManagerUsuarioDB();
        System.out.println("Clave Secreta: " + "ZZZZ6VU572UWZJFU6SIS6AUIKZY6OZZZ");
        mensaje = "ZZZZ6VU572UWZJFU6SIS6AUIKZY6OZZZ";
        clave_encriptada = mdb_usuario.encriptar_AES(mensaje);
        System.out.println("Clave Encriptada: " + clave_encriptada);
        clave_desencriptada = mdb_usuario.desencriptar_AES(clave_encriptada);
        System.out.println("Clave Desencriptada: " + clave_desencriptada);
        System.out.println("--------------------------------------------");
        
        ManagerUsuarioDB mdb_usuario_ = new ManagerUsuarioDB();
        System.out.println("Clave Secreta: " + "ZZZZ111572UWZJFU6SIS6AUIKZ111ZZZ");
        mensaje = "ZZZZ111572UWZJFU6SIS6AUIKZ111ZZZ";
        clave_encriptada = mdb_usuario_.encriptar_AES(mensaje);
        System.out.println("Clave Encriptada: " + clave_encriptada);
        clave_desencriptada = mdb_usuario_.desencriptar_AES(clave_encriptada);
        System.out.println("Clave Desencriptada: " + clave_desencriptada);
        System.out.println("--------------------------------------------");
    }
    
}
