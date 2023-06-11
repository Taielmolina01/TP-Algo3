package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Actividades.Actividad;
import org.example.Alarma.Alarma;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.VentanasAuxiliares.eventoVentana;
import org.example.VentanasAuxiliares.infoCompletaVentana;
import org.example.VentanasAuxiliares.tareaVentana;
import org.example.Visitadores.visitadorActividades;
import org.example.VistaActividades.manejadorCeldasListView;
import org.example.VistaActividades.vistaActividad;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Main extends Application implements interfazGuardarActividadNueva, Initializable, interfazCambioEstado {
    private visitadorActividades visitador;
    protected ManejadorGuardado manejador;
    protected Calendario calendario;
    @FXML
    private Text lapsoTiempoActual;
    @FXML
    private ChoiceBox<String> rangoTiempo;
    @FXML
    private ComboBox<String> cajaCrear;
    @FXML
    private ListView<vistaActividad> listViewActividades; // cambiar a listview de vistaActividad xd
    private LocalDateTime fechaActual;
    private LocalDateTime inicioSemana;
    private LocalDateTime finSemana;
    private HashMap<String, String> meses;
    private ObservableList<vistaActividad> vistaActividadesActuales;
    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;
    private final String[] valoresCrear = new String[]{"", "Evento", "Tarea"};
    private AnimationTimer timer;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaCalendario.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calendario Molina-Kriger");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    public void clickEnBotonIzquierda() {
        switch (this.rangoTiempo.getValue()) {
            case "Dia" -> this.fechaActual = this.fechaActual.minusDays(1);
            case "Semana" -> this.fechaActual = this.fechaActual.minusWeeks(1);
            default -> this.fechaActual = this.fechaActual.minusMonths(1);
        }
        this.actualizarTextoYDemas();
    }

    @FXML
    public void clickEnBotonDerecha() {
        switch (this.rangoTiempo.getValue()) {
            case "Dia" -> this.fechaActual = this.fechaActual.plusDays(1);
            case "Semana" -> this.fechaActual = this.fechaActual.plusWeeks(1);
            default -> this.fechaActual = this.fechaActual.plusMonths(1);
        }
        this.actualizarTextoYDemas();
    }

    private void actualizarTextoYDemas() {
        this.establecerInicioYFinSemana();
        this.establecerText();
        String texto;
        switch (this.rangoTiempo.getValue()) {
            case "Dia" -> texto = this.textoDiario;
            case "Semana" -> texto = this.textoSemanal;
            default -> texto = this.textoMensual;
        }
        this.lapsoTiempoActual.setText(texto);
        this.actualizarListaActividades();
    }

    private void actualizarListaActividades() {
        LocalDateTime fechaInicio;
        LocalDateTime fechaLimite;
        switch (this.rangoTiempo.getValue()) {
            case "Dia" -> {
                fechaInicio = this.fechaActual.with(LocalTime.MIN);
                fechaLimite = this.fechaActual.with(LocalTime.MAX);
                this.crearLista(fechaInicio, fechaLimite);
            }
            case "Semana" -> this.crearLista(this.inicioSemana, this.finSemana);
            default -> {
                fechaInicio = this.fechaActual.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                fechaLimite = this.fechaActual.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                this.crearLista(fechaInicio, fechaLimite);
            }
        }
    }

    private void crearLista(LocalDateTime fechaInicio, LocalDateTime fechaFin) { // esta funcion de mierda es la que funciona mal
        this.listViewActividades.getItems().clear();
        ArrayList<Actividad> actividadesActuales = this.calendario.obtenerActividadesEntreFechas(fechaInicio, fechaFin);
        actividadesActuales.sort(Comparator.comparing(Actividad::obtenerFechaInicio).thenComparing(Actividad::obtenerNombre));
        this.vistaActividadesActuales = FXCollections.observableArrayList(this.visitador.visitarActividades(actividadesActuales));
        this.listViewActividades.getItems().addAll(this.vistaActividadesActuales);
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
        this.manejador = new ManejadorGuardado(System.out);
        try {
            this.calendario = new Calendario().recuperarEstado(this.manejador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.establecerMeses();
        this.establecerInicioYFinSemana();
        this.establecerText();
        this.rangoTiempo.getItems().addAll("Dia", "Semana", "Mes");
        this.cajaCrear.getItems().addAll(this.valoresCrear);
        this.lapsoTiempoActual.setText(this.textoMensual);
        this.rangoTiempo.setOnAction(this::actualizarRango);
        this.cajaCrear.setOnAction(this::crearVentanaActividad);
        this.listViewActividades.getSelectionModel().selectedItemProperty().addListener(this::cambioSeleccion);
        this.visitador = new visitadorActividades();
        this.listViewActividades.setCellFactory(param -> new manejadorCeldasListView(FXCollections.observableArrayList(this.vistaActividadesActuales),
                        this));
        this.actualizarListaActividades();
        /*
        this.timer = (AnimationTimer) (l) -> {
                Alarma a = this.calendario.obtenerSiguienteAlarma(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
                if (a != null && a.cuantoFaltaParaDisparar(LocalDateTime.now()).compareTo(Duration.ofMinutes(3)) < 0) {
                    notificacionVentana.lanzarVentanaNotificacion();
                }
        };
         */
        //timer.start();
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
        // podria simplemente llamar actualizarTextoYDemas
        this.establecerText();
        String texto;
        switch (this.rangoTiempo.getValue()) {
            case "Dia" -> texto = this.textoDiario;
            case "Semana" -> texto = this.textoSemanal;
            default -> texto = this.textoMensual;
        }
        this.lapsoTiempoActual.setText(texto);
        this.actualizarListaActividades();
    }

    private void crearVentanaActividad(ActionEvent event) {
        String tipoElemento = this.cajaCrear.getValue();
        if (tipoElemento.equals(this.valoresCrear[1])) {
            try {
                new eventoVentana(this).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (tipoElemento.equals(this.valoresCrear[2])) {
            try {
                new tareaVentana(this).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void guardarEstadoActual() {
        try {
            this.calendario.guardarEstado(this.manejador);
        } catch (IOException e) {
            //
        }
    }

    private void establecerInicioYFinSemana() {
        int diaSemanaActual = this.fechaActual.getDayOfWeek().getValue();
        int aRestar = diaSemanaActual - 1;
        int aSumar = 7 - diaSemanaActual;
        this.inicioSemana = this.fechaActual.minusDays(aRestar).with(LocalTime.MIN);
        this.finSemana = this.fechaActual.plusDays(aSumar).with(LocalTime.MAX);
    }

    private void cambioSeleccion(Observable Observable) {
        int indice = this.listViewActividades.getSelectionModel().getSelectedIndex();
        String infoCompletaSeleccionado = this.vistaActividadesActuales.get(indice).obtenerInfoCompleta();
        try {
            infoCompletaVentana v = new infoCompletaVentana();
            v.start(infoCompletaSeleccionado);
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void guardarEventoTipo1(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                                   ArrayList<Duration> duracionesAlarmas) {
        int ID = this.calendario.crearEvento(nombre, descripcion, fechaInicio, duracion, diaCompleto);
        this.guardarNuevaActividad(ID, duracionesAlarmas);
    }

    @Override
    public void guardarEventoTipo2(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                                   LocalDateTime fechaFinal, FrecuenciaDiaria frecuencia, ArrayList<Duration> duracionesAlarmas) {
        int ID = this.calendario.crearEvento(nombre, descripcion, fechaInicio, duracion, diaCompleto, fechaFinal, frecuencia);
        this.guardarNuevaActividad(ID, duracionesAlarmas);
    }

    @Override
    public void guardarTarea(String nombre, String descripcion, LocalDateTime fechaInicio, boolean diaCompleto,
                             ArrayList<Duration> duracionesAlarmas) {
        int ID = this.calendario.crearTarea(nombre, descripcion, fechaInicio, diaCompleto);
        this.guardarNuevaActividad(ID, duracionesAlarmas);
    }

    private void guardarNuevaActividad(int ID, ArrayList<Duration> duracionesAlarmas) {
        this.agregarAlarmas(ID, duracionesAlarmas);
        this.guardarEstadoActual();
        this.actualizarListaActividades();
    }

    private void agregarAlarmas(int ID, ArrayList<Duration> duracionesAlarmas) {
        if (duracionesAlarmas != null) {
            for (Duration duracion : duracionesAlarmas) {
                this.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
            }
        }
    }

    @Override
    public void huboCambioEstadoTarea(int i) {
        this.calendario.cambiarEstadoTarea(this.vistaActividadesActuales.get(i).obtenerIDActividad());
        this.guardarEstadoActual();
    }
}