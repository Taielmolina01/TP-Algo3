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
import org.example.InterfazGuardarActividadNueva;
import org.example.ModoApp;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VentanaCrearTarea implements Initializable {

    private final String[] valoresPosibles = new String[]{"Sí", "No"};
    private final InterfazGuardarActividadNueva i;
    @FXML
    private Button botonCrear;
    @FXML
    private TextField nombreTarea;
    @FXML
    private TextField descripcionTarea;
    @FXML
    private TextField fechaInicio;
    @FXML
    private ComboBox<String> alarmas;
    @FXML
    private CheckBox diaCompleto;
    @FXML
    private AnchorPane parent;
    private VentanaCrearAlarmas ventanaAlarma;
    private ArrayList<Duration> duraciones;
    private VentanaLanzarError ventanaError;
    private ModoApp.modo modoActual;

    public VentanaCrearTarea(InterfazGuardarActividadNueva i) {
        this.i = i;
    }

    public void start(ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaCrearTarea.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Creando tarea");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.modoActual = modoActual;
        this.parent.requestFocus();
        ModoApp.setModo(this.modoActual, this.parent);
    }

    @FXML
    public void ingresarDatosTarea(ActionEvent event) {
        String nombre = this.nombreTarea.getText();
        String descripcion = this.descripcionTarea.getText();
        LocalDateTime fechaInicio;
        if (nombre.equals("")) {
            VentanaLanzarError.lanzarVentanaError(this.modoActual);
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicio.getText(), Formateador.formatterConHoras);
        } catch (DateTimeParseException e4) {
            VentanaLanzarError.lanzarVentanaError(this.modoActual);
            return;
        }
        try {
            this.i.guardarTarea(nombre, descripcion, fechaInicio, this.diaCompleto.isSelected(), this.obtenerAlarmas());
        } catch (IOException e) {
            //
        }
        Stage stage = (Stage) this.parent.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
        this.botonCrear.setOnAction(this::ingresarDatosTarea);
        this.alarmas.setOnAction(this::crearAlarmas);
    }

    @FXML
    public void crearAlarmas(ActionEvent event) {
        if (this.alarmas.getValue().equals(valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new VentanaCrearAlarmas();
                this.ventanaAlarma.start(this.modoActual);
            } catch (Exception e) {
                VentanaLanzarError.lanzarVentanaError(this.modoActual);
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
