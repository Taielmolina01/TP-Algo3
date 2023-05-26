package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class repeticionVentana extends Application {
    @FXML
    private TextField repeticion;
    @FXML
    private Text mensaje;

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
            this.mensaje.setText("✓ Repeticion agregada");
        } catch (NumberFormatException e1) {
            this.mensaje.setText("✕ No se ha ingresado un número");
        }
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }


}
