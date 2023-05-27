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

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class tareaVentana extends Application implements Initializable {
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
    private String[] valoresPosibles = new String[]{"Sí", "No"};
    private intervaloAlarmaVentana ventanaAlarma;
    private ArrayList<Duration> duraciones;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaCrearTarea.fxml"));
        Scene scene = new Scene(root);
        Stage stageCrearTarea = new Stage();
        stageCrearTarea.setTitle("Creando tarea");
        stageCrearTarea.setScene(scene);
        stageCrearTarea.show();
    }

    @FXML
    public void ingresarDatosTarea() { // Revisar el orden de to-do esto.
        String nombre = this.nombreTarea.getText();
        String descripcion = this.descripcionTarea.getText();
        LocalDateTime fechaInicio;
        if (nombre.equals("") || descripcion.equals("")) {
            Main.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicio.getText(), Main.formatter);
        } catch (DateTimeParseException e4) {
            Main.lanzarVentanaError();
            return;
        }
        int ID = Main.calendario.crearTarea(nombre, descripcion, fechaInicio, this.diaCompleto.isSelected());
        if (this.ventanaAlarma != null) {
            for (Duration duracion : this.ventanaAlarma.obtenerDuraciones()) {
                Main.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
            }
        }
        Main.guardarEstado();
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.alarmas.getItems().addAll(this.valoresPosibles);
    }

    @FXML
    public void crearAlarmas() {
        if (this.alarmas.getValue().equals("Sí")) {
            try {
                this.ventanaAlarma = new intervaloAlarmaVentana();
                this.ventanaAlarma.start(new Stage());
            } catch (Exception e) {
                Main.lanzarVentanaError();
            }
        }
    }
}
