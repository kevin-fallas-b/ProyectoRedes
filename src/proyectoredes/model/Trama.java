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
public class Trama {

    private Integer numTrama;
    private Paquete paquete;
    private Boolean error;

    public Trama(Integer numTrama, Paquete paquete, Boolean error) {
        this.numTrama = numTrama;
        this.paquete = paquete;
        this.error = error;
    }

    public Integer getNumTrama() {
        return numTrama;
    }

    public void setNumTrama(Integer numTrama) {
        this.numTrama = numTrama;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

}
