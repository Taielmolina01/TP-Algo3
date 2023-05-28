package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class infoCompletaVentana extends Application {

    @FXML
    private Label label;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaInfoCompleta.fxml"));
        Scene scene = new Scene(root);
        Stage stageCrearEvento = new Stage();
        stageCrearEvento.setTitle("Información completa");
        stageCrearEvento.setScene(scene);
        stageCrearEvento.show();
    }

    public void pasarInformacion(String informacion) {
        label.setText(informacion);
        System.out.println(label.getText());
    }

}
