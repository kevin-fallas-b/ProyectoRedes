/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import static java.util.Collections.list;
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
    
    public CapaTransporte(String TipoTransporte, ArrayList<Datos> ListaDatos) {
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
    }
    
    private void CrearDatagramas(){
        
    }
}
