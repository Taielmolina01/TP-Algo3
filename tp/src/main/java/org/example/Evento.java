package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;

/*
    Los eventos pueden ser:
        - De día completo.
        - Comenzar en una fecha y hora y tener una duración arbitrarios. En ambos casos, el evento puede comenzar en un día y terminar en otro.

    Los eventos se pueden repetir:
        - Con frecuencia diaria, semanal, mensual o anual.
        - En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
        - En caso de frecuenia semanal, es posible definir los días de la semana (ej: “todos los martes y jueves”).
        - La repetición puede ser:
            + Infinita.
            + Terminar en una fecha determinada (ej: hasta el 13 de enero).
            + Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
        - Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repticiones.

    En un evento o tarea se pueden configurar una o más alarmas:
        - La alarma se dispara en un instante de tiempo, que se puede determinar de dos maneras:
             1. Una fecha y hora absoluta.
             2. Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).
        - El efecto producido al dispararse la alarma es configurable:
              1. Mostrar una notificación.
              2. Reproducir un sonido.
              3. Enviar un email.
              Nota: dado que en la primera etapa no se implementa la interacción con el usuario, no se deben implementar los
              efectos de las alarmas; pero sí deben tener pruebas asociadas.

*/
public class Evento {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String repeticion;

    private LocalTime duracion;

    private Integer repeticiones;
    private Integer frecuencia;

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.nombre = nombre; this.descripcion = descripcion; this.fechaInicio = fechaInicio;
        this.duracion = LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int horasDuracion = this.duracion.getHour();
        int minutosDuracion = this.duracion.getMinute();
        int segundosDuracion = this.duracion.getSecond();
        this.fechaFin = this.fechaInicio.plusHours(horasDuracion).plusMinutes(minutosDuracion).plusSeconds(segundosDuracion);
    }

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, String duracion, Integer frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFin = fechaFin;
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, String duracion, Integer frecuencia, Integer repeticiones) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFin = fechaFin;
        this.frecuencia = frecuencia;
        this.repeticiones = repeticiones;
    }

    public String getNombre() {
        return this.nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }

    public LocalDateTime getFechaInicio() {
        return this.fechaInicio;
    }

}
