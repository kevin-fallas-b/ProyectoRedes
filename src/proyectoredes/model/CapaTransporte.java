/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Kevin F
 */
public class CapaTransporte {

    List<BufferedImage> listaImagenesRecibida;
    Integer tamano;
    
    public CapaTransporte(List<BufferedImage> listaImagenes, String tipoDeEnvio, Integer Tamano) {
        this.listaImagenesRecibida = listaImagenes;
        if(tipoDeEnvio == "TCP"){
            CrearSegmentos();
        }else{
            CrearDatagramas();
        }
        this.tamano=Tamano;
    }
    
    private void CrearSegmentos(){
        for(int i=0;i<listaImagenesRecibida.size();i++){
            
        }
    }
    
    private void CrearDatagramas(){
        
    }
}
