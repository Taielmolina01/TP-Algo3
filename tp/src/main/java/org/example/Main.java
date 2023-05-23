package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

public class Main extends Application {

    private Button botonIzquierda;
    private Button botonDerecha;
    private LocalDateTime fechaActual;
    private Stage myStage;
    private HashMap<String, String> meses;
    private Label label;
    private VBox contenedor;
    private Scene scene;
    private Month mes;
    private int anio;
    private Calendario calendario;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("escena.fxml"));
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


        /*this.myStage = stage;
        this.botonIzquierda = new Button();
        this.botonDerecha = new Button();

        this.myStage.setTitle("Calendario Molina-Kriger");
        this.calendario = new Calendario();
        this.fechaActual = LocalDateTime.now();
        this.establecerMeses();
        this.actualizarMes();

        var fechaMesSiguiente = this.fechaActual.plusMonths(1);
        var anioNuevo = fechaMesSiguiente.getYear();
        var mesNuevo = fechaMesSiguiente.getMonth().getValue();
        var fechaLimite = LocalDateTime.of(anioNuevo, mesNuevo, 1, 0, 0, 0).minusSeconds(1);
        var fechaInicio = LocalDateTime.of(this.fechaActual.getYear(), this.fechaActual.getMonth().getValue(), 1, 0, 0, 0);
        this.calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaLimite);
        */


    }

    private void crearEventosyTareas() {
        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 5, 1, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 5, 30, 0, 0, 0);

        String nombreEvento2 = "Evento2";
        String descripcionEvento2 = "descripcion del evento2";
        LocalDateTime fechaInicioEvento2 = LocalDateTime.of(2023, 6, 1, 0, 0, 0);


        String nombreTarea2 = "Tarea2";
        String descripcionTarea2 = "descripcion de la tarea2";
        LocalDateTime fechaInicioTarea2 = LocalDateTime.of(2023, 4, 30, 0, 0, 0);


        this.calendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        this.calendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);
        this.calendario.crearEvento(nombreEvento2, descripcionEvento2, fechaInicioEvento2, duracion, false);
        this.calendario.crearTarea(nombreTarea2, descripcionTarea2, fechaInicioTarea2, false);

    }

    private void clickEnBotonIzquierda() {
        this.fechaActual = this.fechaActual.minusMonths(1);
        this.actualizarMes();
    }

    private void clickEnBotonDerecha() {
        this.fechaActual = this.fechaActual.plusMonths(1);
        this.actualizarMes();
    }

    private void actualizarMes() {
        this.mes = this.fechaActual.getMonth();
        this.anio = this.fechaActual.getYear();
        this.label = new Label(this.meses.get(mes.toString()) + " " + String.valueOf(anio));
        this.contenedor = new VBox(this.label);
        this.scene = new Scene(contenedor, 640, 480);
        this.botonIzquierda.setOnAction(event -> this.clickEnBotonIzquierda());
        this.botonDerecha.setOnAction(event -> this.clickEnBotonDerecha());
        this.contenedor.getChildren().addAll(this.botonIzquierda, this.botonDerecha);
        this.contenedor.setAlignment(Pos.BASELINE_CENTER);
        this.contenedor.setSpacing(20);
        this.myStage.setScene(scene);
        this.myStage.show();
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