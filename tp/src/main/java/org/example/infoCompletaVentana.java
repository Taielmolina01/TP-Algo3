package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class infoCompletaVentana {

    @FXML
    private TextArea infoCompleta;

    public void start(String info) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/escenaInfoCompleta.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stageInfoCompleta = new Stage();
        stageInfoCompleta.setTitle("Información completa");
        stageInfoCompleta.setScene(scene);
        stageInfoCompleta.show();
        stageInfoCompleta.setResizable(false);
        this.infoCompleta.setText(info);
    }
}
