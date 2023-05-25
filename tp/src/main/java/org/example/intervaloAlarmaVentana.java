package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.ArrayList;

public class intervaloAlarmaVentana extends Application {

    @FXML
    private TextField intervalo;

    private ArrayList<Duration> duracionAlarmas = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaIntervalo.fxml"));
        Scene scene = new Scene(root);
        Stage stageIntervalo = new Stage();
        stageIntervalo.setTitle("Definir intervalo alarma");
        stageIntervalo.setScene(scene);
        stageIntervalo.show();
    }

    @FXML
    public void clickOk() {
        String intervalo = this.intervalo.getText();
        if (!intervalo.equals("")) {
            Duration intervaloFormateado = Main.formatearDuracion(intervalo);
            if (intervaloFormateado != null) {
                this.duracionAlarmas.add(intervaloFormateado);
            }
        }
    }

    public ArrayList<Duration> obtenerDuraciones() {
        return this.duracionAlarmas;
    }
}
