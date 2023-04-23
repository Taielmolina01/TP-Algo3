package org.example;

import java.time.*;
import java.lang.String;
import java.util.ArrayList;

public class Evento extends ElementoCalendario {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Frecuencia frecuencia;
    private int ocurrencias;

    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.modificarDuracion(duracion);

        this.modificarFrecuencia(new FrecuenciaDiaria(0));
        this.modificarOcurrencias(0);
        this.calcularFechaFinDefinitivo(this.fechaFin);
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, LocalDateTime fechaFinalDefinitivo, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.modificarDuracion(duracion);

        this.modificarFrecuencia(frecuencia);
        this.modificarOcurrencias(0);
        this.calcularFechaFinDefinitivo(fechaFinalDefinitivo);
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.modificarDuracion(duracion);

        this.modificarFrecuencia(frecuencia);
        this.modificarOcurrencias(ocurrencias);
    }

    public void modificarDuracion(Duration duracion) {
        if (this.todoElDia) {
            duracion = Duration.ofHours(23).plusMinutes(59).plusSeconds(59);
        }
        this.duracion = duracion;
        this.fechaFin = this.fechaInicio.plus(this.duracion);
    }

    public void modificarOcurrencias(Integer ocurrencias) {
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo(this.fechaInicio);
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        this.calcularFechaFinDefinitivo(this.fechaInicio);
    }

    private void calcularFechaFinDefinitivo(LocalDateTime fecha) {
        for (int i = 1; i < this.ocurrencias; i++) {
            fecha = this.frecuencia.obtenerProximaFecha(fecha);
        }
        this.fechaFinalDefinitivo = fecha;
    }

    public Duration obtenerDuracion() { return this.duracion; }

    public LocalDateTime obtenerFechaFinal() { return this.fechaFinalDefinitivo; }

    public Frecuencia obtenerFrecuencia() { return this.frecuencia; }

    public ArrayList<LocalDateTime> eventosHastaFecha(LocalDateTime fechaFinal) {
        LocalDateTime fecha = this.fechaInicio;
        var eventos = new ArrayList<LocalDateTime>();
        while (estaEntreFechas(fecha, this.fechaInicio, this.fechaFinalDefinitivo) && estaEntreFechas(fecha, this.fechaInicio, fechaFinal)) {
            eventos.add(fecha);
            fecha = this.frecuencia.obtenerProximaFecha(fecha);
        }
        return eventos;
    }

    public boolean hayEvento(LocalDateTime fecha) {
        ArrayList<LocalDateTime> eventos = eventosHastaFecha(fecha);
        LocalDateTime ultimaFechaEncontradaInicio = eventos.get(eventos.size()-1);
        LocalDateTime ultimaFechaEncontradaFinal = ultimaFechaEncontradaInicio.plus(this.duracion);
        return this.estaEntreFechas(fecha, ultimaFechaEncontradaInicio, ultimaFechaEncontradaFinal);
    }

    private boolean estaEntreFechas(LocalDateTime fecha, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return (fecha.equals(fechaInicio) || fecha.isAfter(fechaInicio)) && (fecha.isBefore(fechaFinal) || fecha.isEqual(fechaFinal));
    }

}