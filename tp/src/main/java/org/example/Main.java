package org.example;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Duration duracion = Duration.ofHours(2).plusMinutes(20).plusSeconds(30);
        Long duracionHoras = duracion.toHours();
        Long duracionMinutos = duracion.minusHours(duracionHoras).toMinutes();
        System.out.println(duracionHoras);
        System.out.println(duracionMinutos);
        System.out.println(duracion.minusHours(duracionHoras).minusMinutes(duracionMinutos).toSeconds());
    }
}