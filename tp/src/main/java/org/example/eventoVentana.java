package org.example;

import javafx.application.Application;
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
import org.example.Alarma.Alarma;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class eventoVentana extends Application implements Initializable {


    @FXML
    private Button botonCrear;
    @FXML
    private TextField nombreEvento;
    @FXML
    private TextField descripcionEvento;
    @FXML
    private TextField fechaInicio;
    @FXML
    private TextField fechaFinal;
    @FXML
    private TextField duracionEvento;
    @FXML
    private ComboBox<String> alarmas;
    @FXML
    private ComboBox<String> repeticion;
    @FXML
    private CheckBox diaCompleto;
    @FXML
    private AnchorPane scenePane;
    private String[] valoresPosibles = new String[]{"Sí", "No"};
    private ArrayList<Duration> duraciones;
    private Integer repeticiones;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaCrearEvento.fxml"));
        Scene scene = new Scene(root);
        Stage stageCrearEvento = new Stage();
        stageCrearEvento.setTitle("Creando evento");
        stageCrearEvento.setScene(scene);
        stageCrearEvento.show();
    }

    @FXML
    public void ingresarDatosEvento() { // Revisar el orden de to-do esto.
        String nombre = this.nombreEvento.getText();
        String descripcion = this.descripcionEvento.getText();
        LocalDateTime fechaInicio;
        LocalDateTime fechaFinal;
        Duration duracionEvento = Main.formatearDuracion(this.duracionEvento.getText());
        if (nombre.equals("") || descripcion.equals("") || (!this.diaCompleto.isSelected() && duracionEvento == null)) {
            Main.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicio.getText(), Main.formatter);
            if (this.repeticiones == null && this.diaCompleto.isSelected()) {
                int ID = Main.calendario.crearEvento(nombre, descripcion, fechaInicio, duracionEvento, true);
                for (Duration duracion : duraciones) {
                    Main.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
                }
                Stage stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
                return;
            }
            fechaFinal = LocalDateTime.parse(this.fechaFinal.getText(), Main.formatter);
        } catch (DateTimeParseException e4) {
            Main.lanzarVentanaError();
            return;
        }
        int ID = Main.calendario.crearEvento(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
                fechaFinal, new FrecuenciaDiaria(this.repeticiones));
        for (Duration duracion : duraciones) {
            Main.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
        }
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
        this.repeticion.getItems().addAll(this.valoresPosibles);
    }

    @FXML
    public void crearAlarmas() {
        try {
            intervaloAlarmaVentana ventana = new intervaloAlarmaVentana();
            this.duraciones = ventana.obtenerDuraciones();
        } catch (Exception e) {
            Main.lanzarVentanaError();
        }
    }

    @FXML
    public void establecerRepeticionDiaria() {
        try {
            repeticionVentana ventana = new repeticionVentana();
            this.repeticiones = ventana.obtenerRepeticiones();
        } catch (Exception e) {
            Main.lanzarVentanaError();
        }
    }
}