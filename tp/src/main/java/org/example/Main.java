package org.example;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ElementosCalendario.ElementoCalendario;
import org.example.Visitadores.visitadorElementosCalendario;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {

    @FXML
    private Text lapsoTiempoActual;
    @FXML
    private ChoiceBox<String> rangoTiempo;
    @FXML
    private ComboBox<String> cajaCrear;
    @FXML
    private ListView<String> listaEventosTareas;
    private LocalDateTime fechaActual;
    private LocalDateTime inicioSemana;
    private LocalDateTime finSemana;
    private HashMap<String, String> meses;
    private ArrayList<ArrayList<String>> infoElementosCalendarioActuales;
    private coloreadorCeldas coloreador;
    protected static ManejadorGuardado manejador = new ManejadorGuardado(System.out);
    protected static Calendario calendario = new Calendario();
    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;
    private String[] valoresRango;
    private String[] valoresCrear;
    private final visitadorElementosCalendario visitador = new visitadorElementosCalendario();
    public static DateTimeFormatter formatterConHoras = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static DateTimeFormatter formatterSinHoras = DateTimeFormatter.ofPattern("dd/MM/yyyy");


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
        String rangoActual = this.rangoTiempo.getValue();
        if (rangoActual.equals(this.valoresRango[0])) {
            this.fechaActual = this.fechaActual.minusDays(1);
        } else if (rangoActual.equals(this.valoresRango[1])) {
            this.fechaActual = this.fechaActual.minusWeeks(1);
        } else {
                this.fechaActual = this.fechaActual.minusMonths(1);
        }
        this.establecerInicioYFinSemana();
        this.establecerText();
        String texto;
        if (rangoActual.equals(this.valoresRango[0])) {
            texto = this.textoDiario;
        } else if (rangoActual.equals(this.valoresRango[1])) {
            texto = this.textoSemanal;
        } else {
            texto = this.textoMensual;
        }
        this.lapsoTiempoActual.setText(texto);
        this.actualizarListaEventosTareas();
    }

    @FXML
    public void clickEnBotonDerecha() {
        String rangoActual = this.rangoTiempo.getValue();
        if (rangoActual.equals(this.valoresRango[0])) {
            this.fechaActual = this.fechaActual.plusDays(1);
        } else if (rangoActual.equals(this.valoresRango[1])) {
            this.fechaActual = this.fechaActual.plusWeeks(1);
        } else {
            this.fechaActual = this.fechaActual.plusMonths(1);
        }
        this.establecerInicioYFinSemana();
        this.establecerText();
        String texto;
        if (rangoActual.equals(this.valoresRango[0])) {
            texto = this.textoDiario;
        } else if (rangoActual.equals(this.valoresRango[1])) {
            texto = this.textoSemanal;
        } else {
            texto = this.textoMensual;
        }
        this.lapsoTiempoActual.setText(texto);
        this.actualizarListaEventosTareas();
    }

    private void actualizarListaEventosTareas() {
        this.listaEventosTareas.getItems().clear();
        if (this.rangoTiempo.getValue().equals(this.valoresRango[0])) {
            LocalDateTime fechaInicio = this.fechaActual.with(LocalTime.MIN);
            LocalDateTime fechaLimite = this.fechaActual.with(LocalTime.MAX);
            this.crearLista(fechaInicio, fechaLimite);
        } else if (this.rangoTiempo.getValue().equals(this.valoresRango[1])) {
            this.crearLista(this.inicioSemana, this.finSemana);
        } else {
            LocalDateTime fechaInicio = this.fechaActual.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
            LocalDateTime fechaLimite = this.fechaActual.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
            this.crearLista(fechaInicio, fechaLimite);
        }
    }

    private void crearLista(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        ArrayList<ElementoCalendario> elementos = calendario.obtenerElementosCalendarioEntreFechas(fechaInicio, fechaFin);
        this.infoElementosCalendarioActuales = this.visitador.visitarElementos(elementos);
        this.listaEventosTareas.getItems().addAll(this.infoElementosCalendarioActuales.get(0));
        // la info completa la tengo que guardar en algun lado
        //this.coloreador.actualizarInfo(this.info);
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
        this.establecerMeses();
        this.establecerInicioYFinSemana();
        this.establecerText();
        this.valoresRango = new String[]{"Dia", "Semana", "Mes"};
        this.valoresCrear = new String[]{"", "Evento", "Tarea"};
        this.rangoTiempo.getItems().addAll(this.valoresRango);
        this.cajaCrear.getItems().addAll(this.valoresCrear);
        this.lapsoTiempoActual.setText(this.textoMensual);
        this.rangoTiempo.setOnAction(this::actualizarRango);
        this.cajaCrear.setOnAction(this::crearElementoCalendario);
        this.listaEventosTareas.getSelectionModel().selectedItemProperty().addListener(this::cambioSeleccion);
        this.actualizarListaEventosTareas();
        this.listaEventosTareas.setCellFactory(param -> new coloreadorCeldas(this.infoElementosCalendarioActuales));
    }

    private void establecerText() {
        this.textoDiario = this.establecerTextoDiario();
        this.textoMensual = this.establecerTextoMensual();
        if (this.inicioSemana.getMonth() != this.finSemana.getMonth()) {
            if (this.inicioSemana.getYear() == this.finSemana.getYear()) {
                this.textoSemanal = this.establecerTextoSemanalDistintosMeses();
            } else {
                this.textoSemanal = this.establecerTextoSemanalDistintosAnios();
            }
        } else {
            this.textoSemanal = this.establecerTextoMensual();
        }
    }

    private String establecerTextoDiario() {
        return this.fechaActual.getDayOfMonth() + " " + this.meses.get(this.fechaActual.getMonth().toString()).toLowerCase()
                + " " + this.fechaActual.getYear();
    }

    private String establecerTextoMensual() {
        return this.meses.get(this.fechaActual.getMonth().toString()) + " " + this.fechaActual.getYear();
    }

    private String establecerTextoSemanalDistintosAnios() {
        return this.meses.get(this.inicioSemana.getMonth().toString()) + " " + this.inicioSemana.getYear() + " - " +
                this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
    }

    private String establecerTextoSemanalDistintosMeses() {
        return this.meses.get(this.inicioSemana.getMonth().toString()) + " - " +
                this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
    }

    private void actualizarRango(ActionEvent event) {
        String valorRango = this.rangoTiempo.getValue();
        this.establecerText();
        String texto;
        if (valorRango.equals(this.valoresRango[0])) {
            texto = this.textoDiario;
        } else if (valorRango.equals(this.valoresRango[1])) {
            texto = this.textoSemanal;
        } else {
            texto = this.textoMensual;
        }
        this.lapsoTiempoActual.setText(texto);
        this.actualizarListaEventosTareas();
    }

    private void crearElementoCalendario(ActionEvent event) {
        String tipoElemento = this.cajaCrear.getValue();
        if (tipoElemento.equals(this.valoresCrear[1])) {
            try {
                new eventoVentana().start(new Stage());
            } catch (Exception e) {
                //
            }
        } else if (tipoElemento.equals(this.valoresCrear[2])) {
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

    public static void guardarEstado() throws IOException  {
            calendario.guardarEstado(manejador);
    }

    private void cambioSeleccion(Observable Observable) {
        int indice = this.listaEventosTareas.getSelectionModel().getSelectedIndex();
        System.out.println(this.infoElementosCalendarioActuales);
        /*
        String infoCompletaSeleccionado = this.infoElementosCalendarioActuales.get(1).get(indice);
        try {
            infoCompletaVentana ventana = new infoCompletaVentana();
            ventana.start(new Stage());
            ventana.pasarInformacion(infoCompletaSeleccionado);
        } catch (Exception e) {
            //
        } finally {
            this.listaEventosTareas.getSelectionModel().clearSelection();
        }
        */
    }
}