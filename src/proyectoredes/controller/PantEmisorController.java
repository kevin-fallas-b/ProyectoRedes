/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import proyectoredes.util.Formato;

/**
 * FXML Controller class
 *
 * @author Kevin F
 */
public class PantEmisorController extends Controller implements Initializable {

    @FXML
    private JFXButton bot_EscogerImagen;
    @FXML
    private JFXTextField tf_RutaDeImagen;
    @FXML
    private JFXTextField tf_FilasFragmentos;
    @FXML
    private JFXTextField tf_ColumnasFragmentos;
    @FXML
    private JFXButton bot_AumentarFilas;
    @FXML
    private JFXButton bot_DisminuirFilas;
    @FXML
    private JFXButton bot_AumentarColumnas;
    @FXML
    private JFXButton Bot_DisminuirColumnas;
    @FXML
    private JFXRadioButton rb_TCP;
    @FXML
    private ToggleGroup TG_TipoEnvio;
    @FXML
    private JFXRadioButton rb_UDP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_ColumnasFragmentos.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
        tf_FilasFragmentos.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
    }    

    @Override
    public void initialize() {
     
    }

    @FXML
    private void presionarBotEscogerImagen(ActionEvent event) {
    }

    @FXML
    private void presionarBotAumentarFilas(ActionEvent event) {
    }

    @FXML
    private void presionarBotDisminuirFilas(ActionEvent event) {
    }

    @FXML
    private void presionarBotAumentarColumnas(ActionEvent event) {
    }

    @FXML
    private void presionarBotDisminuirColumnas(ActionEvent event) {
    }
    
}
