/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;

/**
 *
 * @author ivancho
 */
public class ManagerUsuarioDB {
    private Statement st;
    private String mensaje_error;
    
   
    
    
    public boolean insertarRegistro(String nombre,String email,String password,String clavesecreta){
        
        
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        if(mensaje_error.compareToIgnoreCase("")!=0) return false;
        
        //recibo una password y la encripto usando el algoritmo de Cesar
        //Encrypter enc = new CesarEncrypter(password);
        //password = enc.code();
        //System.out.println("Password encriptado: " + password);
        
        //https://es.stackoverflow.com/questions/54098/que-algoritmo-de-cifrado-se-puede-usar-para-guardar-datos-en-java
        
        String s = "insert into usuario (nombre, email, password, clavesecreta, a2f) ";
        s = s + " values ('"+nombre+"','"+email+"','"+password+"','"+clavesecreta+"','false')";

        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );

        }catch( SQLException e ){
            System.out.println("Error al intentar agregar un Usuario a la Base: " + e.getMessage());  
            mensaje_error = "Error al intentar agregar un Usuario en la BD.";
            return false;
        }

        mdb.cerrarConexion();
        
        

        return true;
        
    }
    
    
    public Usuario consultarRegistro(String email,String password){
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
               usuario.setClave_secreta(res.getString("clavesecreta"));
               usuario.setA2f_activado(res.getBoolean("a2f"));
               System.out.println("Existe");
               
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
            System.out.println("Error al intentar agregar un Usuario a la Base: " + e.getMessage());  
            mensaje_error = "Error al intentar agregar un Usuario en la BD.";
            exito = false;
        }

        mdb.cerrarConexion();
        
        return exito;
    }
    
    
    
    
    
    
    
    

    public String getMensaje_error() {
        return mensaje_error;
    }

    public void setMensaje_error(String mensaje_error) {
        this.mensaje_error = mensaje_error;
    }
    
    
    
    
    public static void main(String[] args) {
        ManagerUsuarioDB mdb_usuario = new ManagerUsuarioDB();
        mdb_usuario.insertarRegistro("pappo","pappo@mail.com","1234","111111");
    }
    
}
