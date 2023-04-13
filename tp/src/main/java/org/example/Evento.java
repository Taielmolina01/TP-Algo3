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

    private String[] frecuencia;

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.nombre = nombre; this.descripcion = descripcion; this.fechaInicio = fechaInicio;
        this.calcularFechaFin(duracion);
    }

    private void calcularFechaFin(String duracion) {
        this.duracion = LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int hsDuracion = this.duracion.getHour(); int minsDuracion = this.duracion.getMinute(); int segsDuracion = this.duracion.getSecond();
        this.fechaFin = this.fechaInicio.plusHours(hsDuracion).plusMinutes(minsDuracion).plusSeconds(segsDuracion);
    }

    /* Se le debe pasar un array de tamaño 2, la primera posicion debe tener un indicador de si la frecuencia es diaria
        semanal, mensual o anual representado por una letra
        "D": diaria
        "S": semanal
        "M": mensual
        "A": anual
        En la segunda posicion se debe indicar cada cuantos dias/semanas/meses/años se produce el evento
    */

    private static boolean esNumero(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private static boolean esLetraValida(String letra) {
        String[] letrasValidas = new String[]{"D", "S", "M", "A"};
        for (String letraValida : letrasValidas) {
            if (letra.equals(letraValida)) {
                return true;
            }
        }
        return false;
    }

    private void calcularFrecuencia(String[] frecuencia) {
        frecuencia[1] = frecuencia[1].toUpperCase();
        if (frecuencia.length != 2 || !esLetraValida(frecuencia[0]) || !esNumero(frecuencia[1])) {
            System.out.println("Error con los datos ingresadoe n la frecuencia");
        } else {
            if (frecuencia[0].equals("D")) {
                System.out.println("D");
            }
            if (frecuencia[0].equals("S")) {
                System.out.println("S");
            }
            if (frecuencia[0].equals("M")) {
                System.out.println("M");
            } else {
                System.out.println("A");
            }
        }
    }

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = this.fechaFin;
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, LocalDateTime fechaFinalDefinitivo,
                  String[] frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        this.frecuencia = frecuencia;
    }


    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, String duracion, Integer ocurrencias, String[] frecuencia) {
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



}
