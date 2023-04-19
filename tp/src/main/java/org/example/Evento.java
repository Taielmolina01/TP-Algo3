package org.example;

import java.time.*;
import java.lang.String;
import java.util.ArrayList;

public class Evento extends elementoCalendario {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer ocurrencias;
    private Integer ocurrenciasRealizadas;
    private Frecuencia frecuencia;

    private static LocalDateTime calcularFechaFin(LocalDateTime fechaInicio, Duration duracion) {
        return fechaInicio.plusHours(duracion.toHours());
    }

    private void calcularFechaFin() {
        this.fechaFin = calcularFechaFin(this.fechaInicio, this.duracion);
    }

    private void definirDuracion(Duration duracion) {
        if (this.todoElDia) {
            this.duracion = Duration.ofDays(1);
        } else {
            this.duracion = duracion;
        }
        this.calcularFechaFin();
    }

   /* public ArrayList<LocalDateTime> eventosHastaFecha(LocalDateTime fechaFinal) {
        LocalDateTime dia = this.fechaInicio;
        ArrayList<LocalDateTime> eventos = new ArrayList<>();
        while (dia.isBefore(this.fechaFinalDefinitivo) && (dia.isBefore(fechaFinal) || dia.isEqual(fechaFinal))) {
            eventos.add(dia);
            dia = this.frecuencia.getProximaFecha(dia);
        }
        return eventos;
    } */


    public boolean hayEvento(LocalDateTime diaAAnalizar) { // analizar si en base a eventosHastaFecha hay un evento en tal fecha.
        return true;
    }


    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalDefinitivo = this.fechaFin;
    }



    // Constructor si se repite el evento dada la fecha de fin, si es de duracion infinita se pasa
    // localdatetime.MAX.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia, LocalDateTime fechaFinalDefinitivo, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.frecuencia = frecuencia;
    }


    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia, Integer frecuenciaInteger) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.ocurrencias = ocurrencias;
        this.ocurrenciasRealizadas = 0;
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
    }

    private Boolean terminoDeRepetirse() { // cada vez que se repite habria que sumar +1 a ocurrenciasRealizadas
        return ocurrencias.equals(ocurrenciasRealizadas);
    }

    public void modificarDuracion(Duration duracion) {
        this.duracion = duracion;
        this.calcularFechaFin();
    }

    public Duration getDuracion() {
        return this.duracion;
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

    public LocalDateTime getFechaFinalDefinitivo() {
        return this.fechaFinalDefinitivo;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

}
