package modelo;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Clase que permite conectarse y desconectarse de una BD.
 * @author Iván Javier Parra
 */

public class ManagerDB {
    
    private Connection conexion;
    private String mensaje_error;
    
    private static final String URL = "jdbc:postgresql://localhost:5432/logina2f";
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "postgres";
    
    /**
     * Permite conectarse a una BD PostgreSQL.
     * @return un String que contiene el resultado de la operación.
     */
    public String establecerConexion(){
        if (conexion != null)return "";
        mensaje_error = "";
        try{
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL,USUARIO,PASSWORD);
            if (conexion !=null)System.out.println("Conexión a base de datos ... Ok");
            else System.out.println("Conexión a base de datos ... Error");
           
        }catch(Exception e){
            mensaje_error = e.getMessage();
            System.out.println("Problema al establecer la Conexión a la base de datos.");
        }
        return mensaje_error;
    }
    
    
    /* Cierra la conexión a una BD. */
    public void cerrarConexion()
      {
          try
          {
             conexion.close();
             System.out.println( "Conexión a la base de datos cerrada correctamente ... " ) ;  
          }
          catch( Exception e )
          {
             System.out.println( "Error al cerrar la conexion: "  + e.getMessage() ) ;  
          }
      }

    
    public Connection getConexion() {
        return conexion;
    }

    
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    
    public String getMensaje_error() {
        return mensaje_error;
    }

    
    public void setMensaje_error(String mensaje_error) {
        this.mensaje_error = mensaje_error;
    }
    
    
    /* Prueba los métodos de la clase ManagerDB */
    public static void main(String [] args) {
        ManagerDB mdb = new ManagerDB();
        String mje = mdb.establecerConexion();
        System.out.println(mje);
        mdb.cerrarConexion();
    }
    
}
