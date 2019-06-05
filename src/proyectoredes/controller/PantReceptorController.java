/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import proyectoredes.model.CapaAplicacion;
import proyectoredes.model.CapaEnlaceDatos;
import proyectoredes.model.CapaRed;
import proyectoredes.model.CapaTransporte;
import proyectoredes.model.Trama;
import proyectoredes.util.Conexion;

/**
 * FXML Controller class
 *
 * @author Kevin F
 */
public class PantReceptorController extends Controller implements Initializable {

    @FXML
    private JFXTextField tf_puerto;
    @FXML
    private JFXButton bot_Iniciar;
    @FXML
    private JFXButton bot_Detener;
    @FXML
    private ImageView iv_imagen;
    public static ServerSocket serverSocket;
    private Socket socket;
    private Integer puerto;
    public static List<Trama> tramasRecibidas = new ArrayList();
    private Conexion conexion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bot_Detener.setDisable(true);
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void presionarBotIniciar(ActionEvent event) {
        puerto = Integer.parseInt(tf_puerto.getText());
        try {
            empezarAEscuchar();
        } catch (IOException ex) {
            Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bot_Iniciar.setDisable(true);
        bot_Detener.setDisable(false);
        tf_puerto.setDisable(true);
    }

    @FXML
    private void presionarBotDetener(ActionEvent event) {
        try {
            detenerEscuchar();
        } catch (IOException ex) {
            Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bot_Iniciar.setDisable(false);
        bot_Detener.setDisable(true);
        tf_puerto.setDisable(false);
    }

    private void empezarAEscuchar() throws IOException {
        serverSocket = new ServerSocket(puerto);
        esperarConexion();

    }

    private void esperarConexion() throws IOException {
        conexion = new Conexion(null);
        conexion.start();
        
    }

    private void detenerEscuchar() throws IOException {
        conexion.setContinuar(false);
        //serverSocket.close();
        armarImagen();
    }

    private void armarImagen() {
        //ordenar lista de tramas
        tramasRecibidas = tramasRecibidas.stream().sorted((o1, o2)->o1.getNumTrama().
                                   compareTo(o2.getNumTrama())).
                                   collect(Collectors.toList());
        
        CapaEnlaceDatos capaEnlaceDatos = new CapaEnlaceDatos(tramasRecibidas,"hola");
        CapaRed capaRed = new CapaRed(capaEnlaceDatos.getListaPaquetes());
        CapaTransporte capaTransporte = new CapaTransporte(capaRed.getListaSegmentos(),null);
        try {
            CapaAplicacion capaAplicacion = new CapaAplicacion(capaTransporte.getListaDatos());
        } catch (IOException ex) {
            Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
