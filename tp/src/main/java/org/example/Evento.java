package org.example;

import java.time.*;
import java.lang.String;
import java.util.ArrayList;

public class Evento extends elementoCalendario {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Frecuencia frecuencia;
    private int ocurrencias;

    // Constructores.

    // Constructor si no se repite el evento nunca.
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

    public Duration obtenerDuracion() { return this.duracion; }

    public LocalDateTime obtenerFechaFinal() { return this.fechaFinalDefinitivo; }

    public Frecuencia obtenerFrecuencia() { return this.frecuencia; }

    public ArrayList<LocalDateTime> eventosHastaFecha(LocalDateTime fechaFinal) { // podria ser desde un dia
        // de inicio distinto al dia inicial mientras sea igual o despues que la fecha inicial.
        LocalDateTime dia = this.fechaInicio;
        ArrayList<LocalDateTime> eventos = new ArrayList<>();
        while (dia.isBefore(this.fechaFinalDefinitivo) && (dia.isBefore(fechaFinal) || dia.isEqual(fechaFinal))) {
            eventos.add(dia);
            dia = this.frecuencia.obtenerProximaFecha(dia);
        }
        return eventos;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        ArrayList<LocalDateTime> eventos = eventosHastaFecha(diaAAnalizar);
        LocalDateTime ultimoDiaInicio = eventos.get(eventos.size()-1);
        LocalDateTime ultimoDiaFin = ultimoDiaInicio.plus(this.duracion);
        return this.isBetween(diaAAnalizar, ultimoDiaInicio, ultimoDiaFin);
    }

    private void calcularFechaFinDefinitivo(LocalDateTime diaActual) {
        for (int i = 0; i < this.ocurrencias; i++) {
            diaActual = this.frecuencia.obtenerProximaFecha(diaActual);
        }
        this.fechaFinalDefinitivo = diaActual;
    }

    private boolean isBetween(LocalDateTime diaAAnalizar, LocalDateTime diaInicio, LocalDateTime diaFin) {
        return diaAAnalizar.equals(diaInicio) || diaAAnalizar.equals(diaFin) || (diaAAnalizar.isAfter(diaInicio) && diaAAnalizar.isBefore(diaFin));
    }

}