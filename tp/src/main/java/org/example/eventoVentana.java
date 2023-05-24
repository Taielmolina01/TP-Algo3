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
    private ComboBox<String> repeticion;
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
        try {
            LocalDateTime fecha = LocalDateTime.parse(this.fechaEvento.toString(), formatter);
        } catch (DateTimeParseException e) {
            // mensaje de error en la fecha
        }
        this.repeticion.getValue();
        this.alarmas.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] valoresPosibles = {"Sí", "No"};
        this.repeticion.getItems().addAll(valoresPosibles);
        this.alarmas.getItems().addAll(valoresPosibles);
    }
}
