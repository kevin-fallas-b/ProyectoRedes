/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
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

    public Conexion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            try {
                while (PantReceptorController.continuar) {
                    System.out.println("*******************************\nSe Inicio a escuchar\n*******************************");
                    Conexion conexion;
                    conexion = new Conexion(serverSocket.accept());
                    conexion.start();
                }
                serverSocket.close();
            } catch (SocketException ex) {
                //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
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
                    PrintWriter pr = new PrintWriter(socket.getOutputStream());
                    pr.write(trama.getNumTrama().toString());
                    pr.flush();
                    //OutputStream os = socket.getOutputStream();
                    //os.write(trama.getNumTrama());
                    //OutputStreamWriter osw = new OutputStreamWriter(os);
                    //BufferedWriter bw = new BufferedWriter(osw);
                    //bw.write(trama.getNumTrama().toString());
                    //bw.flush();
                    //os.flush();
                    //System.out.println(socket.getInetAddress());
                    System.out.println("Trama num " + trama.getNumTrama() + " envio ACK");
                } else {
                    //trama contiene error
                    Label label = new Label("Trama numero " + trama.getNumTrama());
                    FlowController.errores.add(label);
                }
                if (trama.getUltimo() == 1) {
                    PantReceptorController.continuar = false;
                    serverSocket.close();
                    armarImagen();
                }

            } catch (IOException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
}
