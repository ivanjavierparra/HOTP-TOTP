/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ivancho
 */
public class ManagerConfiguracionDB {
    private Statement st;
    private String mensaje_error;
    
    
    
    public Configuracion consultarRegistro(String email){
        Configuracion configuracion = new Configuracion();
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        
        //Validar conexion y consulta a la bd
        
        ResultSet res = null;
        String s = "select * from configuraciona2f where email='"+email+"'"; 
        
        try{
            st = mdb.getConexion().createStatement();
            st.execute( s );
            res = st.getResultSet();
            if( res.next() )
            {
               //existe = true;
               //res.getString("ColumnaX");
               configuracion.setEmail(res.getString("email"));
               configuracion.setAlgoritmo(res.getString("algoritmo"));
               configuracion.setTipo(res.getString("tipo"));
               configuracion.setDigitos(res.getInt("digitos"));
               configuracion.setContador_hotp(res.getInt("contadorhotp"));
               configuracion.setTiempo_totp(res.getInt("tiempototp"));
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
        return configuracion;
    }
    
    
    
    
    public boolean actualizarRegistro(String email, int contador){
        boolean exito = true;
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        //if(mensaje_error.compareToIgnoreCase("")!=0) return true; // Esto esta mal, no se pudo conectar a la BD.
        
        String s = "UPDATE configuraciona2f set contadorhotp="+ contador +" where email = '"+email+"' "; 
        
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
    
    
    public boolean insertarRegistro(Configuracion configuracion){
        
        
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        if(mensaje_error.compareToIgnoreCase("")!=0) return false;
        
        String s = "insert into configuraciona2f (email, algoritmo, tipo, digitos, contadorhotp, tiempototp) ";
        s = s + " values ('"+configuracion.getEmail()+"','"+configuracion.getAlgoritmo()+"','"+configuracion.getTipo()+"',"+configuracion.getDigitos()+","+configuracion.getContador_hotp()+","+configuracion.getTiempo_totp()+")";
        
        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );
            System.out.println("Se ha insertado un registro en la BD.");

        }catch( SQLException e ){
            System.out.println("Error al intentar agregar una Configuracion a la Base: " + e.getMessage());  
            mensaje_error = "Error al intentar agregar la configuracion a la BD.";
            return false;
        }
        
        
        mdb.cerrarConexion();
        
        

        return true;
    }
    
    
    
    public boolean eliminarRegistro(String email){
        ManagerDB mdb = new ManagerDB();
        mensaje_error = mdb.establecerConexion();
        if(mensaje_error.compareToIgnoreCase("")!=0) return false;
        
        String s = "DELETE FROM configuraciona2f WHERE email='"+email+"'";
        
        
        try{
            st = (Statement) mdb.getConexion().createStatement();
            st.execute( s );
            System.out.println("Se ha insertado un registro en la BD.");

        }catch( SQLException e ){
            System.out.println("Error al intentar agregar una Configuracion a la Base: " + e.getMessage());  
            mensaje_error = "Error al intentar agregar la configuracion a la BD.";
            return false;
        }
        
        
        mdb.cerrarConexion();
        
        

        return true;
    }
    
    
    
    public static void main(String[] args) {
        ManagerConfiguracionDB mcdb = new ManagerConfiguracionDB();
        //mcdb.actualizarRegistro("kari@mail.com", 3);
        //Configuracion configuracion = new Configuracion("seba@mail.com","HmacSHA1","HOTP",6,1,0);
        //mcdb.insertarRegistro(configuracion);
        //mcdb.eliminarRegistro("seba@mail.com");
    }
    
}