/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author Kevin F
 */
public class CapaTransporte {
    private String TipoTransporte;
    private List<Datos> ListaDatos;
    private List<Segmento> ListaSegmentos;
    private List<Datagrama> ListaDatagramas;
    private int kb;
    
    public CapaTransporte(String TipoTransporte, List<Datos> ListaDatos, int kb){
        this.kb = kb;
        this.ListaDatos = ListaDatos;
        this.TipoTransporte = TipoTransporte;
        
        if(TipoTransporte == "TCP"){
            CrearSegmentos();
        }else if(TipoTransporte == "UDP"){
            CrearDatagramas();
        }
    }
    
    public CapaTransporte(List<Segmento> ListaSegmentos, List<Datagrama> ListaDatagramas){
        if(ListaSegmentos != null){
           this.ListaSegmentos = ListaSegmentos;
            ReconstruirSegmentos();
        }else if(ListaDatagramas != null){
            this.ListaDatagramas = ListaDatagramas;
            ReconstruirDatagramas();
        }
    }
    
    private void ReconstruirSegmentos(){
        ListaDatos = new ArrayList();
        for(int i=0; i<ListaSegmentos.size(); i++){
            ListaDatos.add((Datos)SerializationUtils.deserialize(ListaSegmentos.get(i).getDatos()));
        }
        /*
        boolean bandera = true;
        int numDato = 0;
        
        while(bandera){
            byte[] bytes = new byte[0];
            int numReconstrucion = 0;
            //
            //CONCATENA TODOS LOS DATOS DIVIDIDOS EN BYTES
            for(int i=0; i<ListaSegmentos.size();i++){
                if(ListaSegmentos.get(i).getNumSegmento()==numDato){
                    if(ListaSegmentos.get(i).getNumReconstruccion()==numReconstrucion){
                        bytes = Concatenar(bytes, ListaSegmentos.get(i).getDatos());
                        ListaSegmentos.remove(i);
                        numReconstrucion++;
                    }
                }
            }
            //TERMINA DE CONCATENAR EN BYTES
            //
            numDato++;
            //System.out.println(Arrays.toString(bytes));
            Datos datoaux = (Datos)SerializationUtils.deserialize(bytes);
        }*/
    }
    
    private byte[] Concatenar(byte[] primero, byte[] segundo){
        byte[] concatenados = new byte[primero.length+segundo.length];
        for(int i=0; i<primero.length;i++){
            concatenados[i]=primero[i];
        }
        
        for(int i=0; i<segundo.length; i++){
            concatenados[i+primero.length] = segundo[i];
        }
        
        return concatenados;
    }
    
    private void ReconstruirDatagramas(){
    
    }
     
    private void CrearSegmentos(){
        ListaSegmentos = new ArrayList<>();
        
        for(int i=0; i<ListaDatos.size(); i++){
            ListaSegmentos.add(new Segmento(SerializationUtils.serialize(ListaDatos.get(i)), 0, i));
            System.out.println(ListaDatos.get(i).getImagen().length);
        }
        /*
        //
        //UN CLICLO POR DATO EN LA LISTA
        for(int i=0; i<ListaDatos.size();i++){
            Datos dato = ListaDatos.get(i);
            byte[] bytes;
            
            bytes = SerializationUtils.serialize(dato);
            //System.out.println(Arrays.toString(bytes));
            
            int tamano = kb*1024;
            int tamanoActual = 0;
            Boolean bandera = true;
            if(bytes.length>0){
                //System.out.println(bytes.length);
                int numReconstruccion = 0;
                while(bandera){
                    byte[] aux = new byte[tamano];
                    for(int j=0; j<tamano&&tamanoActual<bytes.length; j++){
                        aux[j] = bytes[tamanoActual];
                        System.out.println(bytes[tamanoActual]);
                        tamanoActual++;
                    }
                    Segmento segmentoAux = new Segmento(aux, numReconstruccion, i);
                    numReconstruccion++;
                    ListaSegmentos.add(segmentoAux);
                    if(tamanoActual==bytes.length){
                        bandera = false;
                    }
                }
            }
        }
        //FIN DE CICLO POR DATO
        //*/
    }
    
    private void CrearDatagramas(){
        
    }

    public String getTipoTransporte() {
        return TipoTransporte;
    }

    public void setTipoTransporte(String TipoTransporte) {
        this.TipoTransporte = TipoTransporte;
    }

    public List<Datos> getListaDatos() {
        return ListaDatos;
    }

    public void setListaDatos(List<Datos> ListaDatos) {
        this.ListaDatos = ListaDatos;
    }

    public List<Segmento> getListaSegmentos() {
        return ListaSegmentos;
    }

    public void setListaSegmentos(List<Segmento> ListaSegmentos) {
        this.ListaSegmentos = ListaSegmentos;
    }

    public List<Datagrama> getListaDatagramas() {
        return ListaDatagramas;
    }

    public void setListaDatagramas(List<Datagrama> ListaDatagramas) {
        this.ListaDatagramas = ListaDatagramas;
    }

    public int getKb() {
        return kb;
    }

    public void setKb(int kb) {
        this.kb = kb;
    }
    
    
}
