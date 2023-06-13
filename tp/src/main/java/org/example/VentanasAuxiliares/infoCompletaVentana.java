package org.example.VentanasAuxiliares;

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
        Stage s = new Stage();
        s.setTitle("Información completa");
        s.setResizable(false);
        s.setScene(scene);
        s.show();
        this.infoCompleta.setText(info);
    }
}
