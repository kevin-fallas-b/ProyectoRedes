/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author Kevin F
 */
public class CapaEnlaceDatos {

    private List<Paquete> listaPaquetes  = new ArrayList();
    private List<Trama> listaTramas  = new ArrayList();
    private List<byte[]> tramasEnBytes = new ArrayList();
    private Integer cantErrores;

    public CapaEnlaceDatos(List<Trama> tramasRecibidas, String hola){
        this.listaTramas = tramasRecibidas;
        pasarTramasAPaquetes();
    }
    public CapaEnlaceDatos(List<Paquete> paquetes,Integer cantErrores) {
        this.listaPaquetes = paquetes;
        this.cantErrores = cantErrores;
        pasarPaquetesATramas();
    }
    
    public CapaEnlaceDatos(List<byte[]> tramasEnBytes, Integer ax,String hola){
        this.tramasEnBytes = tramasEnBytes;
        desSerializarTramas();
    }
    
    public List<Paquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(List<Paquete> paquetesRecibidos) {
        this.listaPaquetes = paquetesRecibidos;
    }

    public List<Trama> getListaTramas() {
        return listaTramas;
    }

    public void setTramasListasParaEnvio(List<Trama> tramasListasParaEnvio) {
        this.listaTramas = tramasListasParaEnvio;
    }

    private void pasarPaquetesATramas() {
        Random rand = new Random();
        Integer errores[] = new Integer[cantErrores];
        for(int i=0;i<cantErrores;i++){
            errores[i]=rand.nextInt(listaPaquetes.size());
        }
        for (int i = 0; i < listaPaquetes.size(); i++) {
            Trama trama = new Trama(i, listaPaquetes.get(i), false,0,true);
            for(int k=0; k<cantErrores;k++){
                if(errores[k]==i){
                    trama.setError(true);
                }
            }
            listaTramas.add(trama);
        }
        listaTramas.get(listaTramas.size()-1).setUltimo(1);
        serializarTramas();
    }

    private void serializarTramas() {
        for (int i = 0; i < listaTramas.size(); i++) {
            //serializar cada trama y agregarla a la list de tramas serializadas 
            byte[] datos = SerializationUtils.serialize(listaTramas.get(i));
            tramasEnBytes.add(datos);
        }
    }
    
    private void desSerializarTramas(){
        for(int i=0;i<tramasEnBytes.size();i++){
            Trama trama = SerializationUtils.deserialize(tramasEnBytes.get(i));
            listaTramas.add(trama);
        }
        pasarTramasAPaquetes();
    }
    
    private void pasarTramasAPaquetes(){
        for(int i=0;i<listaTramas.size();i++){
            Paquete paquete = listaTramas.get(i).getPaquete();
            listaPaquetes.add(paquete);
        }
    }
    
    public List<byte[]> getTramasEnBytes(){
        return tramasEnBytes;
    }
}
