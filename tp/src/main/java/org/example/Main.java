package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import org.example.ElementosCalendario.ElementoCalendario;
import javafx.stage.WindowEvent;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {


    @FXML
    private Button botonIzquierda;
    @FXML
    private Button botonDerecha;
    @FXML
    private Text faText;
    @FXML
    private ChoiceBox<String> rangoTiempo;

    private LocalDateTime fechaActual;
    private HashMap<String, String> meses;
    private Month mes;
    private int anio;
    private ManejadorGuardado manejador;
    private Calendario calendario;

    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;

    public Main() {
        this.manejador = new ManejadorGuardado(System.out);
        this.calendario = new Calendario().recuperarEstado(manejador);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escena.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calendario Molina-Kriger");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(this::guardarEstado);
    }

    public void guardarEstado(WindowEvent event) {
        System.out.println(this.calendario.elementosCalendario);
        this.calendario.guardarEstado(this.manejador);
    }

    @FXML
    public void clickEnBotonIzquierda() {
        if (this.rangoTiempo.getValue().equals("Dia")) {
            this.fechaActual = this.fechaActual.minusDays(1);
            this.establecerText();
            this.faText.setText(this.textoDiario);
        } else if (this.rangoTiempo.getValue().equals("Semana")) {
            this.fechaActual = this.fechaActual.minusWeeks(1);
        } else {
            this.fechaActual = this.fechaActual.minusMonths(1);
            this.establecerText();
            this.faText.setText(this.textoMensual);
        }
        this.actualizar();
    }

    @FXML
    public void clickEnBotonDerecha() {
        if (this.rangoTiempo.getValue().equals("Dia")) {
            this.fechaActual = this.fechaActual.plusDays(1);
            this.establecerText();
            this.faText.setText(this.textoDiario);
        } else if (this.rangoTiempo.getValue().equals("Semana")) {
            this.fechaActual = this.fechaActual.plusWeeks(1);
            this.establecerText();
        } else {
            this.fechaActual = this.fechaActual.plusMonths(1);
            this.establecerText();
            this.faText.setText(this.textoMensual);
        }
        this.actualizar();
    }

    private void actualizar() {
        this.mes = this.fechaActual.getMonth();
        this.anio = this.fechaActual.getYear();
        if (this.rangoTiempo.getValue().equals("Dia")) {
            LocalDateTime fechaSiguiente = this.fechaActual.plusDays(1);
            Integer anioNuevo = fechaSiguiente.getYear();
            Integer mesNuevo = fechaSiguiente.getMonth().getValue();
            LocalDateTime fechaInicio = LocalDateTime.of(this.fechaActual.getYear(), this.fechaActual.getMonth().getValue(),
                    this.fechaActual.getDayOfMonth(), 0, 0, 0);
            LocalDateTime fechaLimite = LocalDateTime.of(anioNuevo, mesNuevo, fechaSiguiente.getDayOfMonth(), 0, 0, 0).minusSeconds(1);

            StringBuilder aMostrar1 = new StringBuilder();
            for (ElementoCalendario elemento : this.calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaLimite)) {
                aMostrar1.append(elemento.toString());
            }
            String aMostrar = aMostrar1.toString();
        } else if (this.rangoTiempo.getValue().equals("Semana")) {

        } else {
            LocalDateTime fechaSiguiente = this.fechaActual.plusMonths(1);
            Integer anioNuevo = fechaSiguiente.getYear();
            Integer mesNuevo = fechaSiguiente.getMonth().getValue();
            LocalDateTime fechaInicio = LocalDateTime.of(this.fechaActual.getYear(), this.fechaActual.getMonth().getValue(),
                    1, 0, 0, 0);
            LocalDateTime fechaLimite = LocalDateTime.of(anioNuevo, mesNuevo, 1, 0, 0, 0).minusSeconds(1);

            StringBuilder aMostrar1 = new StringBuilder();
            for (ElementoCalendario elemento : this.calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaLimite)) {
                aMostrar1.append(elemento.toString());
            }
            String aMostrar = aMostrar1.toString();
        }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fechaActual = LocalDateTime.now();
        this.mes = this.fechaActual.getMonth();
        this.anio = this.fechaActual.getYear();
        this.establecerMeses();
        this.establecerText();
        String[] valores = {"Dia", "Semana", "Mes"};
        this.faText.setText(this.textoMensual);
        this.rangoTiempo.getItems().addAll(valores);
        this.rangoTiempo.setOnAction(this::actualizarRango);
    }

    private void establecerText() {
        this.textoDiario = this.fechaActual.getDayOfMonth() + " " + this.meses.get(this.fechaActual.getMonth().toString()).toLowerCase() + " " + this.fechaActual.getYear();
        this.textoMensual = this.meses.get(this.fechaActual.getMonth().toString()) + " " + this.fechaActual.getYear();
    }



    public void actualizarRango(ActionEvent event) {
        String valor = this.rangoTiempo.getValue();
        if (valor.equals("Dia")) {
            this.faText.setText(this.textoDiario);
        }
        if (valor.equals("Semana")) {
            // manejarme con los ordinals
        }
        if (valor.equals("Mes")) {
            this.faText.setText(this.textoMensual);
        }
        this.actualizar();
    }
}