package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.formateador;

import java.time.Duration;
import java.util.ArrayList;


public class intervaloAlarmaVentana {

    private final ArrayList<Duration> duracionAlarmas = new ArrayList<>();
    @FXML
    private TextField intervalo;
    @FXML
    private Text mensaje;

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaIntervalo.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Definir intervalos alarmas");
        s.setResizable(false);
        s.setScene(scene);
        s.show();
    }

    @FXML
    public void clickOk() {
        String intervalo = this.intervalo.getText();
        Duration intervaloFormateado = formateador.formatearDuracion(intervalo);
        if (intervaloFormateado != null) {
            this.mensaje.setText("✓ Alarma agregada");
            this.duracionAlarmas.add(intervaloFormateado);
        } else {
            this.mensaje.setText("✕ No se ha ingresado un intervalo válido");
        }
        this.intervalo.setText("");
    }

    public ArrayList<Duration> obtenerDuraciones() {
        return this.duracionAlarmas;
    }

}
