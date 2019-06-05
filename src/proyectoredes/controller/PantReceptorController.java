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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

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
    private ServerSocket serverSocket;
    private Integer puerto;

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

    }

    private void detenerEscuchar() throws IOException {
        serverSocket.close();
    }
}
