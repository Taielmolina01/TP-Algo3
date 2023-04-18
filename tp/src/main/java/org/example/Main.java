package org.example;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime diaInicio = LocalDateTime.parse("2018-01-10T11:25");
        LocalDateTime diaFin = LocalDateTime.parse("2019-01-10T11:25");
        String nombreEvento = "Cumple Luqui";
        String descripcionEvento = "Autista hijo de puta";
        DayOfWeek[] diasRepeticion = new DayOfWeek[]{};
        String[] hola = {"hola"};
        Duration duracion = Duration.ofHours(16);
        frecuenciaMensual frecuencia = new frecuenciaMensual(diasRepeticion, 2);
        Evento evento = new Evento(nombreEvento, descripcionEvento,
                diaInicio, duracion, false, diaFin, frecuencia);
        System.out.println(frecuencia.getTipoFrecuencia());
    }
}