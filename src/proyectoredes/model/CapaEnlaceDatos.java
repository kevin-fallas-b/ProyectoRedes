/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.util.List;

/**
 *
 * @author Kevin F
 */
public class CapaEnlaceDatos {
    private List<Paquete> paquetesRecibidos;
    private List<Trama> tramasListasParaEnvio;
    private List<byte[]> tramasEnBytes;

    public List<Paquete> getPaquetesRecibidos() {
        return paquetesRecibidos;
    }

    public void setPaquetesRecibidos(List<Paquete> paquetesRecibidos) {
        this.paquetesRecibidos = paquetesRecibidos;
    }

    public List<Trama> getTramasListasParaEnvio() {
        return tramasListasParaEnvio;
    }

    public void setTramasListasParaEnvio(List<Trama> tramasListasParaEnvio) {
        this.tramasListasParaEnvio = tramasListasParaEnvio;
    }
    
    private void pasarPaquetesATramas(){
        for(int i=0;i<paquetesRecibidos.size();i++){
            Trama trama = new Trama(i,paquetesRecibidos.get(i),false);
            tramasListasParaEnvio.add(trama);
        }
    }
    
    private void serializarTramas(){
        for(int i=0;i<tramasListasParaEnvio.size();i++){
            //serializar cada trama y agregarla a la list de tramas serializadas 
        }
    }
}
