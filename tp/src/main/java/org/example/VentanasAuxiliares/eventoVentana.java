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
import org.example.Frecuencia.*;
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

    private final String[] valoresPosiblesRepeticion = new String[]{"Sin repetición", "Diaria", "Semanal", "Mensual", "Anual"};
    private final String[] valoresPosibles = new String[]{"Sí", "No"};
    private final interfazGuardarActividadNueva i;
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
    private org.example.VentanasAuxiliares.repeticionVentana repeticionVentana;
    private org.example.VentanasAuxiliares.repeticionSemanalVentana repeticionSemanalVentana;

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
        s.setResizable(false);
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
        Frecuencia frecuencia = switch (this.repeticion.getValue()) {
            case "Diaria" -> new FrecuenciaDiaria(this.repeticionVentana.obtenerRepeticiones());
            case "Semanal" ->
                    new FrecuenciaSemanal(this.repeticionSemanalVentana.obtenerDiasSemana(), this.repeticionSemanalVentana.obtenerRepeticiones());
            case "Mensual" -> new FrecuenciaMensual(this.repeticionVentana.obtenerRepeticiones());
            default -> new FrecuenciaAnual(this.repeticionVentana.obtenerRepeticiones());
        };
        try {
            this.i.guardarEventoConRepeticion(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
                    fechaFinal, frecuencia, alarmas);
        } catch (IOException e) {
            //
        }

        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
        this.repeticion.getItems().addAll(this.valoresPosiblesRepeticion);
        this.botonCrear.setOnAction(this::ingresarDatosEvento);
        this.repeticion.setOnAction(this::establecerRepeticionDiaria);
        this.alarmas.setOnAction(this::crearAlarmas);
    }

    @FXML
    public void establecerRepeticionDiaria(ActionEvent event) {
        switch (this.repeticion.getValue()) {
            case "Sin repetición" -> {
                //
            }
            case "Diaria" -> {
                try {
                    this.repeticionVentana = new repeticionVentana();
                    this.repeticionVentana.start("Definir frecuencia diaria",
                            "Ingrese cada cuántos días se repite");
                } catch (Exception e) {
                    errorVentana.lanzarVentanaError();
                }
            }
            case "Semanal" -> {
                try {
                    this.repeticionSemanalVentana = new repeticionSemanalVentana();
                    this.repeticionSemanalVentana.start();
                } catch (Exception e) {
                    errorVentana.lanzarVentanaError();
                }
            }
            case "Mensual" -> {
                try {
                    this.repeticionVentana = new repeticionVentana();
                    this.repeticionVentana.start("Definir frecuencia mensual",
                            "Ingrese cada cuántos meses se repite");
                } catch (Exception e) {
                    errorVentana.lanzarVentanaError();
                }
            }
            default -> {
                try {
                    this.repeticionVentana = new repeticionVentana();
                    this.repeticionVentana.start("Definir frecuencia anual",
                            "Ingrese cada cuántos años se repite");
                } catch (Exception e) {
                    errorVentana.lanzarVentanaError();
                }
            }
        }
    }

    private boolean datosInicialesNoSonValidos(String nombre, Duration duracionEvento) {
        return nombre.equals("") || (!this.diaCompleto.isSelected() && duracionEvento == null);
    }

    private boolean noHayRepeticion() {
        return (this.repeticionVentana == null && this.repeticionSemanalVentana == null)
                || this.repeticion.getValue().equals(this.valoresPosiblesRepeticion[0]);
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