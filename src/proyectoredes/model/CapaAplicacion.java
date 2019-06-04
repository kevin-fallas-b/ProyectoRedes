/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    List<Datos> listaDatos;
    BufferedImage imagenOriginal;
    VBox apCentro = new VBox();
    HBox hboxes[] = new HBox[3];

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
            System.out.println("width: " + imagenOriginal.getWidth() + "  Height: " + imagenOriginal.getHeight());
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
        segmentarImagen();
        return listaSegmentos;
    }

    public void setListaSegmentos(List<BufferedImage> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
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
                ImageView imageview = new ImageView(SwingFXUtils.toFXImage(imagen, null));
                hboxes[i - 1].getChildren().add(imageview);
                listaSegmentos.add(imagen);
                posX += aumentoEnX;

            }
            posX = 0;
            posY += aumentoEnY;
        }

    }

    private void pasarSegmentosADatos() throws IOException {
        listaDatos = new ArrayList();
        for (int i = 0; i < listaSegmentos.size(); i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(listaSegmentos.get(i), "jpg", baos);
            byte[] bytes = baos.toByteArray();
            
            Datos dato = new Datos(i,bytes);
            listaDatos.add(dato);
        }
    }

    public List<Datos> getListaDatos(){
        return listaDatos;
    }
}
