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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import proyectoredes.model.Trama;
import proyectoredes.util.AppContext;
import proyectoredes.util.Conexion;
import proyectoredes.util.FlowController;
import proyectoredes.util.Formato;

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
    public ImageView iv_imagen;
    public static ServerSocket serverSocket;
    private Socket socket;
    private Integer puerto;
    public static List<Trama> tramasRecibidas = new ArrayList();
    public static Boolean continuar = true;
    private Conexion conexion;
    public static Timer timer = null;
    @FXML
    private Label lbl_Errores;
    @FXML
    public VBox vb_Errores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bot_Detener.setDisable(true);
        tf_puerto.textFormatterProperty().set(Formato.getInstance().integerFormat());
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void presionarBotIniciar(ActionEvent event) {
        vb_Errores.getChildren().removeAll();
        puerto = null;
        if (!tf_puerto.getText().isEmpty()) {
            puerto = Integer.parseInt(tf_puerto.getText());
        }
        
        if (puerto != null) {
            try {
                empezarAEscuchar();
            } catch (IOException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            bot_Iniciar.setDisable(true);
            bot_Detener.setDisable(false);
            tf_puerto.setDisable(true);
        }

    }

    @FXML
    private void presionarBotDetener(ActionEvent event) {
        if (!serverSocket.isClosed()) {
            try {
                detenerEscuchar();
            } catch (IOException ex) {
                Logger.getLogger(PantReceptorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        bot_Iniciar.setDisable(false);
        bot_Detener.setDisable(true);
        tf_puerto.setDisable(false);
    }

    private void empezarAEscuchar() throws IOException {
        serverSocket = new ServerSocket(puerto);
        continuar = true;
        //serverSocket.setSoTimeout(30);
        esperarConexion();

    }

    private void esperarConexion() throws IOException {
        conexion = new Conexion(null);
        conexion.start();
        tramasRecibidas = new ArrayList();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }

    }
    private TimerTask timerTask = new TimerTask() {
        //espacio para declarar variables
        @Override
        public void run() {
            Image imagen = (Image) AppContext.getInstance().get("Imagen");
            AppContext.getInstance().delete("Imagen");
            if (imagen != null) {
                Platform.runLater(() -> {
                    ponerImagen(imagen);
                });
            }
            if(!FlowController.errores.isEmpty()){
                Platform.runLater(() -> {
                    for(int i=0;i<FlowController.errores.size();i++){
                        vb_Errores.getChildren().add(FlowController.errores.get(0));
                        FlowController.errores.remove(0);
                    }
                });
            }

        }
    };

    private void detenerEscuchar() throws IOException {
        serverSocket.close();
    }

    private void ponerImagen(Image imagen) {
        iv_imagen.setImage(imagen);
        bot_Detener.setDisable(true);
        bot_Iniciar.setDisable(false);
        tf_puerto.setDisable(false);
    }
}
