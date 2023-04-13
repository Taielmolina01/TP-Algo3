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

    private void constructorDefault(String nombre, String descripcion, LocalDateTime fechaInicio, LocalTime duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.calcularFechaFin(duracion, fechaInicio);
    }

    private LocalDateTime calcularFechaFin(LocalTime duracion, LocalDateTime fechaInicio) {
        int hsDuracion = this.duracion.getHour();
        int minsDuracion = this.duracion.getMinute();
        int segsDuracion = this.duracion.getSecond();
        fechaFin = fechaInicio.plusHours(hsDuracion).plusMinutes(minsDuracion).plusSeconds(segsDuracion);
        return fechaFin;
    }

    /* Se le debe pasar un array de tamaño 2, la primera posicion debe tener un indicador de si la frecuencia es diaria
        semanal, mensual o anual representado por una letra
        "D": diaria
        "S": semanal
        "M": mensual
        "A": anual
        En la segunda posicion se debe indicar cada cuantos dias/semanas/meses/años se produce el evento
    */

    private static boolean esNumero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
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


    private boolean hayEvento(LocalDateTime diaAAnalizar) {
        if (diaAAnalizar.isBefore(this.fechaInicio) || diaAAnalizar.isAfter(this.fechaFinalDefinitivo)) {
            return false;
        } else {
            /* Puede haber evento porque está entre el rango en el que inicio el evento y cuando termina
            deberia analizar como hago para ir avanzando desde la fecha inicial hasta al fechaAAnalizar SIN PASARME,
            y devolver true si en la fechaAAnalizar hubo evento
            */
            String funcionAUtilizar = ""; // Ver q onda esto
            Long multiplicador = Long.parseLong("1");
            switch (this.frecuencia[0]) {
                case "D":
                    funcionAUtilizar = "PD"; multiplicador = Long.parseLong(this.frecuencia[1]);
                    break;
                case "S":
                    funcionAUtilizar = "PD"; multiplicador = Long.parseLong(this.frecuencia[1]) * 7;
                    break;
                case "M":
                    funcionAUtilizar = "PM"; multiplicador = Long.parseLong(this.frecuencia[1]);
                    break;
                case "A":
                    funcionAUtilizar = "PY"; multiplicador = Long.parseLong(this.frecuencia[1]);
                    break;
            }
            if (funcionAUtilizar.equals("PD")) { // Repito codigo a lo loco
                for (LocalDateTime dia = this.fechaInicio; dia.isBefore(this.fechaFinalDefinitivo); dia = dia.plusDays(multiplicador)) {
                    if (diaAAnalizar.isEqual(dia) || (diaAAnalizar.isAfter(dia) && diaAAnalizar.isBefore(this.calcularFechaFin(this.duracion, dia)))) { // deberia hacer que si diaAAnalizar isBetween dia y dia + duracion
                        return true;
                    }
                }
                return false;
            }
            if (funcionAUtilizar.equals("PM")) {
                for (LocalDateTime dia = this.fechaInicio; dia.isBefore(this.fechaFinalDefinitivo); dia = dia.plusMonths(multiplicador)) {
                    if (diaAAnalizar.isEqual(dia) || (diaAAnalizar.isAfter(dia) && diaAAnalizar.isBefore(this.calcularFechaFin(this.duracion, dia)))) {
                        return true;
                    }
                }
                return false;
            }
            if (funcionAUtilizar.equals("PY")) {
                for (LocalDateTime dia = this.fechaInicio; dia.isBefore(this.fechaFinalDefinitivo); dia = dia.plusYears(multiplicador)) {
                    if (diaAAnalizar.isEqual(dia) || (diaAAnalizar.isAfter(dia) && diaAAnalizar.isBefore(this.calcularFechaFin(this.duracion, dia)))) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }

    private boolean validarFrecuencia(String[] frecuencia) { // Va o no? pienso que no porque sino tendria que validar cada cosa que ingreso.
        frecuencia[1] = frecuencia[1].toUpperCase();
        if (frecuencia.length != 2 || !esLetraValida(frecuencia[0]) || !esNumero(frecuencia[1])) {
            System.out.println("Error con los datos ingresados en la frecuencia");
            return false;
        }
        return true;
    }

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalTime duracion) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = this.fechaFin;
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalTime duracion, LocalDateTime fechaFinalDefinitivo,
                  String[] frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
        if (this.validarFrecuencia(frecuencia)) {
            this.frecuencia = frecuencia;
        }
    }


    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, LocalTime duracion, Integer ocurrencias, String[] frecuencia) {
        this.constructorDefault(nombre, descripcion, fechaInicio, duracion);
        if (this.validarFrecuencia(frecuencia)) {
            this.frecuencia = frecuencia;
        }
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

    public void modificarDuracion(LocalTime duracion) {
        this.fechaFin = this.calcularFechaFin(duracion, this.fechaInicio);
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalDefinitivo) {
        this.fechaFinalDefinitivo = fechaFinalDefinitivo;
    }



}
