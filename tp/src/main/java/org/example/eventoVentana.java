package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class eventoVentana extends Application implements Initializable {

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
    private String[] valoresPosibles = new String[]{"Sí", "No"};
    private interfazGuardado i;

    /*
    Opc1: Deberia devolver el evento con las alarmas agregadas y despues directamente lo agrego al calendario.
    Me estoy cagando en la fachada y deberia agregar esa forma de agregar un evento al modelo
    (Calendario.java), tampoco lo veo taaan mal, es una ventana para agregar un evento.
    Opc2: paso toda la info necesaria para agregar el evento y sus alarmas y lo hago desde el main(creo q
    estaria mejor aunque en cuanto a codigo es + largo).

     Esto se haria al ejecutarse ingresarDatosEvento() y en caso de haber exito, deberia usar el observer con una
     callback function.
     */


    public eventoVentana(interfazGuardado i) {
        this.i = i;
    }

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
    public void ingresarDatosEvento() {
        String nombre = this.nombreEventoText.getText();
        String descripcion = this.descripcionEventoText.getText();
        LocalDateTime fechaInicio;
        LocalDateTime fechaFinal;
        Duration duracionEvento = formateador.formatearDuracion(this.duracionEventoText.getText());
        if (this.datosInicialesNoSonValidos(nombre, descripcion, duracionEvento)) {
            Main.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicioText.getText(), formateador.formatterConHoras);
            if (this.noHayRepeticion()) {
                ArrayList<Duration> alarmas = this.obtenerAlarmas();
                Stage stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
                try {
                    this.i.guardarEventoTipo1(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(), alarmas);
                } catch (IOException e) {
                    //
                }
                return;
            }
            fechaFinal = LocalDateTime.parse(this.fechaFinalText.getText(), formateador.formatterConHoras);
        } catch (DateTimeParseException e4) {
            Main.lanzarVentanaError();
            return;
        }
        ArrayList<Duration> alarmas = this.obtenerAlarmas();
        try {
            this.i.guardarEventoTipo2(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
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
    }

    @FXML
    public void establecerRepeticionDiaria() {
        if (this.repeticion.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.repeticionVentana = new repeticionVentana();
                this.repeticionVentana.start(new Stage());
            } catch (Exception e) {
                Main.lanzarVentanaError();
            }
        }
    }

    private boolean datosInicialesNoSonValidos(String nombre, String descripcion, Duration duracionEvento) {
        return nombre.equals("") || descripcion.equals("") || (!this.diaCompleto.isSelected() && duracionEvento == null);
    }

    private boolean noHayRepeticion() {
        return this.repeticionVentana == null || this.repeticionVentana.obtenerRepeticiones() == null ||
                this.repeticion.getValue().equals(valoresPosibles[1]);
    }

    @FXML
    public void crearAlarmas() {
        if (this.alarmas.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new intervaloAlarmaVentana();
                this.ventanaAlarma.start(new Stage());
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