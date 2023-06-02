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
import org.example.Alarma.Alarma;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
    public void ingresarDatosEvento() throws IOException { // Revisar si se me esta escapando algun caso borde mas
        String nombre = this.nombreEventoText.getText();
        String descripcion = this.descripcionEventoText.getText();
        LocalDateTime fechaInicio;
        LocalDateTime fechaFinal;
        Duration duracionEvento = Main.formatearDuracion(this.duracionEventoText.getText());
        if (this.datosInicialesNoSonValidos(nombre, descripcion, duracionEvento)) {
            Main.lanzarVentanaError();
            return;
        }
        try {
            fechaInicio = LocalDateTime.parse(this.fechaInicioText.getText(), Main.formatterConHoras);
            if (this.noHayRepeticion()) {
                int ID = Main.calendario.crearEvento(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected());
                this.agregarAlarmas(ID);
                Stage stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
                Main.guardarEstado();
                return;
            }
            fechaFinal = LocalDateTime.parse(this.fechaFinalText.getText(), Main.formatterConHoras);
        } catch (DateTimeParseException e4) {
            Main.lanzarVentanaError();
            return;
        }
        int ID = Main.calendario.crearEvento(nombre, descripcion, fechaInicio, duracionEvento, this.diaCompleto.isSelected(),
                fechaFinal, new FrecuenciaDiaria(this.repeticionVentana.obtenerRepeticiones()));
        this.agregarAlarmas(ID);
        Main.guardarEstado();
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
        if (this.alarmas.getValue().equals(this.valoresPosibles[0])) {
            try {
                this.ventanaAlarma = new intervaloAlarmaVentana();
                this.ventanaAlarma.start(new Stage());
            } catch (Exception e) {
                Main.lanzarVentanaError();
            }
        }
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

    private void agregarAlarmas(int ID) {
        if (this.alarmas.getValue() != null && this.alarmas.getValue().equals(valoresPosibles[0])) {
            for (Duration duracion : this.ventanaAlarma.obtenerDuraciones()) {
                Main.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
            }
        }
    }
}