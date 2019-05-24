/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Kevin F
 */
public class CapaAplicacion {
    //esta clase define como trabaja el protocolo KF
    
    Integer cantFilas;
    Integer cantColumnas;
    List<BufferedImage> listaSegmentos;//esta lista es la imagen original nada mas que ahora divida en muchas imagenes pequenas
    BufferedImage imagenOriginal;
    
    public CapaAplicacion(){
        this.cantFilas=0;
        this.cantColumnas=0;
    }
    
    public CapaAplicacion(Integer cantFil, Integer cantCol, File pathImagen){
        this.cantFilas = cantFil;
        this.cantColumnas = cantCol;
        this.listaSegmentos = new ArrayList<BufferedImage>();
        try {
            this.imagenOriginal = ImageIO.read(pathImagen);
        } catch (IOException ex) {
            Logger.getLogger(CapaAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getCantFilas() {
        return cantFilas;
    }

    public void setCantFilas(Integer cantFilas) {
        this.cantFilas = cantFilas;
    }

    public Integer getCantColumnas() {
        return cantColumnas;
    }

    public void setCantColumnas(Integer cantColumnas) {
        this.cantColumnas = cantColumnas;
    }

    public List<BufferedImage> getListaSegmentos() {
        return listaSegmentos;
    }

    public void setListaSegmentos(List<BufferedImage> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    
    private void segmentarImagen(){
        //calculamos la cantidad de pixeles que hay que avanzar entre cada imagen
        Integer aumentoEnX= (int)imagenOriginal.getWidth()/cantColumnas;
        Integer aumentoEnY= (int)imagenOriginal.getHeight()/cantFilas;
        listaSegmentos.clear();
        for(int i=0;i<cantFilas;i++){
            for(int k=0;k<cantColumnas;k++){
                //segmentar
                BufferedImage imagen = imagenOriginal.getSubimage(0, 0, aumentoEnX, aumentoEnY);
                
                //listaSegmentos.add()
            }
        }
    }
  
}
