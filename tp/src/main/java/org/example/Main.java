package org.example;

import javafx.application.Application;
import javafx.beans.Observable;
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
import org.example.VentanasAuxiliares.notificacionVentana;
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
import java.util.*;

public class Main extends Application implements interfazGuardado, Initializable {
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
    private ListView<String> listViewActividades; // cambiar a listview de vistaActividad xd
    private LocalDateTime fechaActual;
    private LocalDateTime inicioSemana;
    private LocalDateTime finSemana;
    private HashMap<String, String> meses;
    private ArrayList<vistaActividad> vistaActividadesActuales;
    private String textoDiario;
    private String textoSemanal;
    private String textoMensual;
    private String[] valoresRango;
    private String[] valoresCrear;

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
        String rangoActual = this.rangoTiempo.getValue();
        if (rangoActual.equals(this.valoresRango[0])) {
            this.fechaActual = this.fechaActual.minusDays(1);
        } else if (rangoActual.equals(this.valoresRango[1])) {
            this.fechaActual = this.fechaActual.minusWeeks(1);
        } else {
            this.fechaActual = this.fechaActual.minusMonths(1);
        }
        this.actualizarTextoYDemas(rangoActual);
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
        this.actualizarTextoYDemas(rangoActual);
    }

    private void actualizarTextoYDemas(String rangoActual) {
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
        this.actualizarListaActividades();
    }

    private void actualizarListaActividades() {
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

    private void crearLista(LocalDateTime fechaInicio, LocalDateTime fechaFin) { // esta funcion de mierda es la que funciona mal
        this.listViewActividades.getItems().clear();
        ArrayList<Actividad> actividadesActuales = this.calendario.obtenerActividadesEntreFechas(fechaInicio, fechaFin);
        actividadesActuales.sort(Comparator.comparing(Actividad::obtenerFechaInicio).thenComparing(Actividad::obtenerNombre));
        this.vistaActividadesActuales = this.visitador.visitarActividades(actividadesActuales);
        this.listViewActividades.setCellFactory(param -> new manejadorCeldasListView(this.vistaActividadesActuales));
        // deberia actualizar la lista del manejador de celdas, pero por alguna razon me lanza un excepcion cuando lo intento hacer
        for (int i = 0; i < vistaActividadesActuales.size(); i++) {
            this.listViewActividades.getItems().add(this.vistaActividadesActuales.get(i).obtenerInfoResumida());
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
        this.manejador = new ManejadorGuardado(System.out);
        try {
            this.calendario = new Calendario().recuperarEstado(this.manejador);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.establecerMeses();
        this.establecerInicioYFinSemana();
        this.establecerText();
        this.valoresRango = new String[]{"Dia", "Semana", "Mes"};
        this.valoresCrear = new String[]{"", "Evento", "Tarea"};
        this.rangoTiempo.getItems().addAll(this.valoresRango);
        this.cajaCrear.getItems().addAll(this.valoresCrear);
        this.lapsoTiempoActual.setText(this.textoMensual);
        this.rangoTiempo.setOnAction(this::actualizarRango);
        this.cajaCrear.setOnAction(this::crearVentanaActividad);
        this.listViewActividades.getSelectionModel().selectedItemProperty().addListener(this::cambioSeleccion);
        this.visitador = new visitadorActividades();
        this.actualizarListaActividades();
        /*
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Alarma a = this.calendario.obtenerSiguienteAlarma(LocalDateTime.now());
                if (a != null && a.cuantoFaltaParaDisparar(LocalDateTime.now()).compareTo(Duration.ofMinutes(3)) < 0) {
                    notificacionVentana.lanzarVentanaNotificacion();
                }
            }
        }, 0, 30000);
         */
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

    public void guardar() {
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
        this.guardar();
        this.actualizarListaActividades();
    }

    private void agregarAlarmas(int ID, ArrayList<Duration> duracionesAlarmas) {
        if (duracionesAlarmas != null) {
            for (Duration duracion : duracionesAlarmas) {
                this.calendario.agregarAlarma(ID, Alarma.Efecto.NOTIFICACION, duracion);
            }
        }
    }
}