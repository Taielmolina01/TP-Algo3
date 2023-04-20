package org.example;


import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0,0,0);
        DayOfWeek[] diasSemana = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        Frecuencia frecuencia = new frecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento("Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        evento.eventosHastaFecha(LocalDateTime.of(2020, 6, 12, 0,0,0));
    }
}