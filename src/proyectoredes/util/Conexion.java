/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import proyectoredes.controller.PantReceptorController;
import static proyectoredes.controller.PantReceptorController.tramasRecibidas;
import static proyectoredes.controller.PantReceptorController.serverSocket;
import static proyectoredes.controller.PantReceptorController.tramasRecibidas;
import proyectoredes.model.CapaAplicacion;
import proyectoredes.model.CapaEnlaceDatos;
import proyectoredes.model.CapaRed;
import proyectoredes.model.CapaTransporte;
import proyectoredes.model.Trama;

/**
 *
 * @author Kevin F
 */
public class Conexion extends Thread {

    private Socket socket;
    private Boolean continuar = true;

    public Conexion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            try {
                while (continuar) {
                    Conexion conexion;
                    conexion = new Conexion(serverSocket.accept());
                    conexion.start();
                }
                System.out.println("Salio del while infinito");
                serverSocket.close();
            } catch (SocketException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException so) {

            }

        } else {
            try {
                System.out.println("se conecto");
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                Trama trama = (Trama) ois.readObject();
                if (trama.getError() == false) {
                    tramasRecibidas.add(trama);
                    System.out.println("Se recibibio una trama");
                }
                if (trama.getUltimo() == 1) {
                    continuar = false;
                    armarImagen();
                }

                
            } catch (IOException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void cerrarForzozamente(){
        
    }
    
    public void armarImagen() throws IOException {
        //ordenar lista de tramas
        tramasRecibidas = tramasRecibidas.stream().sorted((o1, o2) -> o1.getNumTrama().
                compareTo(o2.getNumTrama())).
                collect(Collectors.toList());

        CapaEnlaceDatos capaEnlaceDatos = new CapaEnlaceDatos(tramasRecibidas, "hola");
        CapaRed capaRed = new CapaRed(capaEnlaceDatos.getListaPaquetes());
        CapaTransporte capaTransporte = new CapaTransporte(capaRed.getListaSegmentos(), null);
        CapaAplicacion capaAplicacion = new CapaAplicacion(capaTransporte.getListaDatos());    
        //System.out.println(capaAplicacion.getImagenOriginal());
        AppContext.getInstance().set("Imagen", capaAplicacion.getImagenOriginalEnImage());
    }
    
    public Boolean getContinuar() {
        return continuar;
    }

    public void setContinuar(Boolean continuar) {
        this.continuar = continuar;
    }

}
