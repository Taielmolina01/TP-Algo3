package org.example;

import java.time.*;
import java.lang.String;

public class Evento extends elementoCalendario{

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer repeticiones;
    private String[] frecuencia;

    private LocalDateTime calcularFechaFin(Duration duracion, LocalDateTime fechaInicio) {
        fechaFin = fechaInicio.plusHours(duracion.toHours());
        return fechaFin;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        if (diaAAnalizar.isBefore(this.fechaInicio) || diaAAnalizar.isAfter(this.fechaFinalDefinitivo)) {
            return false;
        } else {
            /*
                Puede haber evento porque está entre el rango en el que inicio el evento y cuando termina
                deberia analizar como hago para ir avanzando desde la fecha inicial hasta al fechaAAnalizar SIN PASARME,
                y devolver true si en la fechaAAnalizar hubo evento
            */
            String funcionAUtilizar = ""; // Ver q onda esto
            Long multiplicador = Long.parseLong("1");
            LocalDateTime dia = this.fechaInicio;
            while (dia.isBefore(this.fechaFinalDefinitivo)) {
                if (diaAAnalizar.isEqual(dia) || (diaAAnalizar.isAfter(dia) && diaAAnalizar.isBefore(this.calcularFechaFin(this.duracion, dia)))) { // deberia hacer que si diaAAnalizar isBetween dia y dia + duracion
                    return true;
                }
                if (funcionAUtilizar.equals("PD")) {
                    dia = dia.plusDays(multiplicador);
                }
                if (funcionAUtilizar.equals("PM")) {
                    dia = dia.plusMonths(multiplicador);
                } else {
                    dia = dia.plusYears(multiplicador);
                }
            }
            return false;
        }
    }


    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFin = this.calcularFechaFin(this.duracion, this.fechaInicio);
        this.fechaFinalDefinitivo = this.fechaFin;
    }

    private void definirDuracion(Duration duracion) {
        if (this.todoElDia) {
            this.duracion = Duration.ofDays(1);
        } else {
            this.duracion = duracion;
        }
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia, LocalDateTime fechaFinalDefinitivo, String[] frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia );
        this.definirDuracion(duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.frecuencia = frecuencia;
    }


    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  Boolean todoElDia, Integer ocurrencias, String[] frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia );
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
    }

    public void modificarDuracion(Duration duracion) {
        this.fechaFin = this.calcularFechaFin(duracion, this.fechaInicio);
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

}
