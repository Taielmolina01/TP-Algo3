package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class infoCompletaVentana {

    @FXML
    private TextField infoCompleta;

    public void start(String info) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/escenaInfoCompleta.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stageCrearEvento = new Stage();
        stageCrearEvento.setTitle("Información completa");
        stageCrearEvento.setScene(scene);
        stageCrearEvento.show();
        this.infoCompleta.setText(info);
    }
}
