package org.example;

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

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class tareaVentana implements Initializable {

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
    private String[] valoresPosibles = new String[]{"Sí", "No"};
    private intervaloAlarmaVentana ventanaAlarma;
    private ArrayList<Duration> duraciones;
    private interfazGuardado i;


    /*
     Opc1: Deberia devolver el tarea con las alarmas agregadas y despues directamente lo agrego al calendario.
     Me estoy cagando en la fachada y deberia agregar esa forma de agregar un tarea al modelo
     (Calendario.java), tampoco lo veo taaan mal, es una ventana para agregar una tarea.
     Opc2: paso toda la info necesaria para agregar la tarea y sus alarmas y lo hago desde el main(creo q
     estaria mejor aunque en cuanto a codigo es + largo).

     Esto se haria al ejecutarse ingresarDatosTarea() y en caso de haber exito, deberia usar el observer con una
     callback function.
    */


    public tareaVentana(interfazGuardado i) {
        this.i = i;
    }

    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaCrearTarea.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stageCrearTarea = new Stage();
        stageCrearTarea.setTitle("Creando tarea");
        stageCrearTarea.setScene(scene);
        stageCrearTarea.show();
    }

    @FXML
    public void ingresarDatosTarea(ActionEvent event) {
        String nombre = this.nombreTarea.getText();
        String descripcion = this.descripcionTarea.getText();
        LocalDateTime fechaInicio;
        if (datosInicialesNoSonValidos(nombre, descripcion)) {
            Main.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicio.getText(), formateador.formatterConHoras);
        } catch (DateTimeParseException e4) {
            Main.lanzarVentanaError();
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

    private boolean datosInicialesNoSonValidos(String nombre, String descripcion) {
        return nombre.equals("") || descripcion.equals("");
    }

    @FXML
    public void crearAlarmas(ActionEvent event) {
        if (this.alarmas.getValue().equals(valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new intervaloAlarmaVentana();
                this.ventanaAlarma.start();
            } catch (Exception e) {
                Main.lanzarVentanaError();
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
