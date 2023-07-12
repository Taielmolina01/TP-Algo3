package org.example.Actividades;

import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;
import org.example.Visitadores.VisitadorActividades;
import org.example.Visitadores.VisitadorEventosFrecuencia;

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
        this.fechaInicio = this.frecuencia.definirFechaInicio(this.fechaInicio);
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
        this.ocurrencias = ocurrencias;
        this.fechaInicio = this.frecuencia.definirFechaInicio(this.fechaInicio);
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
        if (frecuencia == null) {
            if (this.estaEntreFechas(this.fechaInicio, fechaInicial, fechaFinal)) {
                eventos.add(this);
                return eventos;
            }
            return eventos;
        }
        if (!this.esFechaRepeticion(dia, fechaFinal)) {
            dia = this.pasarASiguienteFechaRepeticion(dia, fechaFinal);
            if (dia == null) {
                return eventos;
            }
        }
        HashMap<Integer, Duration> duraciones = this.calcularDuracionesRespectoAAlarmas();
        while (estaEntreFechas(dia, fechaInicial, this.fechaFinalRepeticion) && this.estaEntreFechas(dia, fechaInicial, fechaFinal)) {
            Evento clonEvento = (Evento) this.clonar();
            clonEvento.modificarFechaInicio(dia);
            clonEvento.alarmas = clonEvento.clonarAlarmas();
            for (var i : clonEvento.alarmas.keySet()) {
                Alarma alarmaVieja = this.alarmas.get(i);
                Alarma nuevaAlarma = new Alarma(alarmaVieja.dispararAlarma(), clonEvento.fechaInicio, duraciones.get(i));
                clonEvento.obtenerAlarmas().replace(i, nuevaAlarma);
            }
            eventos.add(clonEvento);
            dia = this.frecuencia.obtenerProximaFecha(dia);
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

    private ArrayList<LocalDateTime> obtenerFechasRepeticiones(LocalDateTime fechaFinal) {
        LocalDateTime dia = this.obtenerFechaInicio();
        ArrayList<LocalDateTime> repeticiones = new ArrayList<>();
        while (this.estaEntreFechas(dia, this.obtenerFechaInicio(), fechaFinal)) {
            repeticiones.add(dia);
            dia = this.frecuencia.obtenerProximaFecha(dia);
        }
        return repeticiones;
    }

    private boolean esFechaRepeticion(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        ArrayList<LocalDateTime> fechas = this.obtenerFechasRepeticiones(fechaFinal);
        for (var fecha : fechas) {
            if (this.estaEntreFechas(fechaInicial, fecha, fecha.plus(this.duracion))) {
                return true;
            }
        }
        return false;
    }

    private LocalDateTime pasarASiguienteFechaRepeticion(LocalDateTime dia, LocalDateTime fechaFinal) {
        ArrayList<LocalDateTime> fechas = this.obtenerFechasRepeticiones(fechaFinal);
        LocalDateTime diaADevolver = dia;
        for (int i = 0; i < fechas.size() - 1; i++) {
            if (dia.isAfter(fechas.get(i)) && dia.isBefore(fechas.get(i + 1))) {
                diaADevolver = fechas.get(i + 1);
            }
        }
        if (diaADevolver.isEqual(dia)) {
            return null;
        }
        return diaADevolver;
    }

    private HashMap<Integer, Duration> calcularDuracionesRespectoAAlarmas() {
        HashMap<Integer, Duration> duraciones = new HashMap<>();
        for (var i : this.obtenerAlarmas().keySet()) {
            var alarma = this.obtenerAlarma(i);
            Duration d = alarma.cuantoFaltaParaDisparar(this.obtenerFechaInicio()).abs();
            duraciones.put(i, d);
        }
        return duraciones;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        ArrayList<Actividad> eventos = this.actividadesEntreFechas(this.obtenerFechaInicio(), diaAAnalizar);
        LocalDateTime ultimoDiaInicio = eventos.get(eventos.size() - 1).obtenerFechaInicio();
        LocalDateTime ultimoDiaFin = ultimoDiaInicio.plus(this.duracion);
        return estaEntreFechas(diaAAnalizar, ultimoDiaInicio, ultimoDiaFin);
    }

    // Pasar esto a void
    @Override
    public void visitarActividad(VisitadorActividades v) {
        v.visitarActividad(this);
    }

    public void visitarFrecuencia(VisitadorEventosFrecuencia v) {
        if (this.frecuencia != null) {
            this.frecuencia.obtenerTipoFrecuencia(v);
        }
    }

}