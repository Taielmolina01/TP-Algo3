package org.example;

import java.lang.reflect.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.lang.String;
import java.util.ArrayList;

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
    private LocalDateTime fechaFin; // En principio es el fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.

    private LocalDateTime fechaFinalDefinitivo; // Es la fecha en la que terminan las repeticiones del evento

    private LocalTime duracion;

    private Integer repeticiones;
    private String frecuencia;

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.nombre = nombre; this.descripcion = descripcion; this.fechaInicio = fechaInicio;
        this.duracion = LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int hsDuracion = this.duracion.getHour(); int minsDuracion = this.duracion.getMinute(); int segsDuracion = this.duracion.getSecond();
        this.fechaFin = this.fechaInicio.plusHours(hsDuracion).plusMinutes(minsDuracion).plusSeconds(segsDuracion);
    }

    private void transformarFrecuencia(String frecuencia) {
        String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo"};
        ArrayList<String> diasFrecuencia = new ArrayList<String>();
        if (frecuencia.split(" ")[0].equals("cada")) {
            // Frecuencia diaria
            String cantDias = frecuencia.split(" ")[1];
            DayOfWeek diaDeLaSemana = this.fechaInicio.getDayOfWeek();
            String valor = dias[diaDeLaSemana.ordinal()];
            // Para qué guardarme todos los dias? si siempre van a caer en dias distintos
        }
        if (frecuencia.split(" ")[0].equals("todos")) {
            // Frecuencia semanal
            String frecuenciaSinComas = frecuencia.replace(",", " ").replace("  ", " ");
            for (String palabra : frecuenciaSinComas.split(" ")) {
                for (String dia : dias) {
                    if (palabra.equals(dia)) {
                        diasFrecuencia.add(palabra);
                    }
                }
            }
            // return diasFrecuencia;
        }
    }

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFinalDefinitivo, String duracion, String frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.transformarFrecuencia(frecuencia);
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, String frecuencia,
                  Integer repeticiones) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.transformarFrecuencia(frecuencia);
        // con el dato de repeticiones y la frecuencia debo poder calcular la fechaFinalDefinitiva
    }

    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    public void modificarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
