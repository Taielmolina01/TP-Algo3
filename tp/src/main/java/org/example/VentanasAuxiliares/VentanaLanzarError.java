package org.example.VentanasAuxiliares;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VentanaLanzarError {

    public static void lanzarVentanaError() {
        try {
            new VentanaLanzarError().start();
        } catch (Exception e5) {
            //
        }
    }

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaError.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
