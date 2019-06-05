/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.io.Serializable;

/**
 *
 * @author Kevin F
 */
public class Datos implements Serializable{
    private Integer numFragmento;
    private byte[] imagen;
    private Integer cantMaxFilas;
    private Integer cantMaxColumnas;
    
    public Datos(Integer numFragmento, byte[] imagen, Integer cantMaxFilas, Integer cantMaxColumnas){
        this.numFragmento = numFragmento;
        this.imagen = imagen;
        this.cantMaxFilas = cantMaxFilas;
        this.cantMaxColumnas = cantMaxColumnas;
    }

    public Integer getNumFragmento() {
        return numFragmento;
    }

    public void setNumFragmento(Integer numFragmento) {
        this.numFragmento = numFragmento;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getCantMaxFilas() {
        return cantMaxFilas;
    }

    public void setCantMaxFilas(Integer cantMaxFilas) {
        this.cantMaxFilas = cantMaxFilas;
    }

    public Integer getCantMaxColumnas() {
        return cantMaxColumnas;
    }

    public void setCantMaxColumnas(Integer cantMaxColumnas) {
        this.cantMaxColumnas = cantMaxColumnas;
    }       
    
}
