package org.example.VentanasAuxiliares;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class repeticionVentana implements Initializable {

    @FXML
    private Button botonOk;
    @FXML
    private TextField repeticion;
    @FXML
    private Text mensaje;
    private Integer repeticiones;

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaRepeticion.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stageIntervalo = new Stage();
        stageIntervalo.setTitle("Definir repeticiones");
        stageIntervalo.setScene(scene);
        stageIntervalo.show();
    }

    @FXML
    public void clickOk(ActionEvent event) {
        String repeticion = this.repeticion.getText();
        try {
            this.repeticiones = Integer.parseInt(repeticion);
            this.mensaje.setText("✓ Repeticion agregada");
        } catch (NumberFormatException e1) {
            this.mensaje.setText("✕ No se ha ingresado un número");
        }
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.botonOk.setOnAction(this::clickOk);
    }
}
