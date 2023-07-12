package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Formateador;
import org.example.ModoApp;

import java.time.Duration;
import java.util.ArrayList;


public class VentanaCrearAlarmas {

    private final ArrayList<Duration> duracionAlarmas = new ArrayList<>();
    @FXML
    private TextField intervalo;
    @FXML
    private Text mensaje;
    @FXML
    private AnchorPane parent;
    private ModoApp.modo modoActual;

    public void start(ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaIntervalo.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Definir intervalos alarmas");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.parent.requestFocus();
        this.modoActual = modoActual;
        ModoApp.setStyleSheet(this.modoActual, this.parent);
    }

    @FXML
    public void clickOk() {
        String intervalo = this.intervalo.getText();
        Duration intervaloFormateado = Formateador.formatearDuracion(intervalo);
        if (intervaloFormateado != null) {
            this.mensaje.setText("✓ Alarma agregada");
            this.duracionAlarmas.add(intervaloFormateado);
        } else {
            this.mensaje.setText("✕ No se ha ingresado un intervalo válido");
        }
        this.intervalo.clear();
    }

    public ArrayList<Duration> obtenerDuraciones() {
        return this.duracionAlarmas;
    }

}
