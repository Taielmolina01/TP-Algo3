package org.example.VentanasAuxiliares;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.formateador;
import org.example.interfazGuardarActividadNueva;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class eventoVentana implements Initializable {

    @FXML
    private Button botonCrear;
    @FXML
    private TextField nombreEventoText;
    @FXML
    private TextField descripcionEventoText;
    @FXML
    private TextField fechaInicioText;
    @FXML
    private TextField fechaFinalText;
    @FXML
    private TextField duracionEventoText;
    @FXML
    private ComboBox<String> alarmas;
    @FXML
    private ComboBox<String> repeticion;
    @FXML
    private CheckBox diaCompleto;
    @FXML
    private AnchorPane scenePane;
    private intervaloAlarmaVentana ventanaAlarma;
    private repeticionVentana repeticionVentana;
    private final String[] valoresPosibles = new String[]{"Sí", "No"};
    private final interfazGuardarActividadNueva i;

    public eventoVentana(interfazGuardarActividadNueva i) {
        this.i = i;
    }

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaCrearEvento.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Creando evento");
        s.setScene(scene);
        s.show();
    }

    @FXML
    public void ingresarDatosEvento(ActionEvent action) {
        String nombre = this.nombreEventoText.getText();
        String descripcion = this.descripcionEventoText.getText();
        LocalDateTime fechaInicio;
        LocalDateTime fechaFinal;
        Duration duracionEvento = formateador.formatearDuracion(this.duracionEventoText.getText());
        if (this.datosInicialesNoSonValidos(nombre, duracionEvento)) {
            errorVentana.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicioText.getText(), formateador.formatterConHoras);
            if (this.noHayRepeticion()) {
                ArrayList<Duration> alarmas = this.obtenerAlarmas();
                Stage stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
                try {
                    this.i.guardarEventoSinRepeticion(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(), alarmas);
                } catch (IOException e) {
                    //
                }
                return;
            }
            fechaFinal = LocalDateTime.parse(this.fechaFinalText.getText(), formateador.formatterConHoras);
        } catch (DateTimeParseException e4) {
            errorVentana.lanzarVentanaError();
            return;
        }
        ArrayList<Duration> alarmas = this.obtenerAlarmas();
        try {
            this.i.guardarEventoRepeticionDiaria(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
                    fechaFinal, new FrecuenciaDiaria(this.repeticionVentana.obtenerRepeticiones()), alarmas);
        } catch (IOException e) {
            //
        }
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
        this.repeticion.getItems().addAll(this.valoresPosibles);
        this.botonCrear.setOnAction(this::ingresarDatosEvento);
        this.repeticion.setOnAction(this::establecerRepeticionDiaria);
        this.alarmas.setOnAction(this::crearAlarmas);
    }

    @FXML
    public void establecerRepeticionDiaria(ActionEvent event) {
        if (this.repeticion.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.repeticionVentana = new repeticionVentana();
                this.repeticionVentana.start();
            } catch (Exception e) {
                errorVentana.lanzarVentanaError();
            }
        }
    }

    private boolean datosInicialesNoSonValidos(String nombre, Duration duracionEvento) {
        return nombre.equals("") || (!this.diaCompleto.isSelected() && duracionEvento == null);
    }

    private boolean noHayRepeticion() {
        return this.repeticionVentana == null || this.repeticionVentana.obtenerRepeticiones() == null ||
                this.repeticion.getValue().equals(valoresPosibles[1]);
    }

    @FXML
    public void crearAlarmas(ActionEvent event) {
        if (this.alarmas.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new intervaloAlarmaVentana();
                this.ventanaAlarma.start();
            } catch (Exception e) {
                errorVentana.lanzarVentanaError();
            }
        }
    }

    private ArrayList<Duration> obtenerAlarmas() {
        if (this.alarmas.getValue() != null && this.alarmas.getValue().equals(valoresPosibles[0])) {
            return this.ventanaAlarma.obtenerDuraciones();
        }
        return null;
    }
}