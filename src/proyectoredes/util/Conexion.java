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
import proyectoredes.controller.PantReceptorController;
import static proyectoredes.controller.PantReceptorController.tramasRecibidas;
import static proyectoredes.controller.PantReceptorController.serverSocket;
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
                if (trama.getUltimo()==1) {
                    continuar=false;
                    PantReceptorController.armarImagen();
                }
                if (trama.getError()) {
                    tramasRecibidas.add(trama);
                }

                System.out.println("Se recibibio una trama");
            } catch (IOException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Boolean getContinuar() {
        return continuar;
    }

    public void setContinuar(Boolean continuar) {
        this.continuar = continuar;
    }

}
