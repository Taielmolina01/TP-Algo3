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
import org.example.formateador;
import org.example.interfazGuardarActividadNueva;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class tareaVentana implements Initializable {

    private final String[] valoresPosibles = new String[]{"Sí", "No"};
    private final interfazGuardarActividadNueva i;
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
    private AnchorPane scenePane;
    private intervaloAlarmaVentana ventanaAlarma;
    private ArrayList<Duration> duraciones;

    public tareaVentana(interfazGuardarActividadNueva i) {
        this.i = i;
    }

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaCrearTarea.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Creando tarea");
        s.setResizable(false);
        s.setScene(scene);
        s.show();
    }

    @FXML
    public void ingresarDatosTarea(ActionEvent event) {
        String nombre = this.nombreTarea.getText();
        String descripcion = this.descripcionTarea.getText();
        LocalDateTime fechaInicio;
        if (nombre.equals("")) {
            errorVentana.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicio.getText(), formateador.formatterConHoras);
        } catch (DateTimeParseException e4) {
            errorVentana.lanzarVentanaError();
            return;
        }
        try {
            this.i.guardarTarea(nombre, descripcion, fechaInicio, this.diaCompleto.isSelected(), this.obtenerAlarmas());
        } catch (IOException e) {
            //
        }
        Stage stage = (Stage) scenePane.getScene().getWindow();
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
