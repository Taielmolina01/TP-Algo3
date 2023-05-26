package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import org.example.ElementosCalendario.ElementoCalendario;

import javafx.event.ActionEvent;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {

    @FXML
    private Text faText;
    @FXML
    private ChoiceBox<String> rangoTiempo;
    @FXML
    private ComboBox<String> cajaCrear;
    private LocalDateTime fechaActual;
    private LocalDateTime inicioSemana;
    private LocalDateTime finSemana;
    private HashMap<String, String> meses;
    private Month mes;
    private int anio;
    private static ManejadorGuardado manejador = new ManejadorGuardado(System.out);
    protected static Calendario calendario = new Calendario().recuperarEstado(manejador);
    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaCalendario.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calendario Molina-Kriger");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void clickEnBotonIzquierda() {
        String textoAActualizar;
        if (this.rangoTiempo.getValue().equals("Dia")) {
            this.fechaActual = this.fechaActual.minusDays(1);
            textoAActualizar = this.textoDiario;
        } else if (this.rangoTiempo.getValue().equals("Semana")) {
            this.fechaActual = this.fechaActual.minusWeeks(1);
            textoAActualizar = this.textoSemanal;
        } else {
            this.fechaActual = this.fechaActual.minusMonths(1);
            textoAActualizar = this.textoMensual;
        }
        this.establecerInicioYFinSemana();
        this.establecerText();
        this.faText.setText(textoAActualizar);
        this.actualizar();
    }

    @FXML
    public void clickEnBotonDerecha() {
        String textoAActualizar;
        if (this.rangoTiempo.getValue().equals("Dia")) {
            this.fechaActual = this.fechaActual.plusDays(1);
            textoAActualizar = this.textoDiario;
        } else if (this.rangoTiempo.getValue().equals("Semana")) {
            this.fechaActual = this.fechaActual.plusWeeks(1);
            textoAActualizar = this.textoSemanal;
        } else {
            this.fechaActual = this.fechaActual.plusMonths(1);
            textoAActualizar = this.textoMensual;
        }
        this.establecerInicioYFinSemana();
        this.establecerText();
        this.faText.setText(textoAActualizar);
        this.actualizar();
    }

    private void actualizar() {
        this.mes = this.fechaActual.getMonth();
        this.anio = this.fechaActual.getYear();
        if (this.rangoTiempo.getValue().equals("Dia")) {
            LocalDateTime fechaInicio = this.fechaActual.with(LocalTime.MIN);
            LocalDateTime fechaLimite = this.fechaActual.with(LocalTime.MAX);
            StringBuilder aMostrar1 = new StringBuilder();
            for (ElementoCalendario elemento : calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaLimite)) {
                aMostrar1.append(elemento.toString());
            }
            String aMostrar = aMostrar1.toString();
        } else if (this.rangoTiempo.getValue().equals("Semana")) {
            StringBuilder aMostrar1 = new StringBuilder();
            for (ElementoCalendario elemento : calendario.obtenerElementosCalendarioEntreFechas(this.inicioSemana, this.finSemana)) {
                aMostrar1.append(elemento.toString());
            }
            String aMostrar = aMostrar1.toString();
        } else {
            LocalDateTime fechaInicio = this.fechaActual.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
            LocalDateTime fechaLimite = this.fechaActual.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
            StringBuilder aMostrar1 = new StringBuilder();
            for (ElementoCalendario elemento : calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaLimite)) {
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
        this.establecerInicioYFinSemana();
        this.establecerText();
        String[] valoresRango = {"Dia", "Semana", "Mes"};
        String[] valoresCrear = {"", "Evento", "Tarea"};
        this.cajaCrear.getItems().addAll(valoresCrear);
        this.rangoTiempo.getItems().addAll(valoresRango);
        this.faText.setText(this.textoMensual);
        this.rangoTiempo.setOnAction(this::actualizarRango);
        this.cajaCrear.setOnAction(this::crearElementoCalendario);
    }
    private void establecerText() {
        this.textoDiario = this.fechaActual.getDayOfMonth() + " " + this.meses.get(this.fechaActual.getMonth().toString()).toLowerCase()
                + " " + this.fechaActual.getYear();
        this.textoMensual = this.meses.get(this.fechaActual.getMonth().toString()) + " " + this.fechaActual.getYear();
        if (this.inicioSemana.getMonth() != this.finSemana.getMonth()) {
            if (this.inicioSemana.getYear() == this.finSemana.getYear()) {
                this.textoSemanal = this.meses.get(this.inicioSemana.getMonth().toString()) + " - " +
                        this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
            } else {
                this.textoSemanal = this.meses.get(this.inicioSemana.getMonth().toString()) + " " + this.inicioSemana.getYear() + " - " +
                        this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
            }
        } else {
            this.textoSemanal = this.meses.get(this.inicioSemana.getMonth().toString()) + " " + this.inicioSemana.getYear();
        }
    }

    private void actualizarRango(ActionEvent event) {
        String valorRango = this.rangoTiempo.getValue();
        if (valorRango.equals("Dia")) {
            this.faText.setText(this.textoDiario);
        }
        if (valorRango.equals("Semana")) {
            this.faText.setText(this.textoSemanal);
        }
        if (valorRango.equals("Mes")) {
            this.faText.setText(this.textoMensual);
        }
        this.actualizar();
    }

    private void crearElementoCalendario(ActionEvent event) {
        String tipoElemento = this.cajaCrear.getValue();
        if (tipoElemento.equals("Evento")) {
            try {
                new eventoVentana().start(new Stage());
            } catch (Exception e) {
                //
            }
        } else if (tipoElemento.equals("Tarea")) {
            try {
                new tareaVentana().start(new Stage());
            } catch (Exception e) {
                //
            }
        }
    }

    public static void lanzarVentanaError() {
        try {
            new errorVentana().start(new Stage());
        } catch (Exception e5) {
            //
        }
    }

    protected static Duration formatearDuracion(String intervalo) {
        String[] formateado = intervalo.split(":");

        int horas;
        int minutos;
        int segundos;
        try {
            horas = Integer.parseInt(formateado[0]);
            minutos = Integer.parseInt(formateado[1]);
            segundos = Integer.parseInt(formateado[2]);
        } catch (NumberFormatException e1) {
            return null;
        }
        if (formateado.length != 3) {
            return null;
        }  else {
            return Duration.ofHours(horas).plusMinutes(minutos).plusSeconds(segundos);
        }
    }

    private void establecerInicioYFinSemana() {
        int diaSemanaActual = this.fechaActual.getDayOfWeek().getValue();
        int aRestar = diaSemanaActual - 1;
        int aSumar = 7 - diaSemanaActual;
        this.inicioSemana = this.fechaActual.minusDays(aRestar).with(LocalTime.MIN);
        this.finSemana = this.fechaActual.plusDays(aSumar).with(LocalTime.MAX);
    }
}