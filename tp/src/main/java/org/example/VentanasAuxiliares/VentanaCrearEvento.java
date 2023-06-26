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
import org.example.Formateador;
import org.example.Frecuencia.*;
import org.example.InterfazGuardarActividadNueva;
import org.example.ModoApp;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class VentanaCrearEvento implements Initializable {

    private final String[] valoresPosiblesRepeticion = new String[]{"Sin repetición", "Diaria", "Semanal", "Mensual", "Anual"};
    private final String[] valoresPosibles = new String[]{"Sí", "No"};
    private final InterfazGuardarActividadNueva i;
    @FXML
    private Button botonCrear;
    @FXML
    private TextField nombreEventoText;
    @FXML
    private TextField descripcionEventoText;
    @FXML
    private TextField fechaInicioText;
    @FXML
    private TextField duracionEventoText;
    @FXML
    private ComboBox<String> alarmas;
    @FXML
    private ComboBox<String> repeticion;
    @FXML
    private CheckBox diaCompleto;
    @FXML
    private AnchorPane parent;
    private LocalDateTime fechaFinal;
    private Integer repeticiones;
    private Frecuencia frecuencia;
    private VentanaCrearAlarmas ventanaAlarma;
    private VentanaEstablecerRep ventanaEstablecerRep;
    private VentanaEstablecerRepSemanal ventanaEstablecerRepSemanal;
    private ModoApp.modo modoActual;

    public VentanaCrearEvento(InterfazGuardarActividadNueva i) {
        this.i = i;
    }

    public void start(ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaCrearEvento.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Creando evento");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.parent.requestFocus();
        this.modoActual = modoActual;
        ModoApp.setStyleSheet(this.modoActual, this.parent);
    }

    @FXML
    public void ingresarDatosEvento(ActionEvent action) {
        String nombre = this.nombreEventoText.getText();
        String descripcion = this.descripcionEventoText.getText();
        LocalDateTime fechaInicio;
        Duration duracionEvento = Formateador.formatearDuracion(this.duracionEventoText.getText());
        if (this.datosInicialesNoSonValidos(nombre, duracionEvento)) {
            VentanaLanzarError.lanzarVentana(this.modoActual);
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicioText.getText(), Formateador.formatterConHoras);
            if (this.noHayRepeticion()) {
                ArrayList<Duration> alarmas = this.obtenerAlarmas();
                Stage stage = (Stage) this.parent.getScene().getWindow();
                stage.close();
                try {
                    this.i.guardarEventoSinRepeticion(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(), alarmas);
                } catch (IOException e) {
                    throw new RuntimeException("No se ha podido guardar el evento");
                }
                return;
            }
        } catch (DateTimeParseException e4) {
            VentanaLanzarError.lanzarVentana(this.modoActual);
            return;
        }
        ArrayList<Duration> alarmas = this.obtenerAlarmas();
        if (!sonValidosDatosFrecuencia()) {
            VentanaLanzarError.lanzarVentana(this.modoActual);
            return;
        }
        try {
            this.i.guardarEventoConRepeticion(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
                    this.fechaFinal, this.frecuencia, alarmas);
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido guardar el evento");
        }
        Stage stage = (Stage) this.parent.getScene().getWindow();
        stage.close();
    }

    private boolean sonValidosDatosFrecuencia() {
        switch (this.repeticion.getValue()) {
            case "Diaria" -> {
                this.establecerFechaFinalYRepsNoSemanal();
                if (this.repsYFechaFinalSonInvalidos()) {
                    return false;
                }
                this.frecuencia = new FrecuenciaDiaria(this.repeticiones);
            }
            case "Semanal" -> {
                TreeSet<DayOfWeek> dias = this.ventanaEstablecerRepSemanal.obtenerDiasSemana();
                this.fechaFinal = this.ventanaEstablecerRepSemanal.obtenerFechaFinal();
                this.repeticiones = this.ventanaEstablecerRepSemanal.obtenerRepeticiones();
                if (dias == null || dias.size() == 0 || this.repsYFechaFinalSonInvalidos()) {
                    return false;
                }
                this.frecuencia = new FrecuenciaSemanal(dias, repeticiones);
            }
            case "Mensual" -> {
                this.establecerFechaFinalYRepsNoSemanal();
                if (this.repsYFechaFinalSonInvalidos()) {
                    return false;
                }
                this.frecuencia = new FrecuenciaMensual(repeticiones);
            }
            default -> {
                this.establecerFechaFinalYRepsNoSemanal();
                if (this.repsYFechaFinalSonInvalidos()) {
                    return false;
                }
                this.frecuencia = new FrecuenciaAnual(this.repeticiones);
            }
        }
        return true;
    }

    private void establecerFechaFinalYRepsNoSemanal() {
        this.fechaFinal = this.ventanaEstablecerRep.obtenerFechaFinal();
        this.repeticiones = this.ventanaEstablecerRep.obtenerRepeticiones();
    }

    private boolean repsYFechaFinalSonInvalidos() {
        return this.repeticiones == null || this.fechaFinal == null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
        this.repeticion.getItems().addAll(this.valoresPosiblesRepeticion);
        this.botonCrear.setOnAction(this::ingresarDatosEvento);
        this.repeticion.setOnAction(this::establecerRepeticion);
        this.alarmas.setOnAction(this::crearAlarmas);
    }

    public void establecerRepeticion(ActionEvent event) {
        switch (this.repeticion.getValue()) {
            case "Sin repetición" -> {
                //
            }
            case "Diaria" -> {
                try {
                    this.ventanaEstablecerRep = new VentanaEstablecerRep();
                    this.ventanaEstablecerRep.start("Definir frecuencia diaria",
                            "Ingrese cada cuántos días se repite", this.modoActual);
                } catch (Exception e) {
                    VentanaLanzarError.lanzarVentana(this.modoActual);
                }
            }
            case "Semanal" -> {
                try {
                    this.ventanaEstablecerRepSemanal = new VentanaEstablecerRepSemanal();
                    this.ventanaEstablecerRepSemanal.start(this.modoActual);
                } catch (Exception e) {
                    VentanaLanzarError.lanzarVentana(this.modoActual);
                }
            }
            case "Mensual" -> {
                try {
                    this.ventanaEstablecerRep = new VentanaEstablecerRep();
                    this.ventanaEstablecerRep.start("Definir frecuencia mensual",
                            "Ingrese cada cuántos meses se repite", this.modoActual);
                } catch (Exception e) {
                    VentanaLanzarError.lanzarVentana(this.modoActual);
                }
            }
            default -> {
                try {
                    this.ventanaEstablecerRep = new VentanaEstablecerRep();
                    this.ventanaEstablecerRep.start("Definir frecuencia anual",
                            "Ingrese cada cuántos años se repite", this.modoActual);
                } catch (Exception e) {
                    VentanaLanzarError.lanzarVentana(this.modoActual);
                }
            }
        }
    }

    private boolean datosInicialesNoSonValidos(String nombre, Duration duracionEvento) {
        return nombre.equals("") || (!this.diaCompleto.isSelected() && duracionEvento == null);
    }

    private boolean noHayRepeticion() {
        return (this.ventanaEstablecerRep == null && this.ventanaEstablecerRepSemanal == null)
                || this.repeticion.getValue().equals(this.valoresPosiblesRepeticion[0]);
    }

    public void crearAlarmas(ActionEvent event) {
        if (this.alarmas.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new VentanaCrearAlarmas();
                this.ventanaAlarma.start(this.modoActual);
            } catch (Exception e) {
                VentanaLanzarError.lanzarVentana(this.modoActual);
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