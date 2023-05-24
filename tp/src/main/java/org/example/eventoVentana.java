package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class eventoVentana extends Application implements Initializable {


    @FXML
    private Button botonCrear;
    @FXML
    private TextField nombreEvento;
    @FXML
    private TextField descripcionEvento;
    @FXML
    private TextField fechaEvento;
    @FXML
    private TextField repeticiones;
    @FXML
    private ComboBox<String> alarmas;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


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
        String nombre = this.nombreEvento.getText();
        String descripcion = this.descripcionEvento.getText();
        String repeticion = this.repeticiones.getText();
        try {
            int repeticiones = Integer.parseInt(repeticion);
        } catch (NumberFormatException e1) {
            this.lanzarVentanaError();
            return;
        }
        if (nombre.equals("") || descripcion.equals("")) {
            this.lanzarVentanaError();
            return;
        }
        try {
            LocalDateTime fecha = LocalDateTime.parse(this.fechaEvento.toString(), formatter);
        } catch (DateTimeParseException e4) {
            this.lanzarVentanaError();
            return;
        }
        this.alarmas.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] valoresPosibles = {"Sí", "No"};
        this.alarmas.getItems().addAll(valoresPosibles);
    }

    private void lanzarVentanaError() {
        try {
            new errorVentana().start(new Stage());
        } catch (Exception e5) {
            //
        }
    }
}
