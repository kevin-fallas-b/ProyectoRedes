/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import proyectoredes.model.CapaAplicacion;
import proyectoredes.model.CapaEnlaceDatos;
import proyectoredes.model.CapaRed;
import proyectoredes.model.CapaTransporte;
import proyectoredes.model.Paquete;
import proyectoredes.util.FlowController;
import proyectoredes.util.Formato;
import proyectoredes.util.Mensaje;

/**
 * FXML Controller class
 *
 * @author Kevin F
 */
public class PantEmisorController extends Controller implements Initializable {

    @FXML
    private JFXButton bot_EscogerImagen;
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

    private Image imagenAEnviar;
    @FXML
    private ImageView iv_imagenAEnviar;
    @FXML
    private JFXButton bot_Enviar;
    @FXML
    private JFXTextField tf_TamSegmento;
    @FXML
    private JFXRadioButton rb_tamSegKB;
    @FXML
    private ToggleGroup tg_TamSegmento;
    private JFXRadioButton rb_tamSegMB;

    private SimpleStringProperty cantFilasProperty = new SimpleStringProperty("1");
    private SimpleStringProperty cantColumnasProperty = new SimpleStringProperty("1");
    private SimpleStringProperty tamSegmentosProperty = new SimpleStringProperty("100");
    private FileChooser fileChooser = new FileChooser();
    private File imagenEnFile;
    @FXML
    private AnchorPane ap_PantEmisor;
    private List<Line> lineas = new ArrayList();
    private List<Text> numeros = new ArrayList();
    private Double aspectRatio = 0.0;
    private Double realWidth;
    private Double realHeight;
    private List<String> direccionesIpBroadcast;
    @FXML
    private AnchorPane apDireccionesIP;
    @FXML
    private TableView<String> TbDireccionesAEnviar;
    @FXML
    private TableColumn<String, String> tcDireccionesIP;
    @FXML
    private JFXTextField txfAgregarDireccionIP;
    @FXML
    private JFXButton btnAgregarDireccionIP;
    
    //elementos para el servicio cliente-servidor
    Socket socket;
    @FXML
    private JFXTextField tf_ipDestino;
    @FXML
    private JFXTextField tf_puerto;
    private ObservableList<String> ips;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tcDireccionesIP.setCellValueFactory((param) -> 
                new SimpleStringProperty(param.getValue()));
        
        ips = FXCollections.observableArrayList();
        TbDireccionesAEnviar.setItems(ips);
        
        tf_ColumnasFragmentos.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
        tf_ColumnasFragmentos.textProperty().bindBidirectional(cantColumnasProperty);
        tf_FilasFragmentos.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
        tf_FilasFragmentos.textProperty().bindBidirectional(cantFilasProperty);
        tf_TamSegmento.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
        tf_TamSegmento.textProperty().bindBidirectional(tamSegmentosProperty);
        fileChooser.setTitle("Escoger imagen.");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        rb_TCP.setUserData("TCP");
        rb_UDP.setUserData("UDP");
        rb_tamSegKB.setUserData("KB");
        tf_ColumnasFragmentos.textProperty().addListener((observable, oldValue, newValue) -> {
            graficarImagen();
        });
        tf_FilasFragmentos.textProperty().addListener((observable, oldValue, newValue) -> {
            graficarImagen();
        });
        apDireccionesIP.setVisible(false);
        tf_ipDestino.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
        tf_puerto.textFormatterProperty().setValue(Formato.getInstance().integerFormat());
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void presionarBotEscogerImagen(ActionEvent event) {
        imagenEnFile = fileChooser.showOpenDialog(FlowController.getInstance().getMain());
        if (imagenEnFile != null) {
            imagenAEnviar = new Image(imagenEnFile.toURI().toString());
            iv_imagenAEnviar.setImage(imagenAEnviar);
            aspectRatio = imagenAEnviar.getWidth() / imagenAEnviar.getHeight();
            realWidth = Math.min(iv_imagenAEnviar.getFitWidth(), iv_imagenAEnviar.getFitHeight() * aspectRatio);
            realHeight = Math.min(iv_imagenAEnviar.getFitHeight(), iv_imagenAEnviar.getFitWidth() / aspectRatio);
            graficarImagen();
        }

    }

    @FXML
    private void presionarBotAumentarFilas(ActionEvent event) {
        Integer cant = Integer.parseInt(cantFilasProperty.getValue());
        cant++;
        cantFilasProperty.setValue(cant.toString());
    }

    @FXML
    private void presionarBotDisminuirFilas(ActionEvent event) {
        Integer cant = Integer.parseInt(cantFilasProperty.getValue());
        if (cant != 1) {
            cant--;
        }
        cantFilasProperty.setValue(cant.toString());
    }

    @FXML
    private void presionarBotAumentarColumnas(ActionEvent event) {
        Integer cant = Integer.parseInt(cantColumnasProperty.getValue());
        cant++;
        cantColumnasProperty.setValue(cant.toString());
    }

    @FXML
    private void presionarBotDisminuirColumnas(ActionEvent event) {
        Integer cant = Integer.parseInt(cantColumnasProperty.getValue());
        if (cant != 1) {
            cant--;
        }
        cantColumnasProperty.setValue(cant.toString());
    }

