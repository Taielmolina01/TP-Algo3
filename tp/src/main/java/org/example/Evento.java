package org.example;

import java.time.*;
import java.lang.String;

public class Evento extends elementoCalendario {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer ocurrencias;
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

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
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
                  Boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
    }

    public void modificarDuracion(Duration duracion) {
        this.duracion = duracion;
        this.calcularFechaFin();
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

}
