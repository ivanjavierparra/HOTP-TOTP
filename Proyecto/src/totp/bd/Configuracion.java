/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totp.bd;

/**
 * Esta clase contiene los parámetros de configuración de HOTP y TOTP.
 * 
 * @author ivancho
 */
public class Configuracion {
    
    private String email;
    private String algoritmo;
    private String tipo;
    private Integer digitos;
    private Integer contador_hotp;
    private Integer tiempo_totp;
    
    public Configuracion(){
        this.email = "noexisto"; // "noexisto" lo uso para validar en DialogIniciarSesion.
        this.algoritmo = "HmacSHA1"; //The crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512).
        this.tipo = "TOTP";
        this.digitos = 6;
        this.contador_hotp = 0;
        this.tiempo_totp = 30; // 30 segundos.
    }
    
    public Configuracion(String email, String algoritmo, String tipo, int digitos, int contador_hotp, int tiempo_totp){
        this.email = email;
        this.algoritmo = algoritmo; //The crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512).
        this.tipo = tipo;
        this.digitos = digitos;
        this.contador_hotp = contador_hotp;
        this.tiempo_totp = tiempo_totp; //  segundos.
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDigitos() {
        return digitos;
    }

    public void setDigitos(Integer digitos) {
        this.digitos = digitos;
    }

    public Integer getContador_hotp() {
        return contador_hotp;
    }

    public void setContador_hotp(Integer contador_hotp) {
        this.contador_hotp = contador_hotp;
    }

    public Integer getTiempo_totp() {
        return tiempo_totp;
    }

    public void setTiempo_totp(Integer tiempo_totp) {
        this.tiempo_totp = tiempo_totp;
    }
    
    
}
