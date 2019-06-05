/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

/**
 *
 * @author Kevin F
 */
public class Datos {
    private Integer numFragmento;
    private byte[] imagen;
    private Integer cantMaxFilas;
    private Integer cantMaxColumnas;
    private Integer fila;
    private Integer columna;
    
    public Datos(Integer numFragmento, byte[] imagen, Integer fila, Integer columna, Integer cantMaxFilas, Integer cantMaxColumnas){
        this.numFragmento = numFragmento;
        this.imagen = imagen;
        this.fila= fila;
        this.columna = columna;
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
       
    
}
