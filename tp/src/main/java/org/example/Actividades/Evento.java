package org.example.Actividades;

import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;
import org.example.Visitadores.visitadorActividades;
import org.example.Visitadores.visitadorEventosFrecuencia;
import org.example.VistaActividades.vistaActividad;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Evento extends Actividad implements Serializable, eventoClonable {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalRepeticion; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer ocurrencias;
    private Frecuencia frecuencia;

    // Constructores.

    // Constructor si no se repite el evento nunca.
    public Evento(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalRepeticion = this.fechaFin;
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalRepeticion = fechaFinalRepeticion;
        this.frecuencia = frecuencia;
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo();
    }

    public void modificarDuracion(Duration duracion) {
        this.duracion = duracion;
        this.calcularFechaFin();
    }

    public void modificarOcurrencias(Integer ocurrencias) {
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo();
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalRepeticion) {
        this.fechaFinalRepeticion = fechaFinalRepeticion;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        if (this.ocurrencias != null) {
            this.calcularFechaFinDefinitivo();
        }
    }

    private void calcularFechaFinDefinitivo() {
        LocalDateTime fecha = this.obtenerFechaInicio();
        for (int i = 1; i < this.ocurrencias; i++) {
            fecha = this.frecuencia.obtenerProximaFecha(fecha);
        }
        this.fechaFinalRepeticion = fecha;
    }

    private void calcularFechaFin() {
        this.fechaFin = this.obtenerFechaInicio().plus(this.duracion);
    }

    private void definirDuracion(Duration duracion) {
        if (this.obtenerTodoElDia()) {
            this.duracion = Duration.ofHours(23).plusMinutes(59).plusSeconds(59);
        } else {
            this.duracion = duracion;
        }
        this.calcularFechaFin();
    }

    public Duration obtenerDuracion() {
        return this.duracion;
    }

    public LocalDateTime obtenerFechaFinalDefinitivo() {
        return this.fechaFinalRepeticion;
    }

    public Frecuencia obtenerFrecuencia() {
        return this.frecuencia;
    }

    @Override
    public ArrayList<Actividad> actividadesEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        LocalDateTime dia = fechaInicial.isBefore(this.obtenerFechaInicio()) ? this.obtenerFechaInicio() : fechaInicial;
        ArrayList<Actividad> eventos = new ArrayList<>();
        if (fechaInicial.isAfter(this.fechaFinalRepeticion)) {
            return eventos;
        }
        while (estaEntreFechas(dia, fechaInicial, this.fechaFinalRepeticion) && estaEntreFechas(dia, fechaInicial, fechaFinal)) {
            System.out.println("Inicio");
            Evento clonEvento = (Evento) this.clonar();
            clonEvento.modificarFechaInicio(dia);
            System.out.println(dia);
            HashMap<Integer, Alarma> alarmas = this.obtenerAlarmas();
            if (clonEvento.obtenerFechaInicio() != this.obtenerFechaInicio()) {
                System.out.println("Entra al if");
                for (var i : alarmas.keySet()) {
                    Alarma a = alarmas.get(i);
                    System.out.println(a.obtenerFechaActivacion());
                    Duration d = a.cuantoFaltaParaDisparar(this.obtenerFechaInicio());
                    System.out.println("Duracion en horas: " + d.toHours());
                    Alarma aNueva = new Alarma(a.dispararAlarma(), clonEvento.obtenerFechaInicio(), d);
                    System.out.println("Hora que se activa antes de reemplazar en el clon: " + aNueva.obtenerFechaActivacion());
                    System.out.println("Tamaño alarmas del clon antes de ingresar: " + clonEvento.obtenerAlarmas().size());
                    clonEvento.obtenerAlarmas().replace(i, aNueva);
                    System.out.println("Tamaño alarmas del clon post de ingresar: " + clonEvento.obtenerAlarmas().size());
                    System.out.println("Hora que se activa post de reemplazar en el clon: " + clonEvento.obtenerAlarma(0).obtenerFechaActivacion());
                }
            }
            eventos.add(clonEvento);
            dia = this.frecuencia.obtenerProximaFecha(dia);
            System.out.println("Pasa al siguiente");
            System.out.println("");
        }
        return eventos;
    }
    
    public eventoClonable clonar() {
        Evento clonEvento = null;
        try {
            clonEvento = (Evento) clone();
        } catch (CloneNotSupportedException e) {
            //
        }
        return clonEvento;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        ArrayList<Actividad> eventos = actividadesEntreFechas(this.obtenerFechaInicio(), diaAAnalizar);
        LocalDateTime ultimoDiaInicio = eventos.get(eventos.size() - 1).obtenerFechaInicio();
        LocalDateTime ultimoDiaFin = ultimoDiaInicio.plus(this.duracion);
        return estaEntreFechas(diaAAnalizar, ultimoDiaInicio, ultimoDiaFin);
    }

    @Override
    public vistaActividad visitarActividad(visitadorActividades v) {
        return v.visitarActividad(this);
    }

    public String visitarFrecuencia(visitadorEventosFrecuencia v) {
        if (this.frecuencia != null) {
            return this.frecuencia.obtenerTipoFrecuencia(v);
        } else {
            return "";
        }
    }
}