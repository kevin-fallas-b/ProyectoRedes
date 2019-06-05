/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.model;

import java.awt.Graphics2D;
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
        try {
            pasarDatosASegmentos();
        } catch (IOException ex) {
            Logger.getLogger(CapaAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            Datos dato = new Datos(i,bytes,cantFilas,cantColumnas);
            listaDatos.add(dato);
        }
    }

    private void pasarDatosASegmentos() throws IOException {
        listaSegmentos = new ArrayList();
        for (int i = 0; i < listaDatos.size(); i++) {
            ByteArrayInputStream bis = new ByteArrayInputStream(listaDatos.get(i).getImagen());
            BufferedImage bImage = ImageIO.read(bis);
            ImageIO.write(bImage, "jpg", new File("output" + i + ".jpg"));
            listaSegmentos.add(bImage);
        }
        pasarSegmentosAImagen();
    }
    
    private void pasarSegmentosAImagen(){
        cantFilas = listaDatos.get(0).getCantMaxFilas();
        cantColumnas = listaDatos.get(0).getCantMaxColumnas();
        Integer cont=0;//simple contador para saber cual imagen leer de la lista
        Integer aumentoEnX=listaSegmentos.get(0).getWidth();
        Integer aumentoEnY=listaSegmentos.get(0).getHeight();
        Integer xCurrent=0;
        Integer yCurrent=0;
        //tenemos que calcular que tan alta y ancha va a ser la imagen final
        Integer alturaFinal = listaSegmentos.get(0).getHeight()*cantFilas;
        Integer AnchoFinal = listaSegmentos.get(0).getWidth()*cantColumnas;
        //creamos una imagen en blanco con las dimensiones correctas donde vamos a agregar las imagenes pequenas
        BufferedImage imagenFinal = new BufferedImage(AnchoFinal, alturaFinal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagenFinal.createGraphics();
        
        //agregamos los segmentos de imagen a la imagen final
        for(int i=0;i<cantFilas;i++){
            for(int k=0;k<cantColumnas;k++){
                g2d.drawImage(listaSegmentos.get(cont),xCurrent,yCurrent,null);
                xCurrent+=aumentoEnX;
                cont++;
            }
            yCurrent+=aumentoEnY;
            xCurrent=0;
        }
        g2d.dispose();
        try {
            System.out.println(ImageIO.write(imagenFinal, "jpg", new File("C:\\Users\\Kevin F\\Pictures\\concat.jpg")));
            System.out.println("se intento guardar la imagen");
        } catch (IOException ex) {
            Logger.getLogger(CapaAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
