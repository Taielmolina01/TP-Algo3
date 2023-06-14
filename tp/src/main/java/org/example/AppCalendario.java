package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Actividades.Actividad;
import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;
import org.example.VentanasAuxiliares.VentanaCrearEvento;
import org.example.VentanasAuxiliares.VentanaCrearTarea;
import org.example.VentanasAuxiliares.VentanaMostrarInfoCompleta;
import org.example.VentanasAuxiliares.VentanaMostrarNotificacionAlarma;
import org.example.Visitadores.VisitadorActividades;
import org.example.VistaActividades.CeldaListaActividades;
import org.example.VistaActividades.VistaActividad;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class AppCalendario extends Application implements InterfazGuardarActividadNueva, Initializable, InterfazCambioEstado {
    private final String[] valoresCrear = new String[]{"", "Evento", "Tarea"};
    protected ManejadorGuardado manejador;
    protected Calendario calendario;
    private VisitadorActividades visitador;
    @FXML
    private Text lapsoTiempoActual;
    @FXML
    private ChoiceBox<String> rangoDeTiempo;
    @FXML
    private ComboBox<String> cajaCrear;
    @FXML
    private ListView<VistaActividad> listViewActividades;
    private LocalDateTime fechaActual;
    private LocalDateTime inicioSemana;
    private LocalDateTime finSemana;
    private HashMap<String, String> meses;
    private ObservableList<VistaActividad> vistaActividadesActuales;
    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/escenaCalendario.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Calendario Molina-Kriger");
        stage.getIcons().add(new Image(AppCalendario.class.getClassLoader().getResource("logo.png").toExternalForm()));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnHidden(e -> Platform.exit());
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
        this.rangoDeTiempo.getItems().addAll("Dia", "Semana", "Mes");
        this.cajaCrear.getItems().addAll(this.valoresCrear);
        this.lapsoTiempoActual.setText(this.textoMensual);
        this.rangoDeTiempo.setOnAction(this::actualizarInfoRangoDeTiempo);
        this.cajaCrear.setOnAction(this::crearVentanaActividad);
        this.listViewActividades.getSelectionModel().selectedItemProperty().addListener(this::mostrarInfoCeldaSeleccionada);
        this.visitador = new VisitadorActividades();
        this.vistaActividadesActuales = FXCollections.observableArrayList();
        this.listViewActividades.setCellFactory(listViewActividades -> new CeldaListaActividades(this));
        this.actualizarListaActividades();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                LocalDateTime horaActual = LocalDateTime.now();
                AbstractMap.SimpleEntry<Integer, Alarma> parActividadAlarma = calendario.obtenerSiguienteAlarmaPorActividad
                        (horaActual, horaActual.plusDays(3));
                if (parActividadAlarma != null && parActividadAlarma.getValue().
                        cuantoFaltaParaDisparar(horaActual).compareTo(Duration.ofMillis(10)) < 0) {
                    VentanaMostrarNotificacionAlarma.lanzarVentanaNotificacion(calendario.obtenerNombre(parActividadAlarma.getKey()));
                }
            }
        };
        timer.start();
    }

    // Interacción con la GUI


    @FXML
    public void clickEnBotonIzquierda() {
        switch (this.rangoDeTiempo.getValue()) {
            case "Dia" -> this.fechaActual = this.fechaActual.minusDays(1);
            case "Semana" -> this.fechaActual = this.fechaActual.minusWeeks(1);
            default -> this.fechaActual = this.fechaActual.minusMonths(1);
        }
        this.actualizarTextoYLista();
    }

    @FXML
    public void clickEnBotonDerecha() {
        switch (this.rangoDeTiempo.getValue()) {
            case "Dia" -> this.fechaActual = this.fechaActual.plusDays(1);
            case "Semana" -> this.fechaActual = this.fechaActual.plusWeeks(1);
            default -> this.fechaActual = this.fechaActual.plusMonths(1);
        }
        this.actualizarTextoYLista();
    }

    private void crearVentanaActividad(ActionEvent event) {
        String tipoElemento = this.cajaCrear.getValue();
        if (tipoElemento.equals(this.valoresCrear[1])) {
            try {
                new VentanaCrearEvento(this).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (tipoElemento.equals(this.valoresCrear[2])) {
            try {
                new VentanaCrearTarea(this).start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void mostrarInfoCeldaSeleccionada(Observable Observable) {
        int indice = this.listViewActividades.getSelectionModel().getSelectedIndex();
        String infoCompletaSeleccionado = this.vistaActividadesActuales.get(indice).obtenerInfoCompleta();
        try {
            VentanaMostrarInfoCompleta v = new VentanaMostrarInfoCompleta();
            v.start(infoCompletaSeleccionado);
        } catch (Exception e) {
            //
        }
    }

    private void actualizarInfoRangoDeTiempo(ActionEvent event) {
        this.actualizarTextoYLista();
    }

    // Actualización de datos de la GUI

    private void establecerInicioYFinSemana() {
        int diaSemanaActual = this.fechaActual.getDayOfWeek().getValue();
        int aRestar = diaSemanaActual - 1;
        int aSumar = 7 - diaSemanaActual;
        this.inicioSemana = this.fechaActual.minusDays(aRestar).with(LocalTime.MIN);
        this.finSemana = this.fechaActual.plusDays(aSumar).with(LocalTime.MAX);
    }

    private void actualizarTextoYLista() {
        this.establecerInicioYFinSemana();
        this.establecerText();
        String texto;
        switch (this.rangoDeTiempo.getValue()) {
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
        switch (this.rangoDeTiempo.getValue()) {
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

    private void crearLista(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.listViewActividades.getSelectionModel().clearSelection();
        this.listViewActividades.getItems().clear();
        ArrayList<Actividad> actividadesActuales = this.calendario.obtenerActividadesEntreFechas(fechaInicio, fechaFin);
        actividadesActuales.sort(Comparator.comparing(Actividad::obtenerFechaInicio).thenComparing(Actividad::obtenerNombre));
        this.vistaActividadesActuales = FXCollections.observableArrayList(this.visitador.visitarActividades(actividadesActuales));
        this.listViewActividades.getItems().addAll(this.vistaActividadesActuales);
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
            this.textoSemanal = this.establecerTextoSemanal();
        }
    }

    private String establecerTextoDiario() {
        return this.fechaActual.getDayOfMonth() + " " + this.meses.get(this.fechaActual.getMonth().toString()).toLowerCase()
                + " " + this.fechaActual.getYear();
    }

    private String establecerTextoMensual() {
        return this.meses.get(this.fechaActual.getMonth().toString()) + " " + this.fechaActual.getYear();
    }

    private String establecerTextoSemanal() {
        return this.inicioSemana.getDayOfMonth() + " - " + this.finSemana.getDayOfMonth() + " " +
                this.meses.get(this.fechaActual.getMonth().toString()).toLowerCase() + " " + this.inicioSemana.getYear();
    }

    private String establecerTextoSemanalDistintosAnios() {
        return this.inicioSemana.getDayOfMonth() + " " + this.meses.get(this.inicioSemana.getMonth().toString()).toLowerCase() + " " +
                this.inicioSemana.getYear() + " - " + this.finSemana.getDayOfMonth() + " " +
                this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
    }

    private String establecerTextoSemanalDistintosMeses() {
        return this.inicioSemana.getDayOfMonth() + " " + this.meses.get(this.inicioSemana.getMonth().toString()).toLowerCase() + " - " +
                +this.finSemana.getDayOfMonth() + " " +
                this.meses.get(this.finSemana.getMonth().toString()).toLowerCase() + " " + this.finSemana.getYear();
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

    // Manejo del guardado de nuevas actividades y nuevos estados de las ya existentes.


    public void guardarEstadoActual() {
        try {
            this.calendario.guardarEstado(this.manejador);
        } catch (IOException e) {
            //
        }
    }

    @Override
    public void guardarEventoSinRepeticion(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                                           ArrayList<Duration> duracionesAlarmas) {
        int ID = this.calendario.crearEvento(nombre, descripcion, fechaInicio, duracion, diaCompleto);
        this.guardarNuevaActividad(ID, duracionesAlarmas);
    }

    @Override
    public void guardarEventoConRepeticion(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                                           LocalDateTime fechaFinal, Frecuencia frecuencia, ArrayList<Duration> duracionesAlarmas) {
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
        this.guardarEstadoActual();
    }
}