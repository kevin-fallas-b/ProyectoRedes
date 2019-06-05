/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Kevin F
 */
public class Paquete implements Serializable{
    private InetAddress ipOrigen;
    private InetAddress ipDestino;
    private Segmento datos;
    
    public Paquete(InetAddress ipOrigen,InetAddress ipDestino, Segmento datos){
        this.ipOrigen = ipOrigen;
        this.ipDestino = ipDestino;
        this.datos = datos;
    }

    public InetAddress getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(InetAddress ipOrigen) {
        this.ipOrigen = ipOrigen;
    }

    public InetAddress getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(InetAddress ipDestino) {
        this.ipDestino = ipDestino;
    }

    public Segmento getDatos() {
        return datos;
    }

    public void setDatos(Segmento datos) {
        this.datos = datos;
    }
    
}
