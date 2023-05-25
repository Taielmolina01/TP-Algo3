package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class repeticionVentana extends Application {
    @FXML
    private TextField repeticion;

    private Integer repeticiones;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaRepeticion.fxml"));
        Scene scene = new Scene(root);
        Stage stageIntervalo = new Stage();
        stageIntervalo.setTitle("Definir repeticiones");
        stageIntervalo.setScene(scene);
        stageIntervalo.show();
    }

    @FXML
    public void clickOk() {
        String repeticion = this.repeticion.getText();
        try {
            this.repeticiones = Integer.parseInt(repeticion);
        } catch (NumberFormatException e1) {
            this.repeticiones = null;
        }
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }
}
