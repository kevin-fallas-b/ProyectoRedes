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
    
    public Datos(Integer numFragmento, byte[] imagen){
        this.numFragmento = numFragmento;
        this.imagen = imagen;
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
