/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

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
    
    public CapaTransporte(String TipoTransporte, ArrayList<Datos> ListaDatos, int kb){
        this.kb = kb;
        this.ListaDatos = ListaDatos;
        this.TipoTransporte = TipoTransporte;
        
        if(TipoTransporte == "TCP"){
            CrearSegmentos();
        }else if(TipoTransporte == "UDP"){
            CrearDatagramas();
        }
    }
    
    private void CrearSegmentos(){
        ListaSegmentos = new ArrayList<>();
        
        //
        //UN CLICLO POR DATO EN LA LISTA
        for(int i=0; i<ListaDatos.size();i++){
            Datos dato = ListaDatos.get(i);
            byte[] bytes;
            
            //
            //CODIGO PARA CONVERTIR EL OBJETO DATOS A UN ARRAY DE BYTES
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(bos);   
                out.writeObject(dato);
                out.flush();
                bytes = bos.toByteArray();
                bos.close();
            }catch (IOException ex) {
                bytes = new byte[0];
            }
            //FIN DE CONVERSION
            //
            
            int tamano = kb*1024;
            int tamanoActual = 0;
            Boolean bandera = true;
            if(bytes.length>0){
                while(bandera){
                    int numReconstruccion = 0;
                    byte[] aux = new byte[tamano];
                    for(int j=0; j<tamano||tamanoActual<bytes.length; j++){
                        aux[j] = bytes[tamanoActual];
                        tamanoActual++;
                    }
                    Segmento segmentoAux = new Segmento(aux, numReconstruccion, i);
                    ListaSegmentos.add(segmentoAux);
                    if(tamanoActual==bytes.length){
                        bandera = false;
                    }
                }
            }
        }
        //FIN DE CICLO POR DATO
        //
    }
    
    private void CrearDatagramas(){
        
    }
}
