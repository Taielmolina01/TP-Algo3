package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.ModoApp;

public class VentanaMostrarInfoCompleta {

    @FXML
    private AnchorPane parent;
    @FXML
    private TextArea infoCompleta;
    private ModoApp.modo modoActual;

    public void start(String info, ModoApp.modo modoActual) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/escenaInfoCompleta.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Información completa");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.infoCompleta.setText(info);
        this.modoActual = modoActual;
        ModoApp.setModo(this.modoActual, this.parent);
    }
}
