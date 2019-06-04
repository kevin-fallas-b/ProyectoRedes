/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoredes;

import javafx.application.Application;
import javafx.stage.Stage;
import proyectoredes.util.FlowController;

/**
 *
 * @author Kevin F
 */
public class ProyectoRedes extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain();//hola
        FlowController.getInstance().goView("PantEmisor");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
