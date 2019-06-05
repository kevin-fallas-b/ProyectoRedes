/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Kevin F
 */
public class CapaAplicacion {
    //esta clase define como trabaja el protocolo KFFM

    Integer cantFilas;
    Integer cantColumnas;
    List<BufferedImage> listaSegmentos;//esta lista es la imagen original nada mas que ahora divida en muchas imagenes pequenas
    List<Datos> listaDatos;
    BufferedImage imagenOriginal;

    public CapaAplicacion() {
        this.cantFilas = 0;
        this.cantColumnas = 0;
    }

    public CapaAplicacion(Integer cantFil, Integer cantCol, File pathImagen) {
        this.cantFilas = cantFil;
        this.cantColumnas = cantCol;
        this.listaSegmentos = new ArrayList<BufferedImage>();
        try {
            this.imagenOriginal = ImageIO.read(pathImagen);
        } catch (IOException ex) {
            Logger.getLogger(CapaAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        segmentarImagen();
    }

    public CapaAplicacion(List<Datos> listaDatos) throws IOException {
        this.listaDatos = listaDatos;
        pasarDatosASegmentos();
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

    public void setListaSegmentos(List<BufferedImage> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    public List<BufferedImage> getListaSegmentos() {

        return listaSegmentos;
    }

    public List<Datos> getListaDatos() {
        return listaDatos;
    }

    private void segmentarImagen() {
        //calculamos la cantidad de pixeles que hay que avanzar entre cada imagen
        Integer aumentoEnX = imagenOriginal.getWidth() / cantColumnas;
        Integer aumentoEnY = imagenOriginal.getHeight() / cantFilas;
        listaSegmentos.clear();
        Integer posX = 0;
        Integer posY = 0;
        for (int i = 1; i <= cantFilas; i++) {
            for (int k = 1; k <= cantColumnas; k++) {
                BufferedImage imagen = imagenOriginal.getSubimage(posX, posY, aumentoEnX, aumentoEnY);
                listaSegmentos.add(imagen);
                posX += aumentoEnX;
            }
            posX = 0;
            posY += aumentoEnY;
        }
        try {
            pasarSegmentosADatos();
        } catch (IOException ex) {
            Logger.getLogger(CapaAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pasarSegmentosADatos() throws IOException {
        listaDatos = new ArrayList();
        for (int i = 0; i < listaSegmentos.size(); i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(listaSegmentos.get(i), "jpg", baos);
            byte[] bytes = baos.toByteArray();

            Datos dato = new Datos(i, bytes,1,1,cantFilas,cantColumnas);
            listaDatos.add(dato);
        }
    }

    private void pasarDatosASegmentos() throws IOException {
        for (int i = 0; i < listaDatos.size(); i++) {
            ByteArrayInputStream bis = new ByteArrayInputStream(listaDatos.get(i).getImagen());
            BufferedImage bImage = ImageIO.read(bis);
            ImageIO.write(bImage, "jpg", new File("output" + i + ".jpg"));
            listaSegmentos.add(bImage);
        }
        pasarSegmentosAImagen();
    }
    
    private void pasarSegmentosAImagen(){
        
    }
}
