package org.example.VentanasAuxiliares;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class errorVentana {
    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaError.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stageCrearEvento = new Stage();
        stageCrearEvento.setTitle("Error");
        stageCrearEvento.setScene(scene);
        stageCrearEvento.show();
    }

    public static void lanzarVentanaError() {
        try {
            new errorVentana().start();
        } catch (Exception e5) {
            //
        }
    }
}