    @FXML
    private void presionarBotEnviar(ActionEvent event) {
        Integer cantColumnas = Integer.parseInt(cantColumnasProperty.getValue());
        Integer cantFilas = Integer.parseInt(cantFilasProperty.getValue());
        Integer tamSegmento = Integer.parseInt(tamSegmentosProperty.getValue());
        if (new Mensaje().showConfirmation("Confirmar Envio.", FlowController.getInstance().getMain().getOwner(), "Por favor confirmar los datos.\nCantidad de Filas: " + cantFilas + "\nCantidad de Columnas: " + cantColumnas + "\nProtocolo: " + TG_TipoEnvio.getSelectedToggle().getUserData() + "\nTamaño de segmento: " + tamSegmento + " " + tg_TamSegmento.getSelectedToggle().getUserData())) {
            //hacer envio
            intentarEnvio();
        }

    }

    private void graficarImagen() {
        if (imagenEnFile != null) {

            //primero que nada quitar cualquier linea ya en pantalla
            ap_PantEmisor.getChildren().removeAll(lineas);
            ap_PantEmisor.getChildren().removeAll(numeros);
            //agarro datos de la imagen
            /*
        tenia problemas ya que getfitHeight regresa el tamano del imageview no de la foto, 
        entonces este metodo utilizando el aspectRatio lo saque de y los Realheight y realwidth
        https://stackoverflow.com/questions/39408845/how-to-get-width-height-of-displayed-image-in-javafx-imageview
        el resto de algoritmo si es idea mia. - Kevin
             */
            Double xInicio = iv_imagenAEnviar.getLayoutX();
            Double xFinal = xInicio + realWidth;
            Double yInicio = iv_imagenAEnviar.getLayoutY();
            Double yFinal = yInicio + realHeight;
            //primero hacemos las columnas
            Integer cantColumnas = Integer.parseInt(cantColumnasProperty.getValue());
            Double cantAumentarEnX = realWidth / cantColumnas;
            for (int i = 1; i < cantColumnas; i++) {
                //dibujar una raya en xinicio+cantAumentarenX*i 
                Line line = new Line(xInicio + (cantAumentarEnX * i), yInicio, xInicio + (cantAumentarEnX * i), yFinal);
                lineas.add(line);
                ap_PantEmisor.getChildren().add(line);
            }

            //vamos con las filas, es lo mismo pero diferente
            Integer cantFilas = Integer.parseInt(cantFilasProperty.getValue());
            Double cantAumentarEnY = realHeight / cantFilas;
            for (int k = 1; k < cantFilas; k++) {
                //dibujar una raya en yinicio+cantAumentareny*k 
                Line line = new Line(xInicio, yInicio + (cantAumentarEnY * k), xFinal, yInicio + (cantAumentarEnY * k));
                lineas.add(line);
                ap_PantEmisor.getChildren().add(line);
            }

            //le agregamos los numeros a cada cuadro, buscamos intersecciones basicamente 
            Integer cont = 1;
            for (int j = 1; j <= cantFilas; j++) {
                for (int x = 1; x <= cantColumnas; x++) {
                    //poner cont en y=(yinicio+(aumentarEnY*j))-5 x=(xInicio+(aumentarEnX*x))-5.... el -5 es para que no lo ponga en la pura interseccion
                    Text text = new Text(cont.toString());
                    text.setLayoutX((xInicio + (cantAumentarEnX * x)) - 15);
                    text.setLayoutY((yInicio + (cantAumentarEnY * j)) - 10);
                    text.setFill(Paint.valueOf("FF0000"));
                    numeros.add(text);
                    ap_PantEmisor.getChildren().add(text);
                    cont++;
                }
            }
        }

    }

    private void intentarEnvio() {
        try {
            CapaAplicacion capaAplicacion = new CapaAplicacion(Integer.parseInt(cantFilasProperty.getValue()), Integer.parseInt(cantColumnasProperty.getValue()), imagenEnFile);
            CapaTransporte capaTransporte1 = new CapaTransporte("TCP", capaAplicacion.getListaDatos(), 100);
            List<String> destinos = new ArrayList();
            for(int i=0; i<ips.size(); i++){
                destinos.add(ips.get(i));
            }
            CapaRed capaRed = new CapaRed(capaTransporte1.getListaSegmentos(), InetAddress.getLocalHost(),destinos);
            List<List<Paquete>> lista2 = new ArrayList();
            CapaEnlaceDatos capaEnlaceDatos = new CapaEnlaceDatos(capaRed.getListaPaquetes().get(0));
            if(!tf_puerto.getText().isEmpty()){
                int puerto = Integer.parseInt(tf_puerto.getText());
                
                try {
                    for(int i=0; i<capaEnlaceDatos.getTramasEnBytes().size(); i++){
                        Socket socket = new Socket("192.168.1.9", puerto);
                        OutputStream os = socket.getOutputStream();
                        os.write(capaEnlaceDatos.getTramasEnBytes().get(i));
                        os.flush();
                        System.out.println("Trama numero "+i+" enviada! Peso: "+capaEnlaceDatos.getTramasEnBytes().get(i).length);
                        socket.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PantEmisorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Error", FlowController.getInstance().getMain().getOwner(), "Debe definir un puerto.");
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(PantEmisorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void presionarAgregarDireccionIP(ActionEvent event) {
        ips.add(txfAgregarDireccionIP.getText());
        System.out.println(txfAgregarDireccionIP.getText());
    }

    @FXML
    private void presionarRbUDP(ActionEvent event) {
        apDireccionesIP.setVisible(true);
        tf_ipDestino.setVisible(false);
    }

    @FXML
    private void presionarRbTCP(ActionEvent event) {
        apDireccionesIP.setVisible(false);
        tf_ipDestino.setVisible(true);
    }
    
    private void establecerConexion(String ipDestino,Integer puerto){
        try {
            socket = new Socket(InetAddress.getByName(ipDestino),puerto);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PantEmisorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PantEmisorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
