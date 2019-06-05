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
public class Segmento implements Serializable{
    private byte []Datos;
    private int numReconstruccion;
    private int numSegmento;
    
    public Segmento(byte []Datos, int numeconstruccion, int numSegmento){
        this.Datos = Datos;
        this.numReconstruccion = numReconstruccion;
        this.numSegmento = numSegmento;
    }

    public byte[] getDatos() {
        return Datos;
    }

    public void setDatos(byte[] Datos) {
        this.Datos = Datos;
    }

    public int getNumReconstruccion() {
        return numReconstruccion;
    }

    public void setNumReconstruccion(int numReconstruccion) {
        this.numReconstruccion = numReconstruccion;
    }

    public int getNumSegmento() {
        return numSegmento;
    }

    public void setNumSegmento(int numSegmento) {
        this.numSegmento = numSegmento;
    }
    
    
}
