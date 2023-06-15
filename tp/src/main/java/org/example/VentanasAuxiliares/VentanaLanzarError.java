package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.ModoApp;

public class VentanaLanzarError {

    @FXML
    private AnchorPane parent;
    private ModoApp.modo modoActual;

    public static void lanzarVentana(ModoApp.modo modoActual) {
        try {
            new VentanaLanzarError().start(modoActual);
        } catch (Exception e5) {
            //
        }
    }

    public void start(ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaError.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.modoActual = modoActual;
        ModoApp.setModo(this.modoActual, this.parent);
    }

}
