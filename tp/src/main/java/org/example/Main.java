package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

public class Main extends Application {


    @FXML
    private Button bizq;
    @FXML
    private Button bder;

    private HashMap<String, String> meses;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tp/src/main/resources/escena.fxml"));
        loader.setController(this);
        VBox contenedor = loader.load();

        stage.setTitle("Calendario Molina-Kriger");
        Calendario calendario = new Calendario();
        LocalDateTime fechaActual = LocalDateTime.now();
        this.establecerMeses();
        int anio = fechaActual.getYear();
        Month mes = fechaActual.getMonth();
        var label = new Label(this.meses.get(mes.toString()) + " " + String.valueOf(anio));
        var scene = new Scene(contenedor, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void establecerMeses() {
        this.meses = new HashMap<>();
        this.meses.put("JANUARY", "Enero");
        this.meses.put("FEBRUARY", "Febrero");
        this.meses.put("MARCH", "Marzo");
        this.meses.put("APRIL", "Abril");
        this.meses.put("MAY", "Mayo");
        this.meses.put("JUNE", "Junio");
        this.meses.put("JULY", "Julio");
        this.meses.put("AUGUST", "Agosto");
        this.meses.put("SEPTEMBER", "Septiembre");
        this.meses.put("OCTOBER", "Octubre");
        this.meses.put("NOVEMBER", "Noviembre");
        this.meses.put("DECEMBER", "Diciembre");
    }

}