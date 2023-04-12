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

    // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFin;


    // Fecha en la que terminan las repeticiones del evento.
    private LocalDateTime fechaFinalDefinitivo;

    private LocalTime duracion;

    private Integer repeticiones;
    private String frecuencia;

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.nombre = nombre; this.descripcion = descripcion; this.fechaInicio = fechaInicio;
        this.duracion = LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int hsDuracion = this.duracion.getHour(); int minsDuracion = this.duracion.getMinute(); int segsDuracion = this.duracion.getSecond();
        this.fechaFin = this.fechaInicio.plusHours(hsDuracion).plusMinutes(minsDuracion).plusSeconds(segsDuracion);
    }

    // Manejarme con un constructor con un Integer para decir cada cuantos dias y otro con strings que simplmente diga
    // los dias de la semana en los que se repite.

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
    }

    /*
        Los eventos se pueden repetir:
            - Con frecuencia diaria, semanal, mensual o anual.
            - En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
            - En caso de frecuenia semanal, es posible definir los días de la semana (ej: “todos los martes y jueves”).
            - La repetición puede ser:
                + Infinita.
                + Terminar en una fecha determinada (ej: hasta el 13 de enero).
                + Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
            - Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repeticiones.
    */

    // Si se repite infinitamente poner en fechaFinalDefinitivo como MAX de LocalDateTime (creo que es LocalDateTime.MAX)

    // Para ver si hay eventos antes o despues utilizar isAfter/isBefore

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, LocalDateTime fechaFinalDefinitivo,
                  String frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, String frecuencia,
                  Integer repeticiones) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        // con el dato de repeticiones y la frecuencia debo poder calcular la fechaFinalDefinitiva
    }


    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    public void modificarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*private void transformarFrecuencia(String frecuencia) { // Hace falta que la transforme?
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
    }*/


}
