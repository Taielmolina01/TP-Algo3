package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class infoCompletaVentana extends Application {

    @FXML
    private TextField infoCompleta;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaInfoCompleta.fxml"));
        Scene scene = new Scene(root);
        Stage stageCrearEvento = new Stage();
        stageCrearEvento.setTitle("Información completa");
        stageCrearEvento.setScene(scene);
        stageCrearEvento.show();
    }

    public void setText(String info) {
        this.infoCompleta.setText(info);
    }
}
