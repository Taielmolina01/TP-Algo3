package org.example;

import java.time.*;
import java.lang.String;
import java.util.ArrayList;

public class Evento extends elementoCalendario {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer ocurrencias;
    private Frecuencia frecuencia;

    // Constructores.

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalDefinitivo = this.fechaFin;
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, LocalDateTime fechaFinalDefinitivo, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.frecuencia = frecuencia;
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo();
    }

    // Métodos públicos.

    public ArrayList<LocalDateTime> eventosHastaFecha(LocalDateTime fechaFinal) {
        LocalDateTime dia = this.fechaInicio;
        ArrayList<LocalDateTime> eventos = new ArrayList<>();
        while (dia.isBefore(this.fechaFinalDefinitivo) && (dia.isBefore(fechaFinal) || dia.isEqual(fechaFinal))) {
            eventos.add(dia);
            dia = this.frecuencia.getProximaFecha(dia);
        }
        return eventos;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        ArrayList<LocalDateTime> eventos = eventosHastaFecha(diaAAnalizar);
        LocalDateTime ultimoDiaInicio = eventos.get(eventos.size()-1);
        Long[] duracionFormateada = this.formatearDuracion();
        LocalDateTime ultimoDiaFin = ultimoDiaInicio.plusHours(duracionFormateada[0]).plusMinutes(duracionFormateada[1]).plusSeconds(duracionFormateada[2]);
        return this.isBetween(diaAAnalizar, ultimoDiaInicio, ultimoDiaFin);
    }

    public void modificarDuracion(Duration duracion) {
        this.duracion = duracion;
        this.calcularFechaFin();
    }

    public void modificarOcurrencias(Integer ocurrencias) {
        if (this.ocurrencias != null) {
            this.ocurrencias = ocurrencias;
            this.calcularFechaFinDefinitivo();
        }
    }
    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        if (this.ocurrencias != null) {
            this.calcularFechaFinDefinitivo();
        }
    }

    public LocalDateTime getFechaFinal() { return this.fechaFinalDefinitivo; }

    public Duration getDuracion() {
        return this.duracion;
    }

    public Frecuencia getFrecuencia() {
        return this.frecuencia;
    }

    // Métodos privados.

    private void calcularFechaFinDefinitivo() {
        int ocurrenciasContadas = 0;
        LocalDateTime diaActual = this.fechaInicio;
        while (this.ocurrencias != ocurrenciasContadas) {
            diaActual = this.frecuencia.getProximaFecha(diaActual);
            ocurrenciasContadas++;
        }
        this.fechaFinalDefinitivo = diaActual;
    }

    private void calcularFechaFin() {
        Long[] duracionFormateada = this.formatearDuracion();
        this.fechaFin = this.fechaInicio.plusHours(duracionFormateada[0]).plusMinutes(duracionFormateada[1]).plusSeconds(duracionFormateada[2]);
    }

    private void definirDuracion(Duration duracion) {
        if (this.todoElDia) {
            this.duracion = Duration.ofHours(23).plusMinutes(59).plusSeconds(59);
        } else {
            this.duracion = duracion;
        }
        this.calcularFechaFin();
    }

    private Long[] formatearDuracion() {
        Long duracionHoras = this.duracion.toHours();
        Long duracionMinutos = this.duracion.minusHours(duracionHoras).toMinutes();
        Long duracionSegundos = this.duracion.minusHours(duracionHoras).minusMinutes(duracionMinutos).toSeconds();
        Long[] duracionFormateada = {duracionHoras , duracionMinutos, duracionSegundos};
        return duracionFormateada;
    }

    private boolean isBetween(LocalDateTime diaAAnalizar, LocalDateTime diaInicio, LocalDateTime diaFin) {
        return diaAAnalizar.equals(diaInicio) || diaAAnalizar.equals(diaFin) || (diaAAnalizar.isAfter(diaInicio) && diaAAnalizar.isBefore(diaFin));
    }

}
