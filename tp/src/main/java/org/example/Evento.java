package org.example;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.lang.String;

public class Evento {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.

    private LocalDateTime fechaFinalDefinitivo; // Fecha en la que terminan las repeticiones del evento.
    private LocalTime duracion;
    private Integer repeticiones;
    private String frecuencia;

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.nombre = nombre; this.descripcion = descripcion; this.fechaInicio = fechaInicio;
        this.calcularFechaFin(duracion);
    }

    private void calcularFechaFin(String duracion) {
        this.duracion = LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int hsDuracion = this.duracion.getHour(); int minsDuracion = this.duracion.getMinute(); int segsDuracion = this.duracion.getSecond();
        this.fechaFin = this.fechaInicio.plusHours(hsDuracion).plusMinutes(minsDuracion).plusSeconds(segsDuracion);
    }

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = this.fechaFin;
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


    // Constructor si se repite el evento dada la fecha de fin y la frecuencia con "cada x dias".
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, LocalDateTime fechaFinalDefinitivo,
                  Integer frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.frecuencia = frecuencia;
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

    public void modificarFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void modificarDuracion(String duracion) {
        this.calcularFechaFin(duracion);
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
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
