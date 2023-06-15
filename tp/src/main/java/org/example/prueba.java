package org.example;

import org.example.Actividades.Evento;
import org.example.Frecuencia.Frecuencia;
import org.example.Frecuencia.FrecuenciaSemanal;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class prueba {

    public static void main(String[] args) {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 6, 14, 16, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 8, 1, 16, 0, 0);
        DayOfWeek[] dias = new DayOfWeek[]{DayOfWeek.THURSDAY};
        SortedSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));
        Frecuencia frecuencia = new FrecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento(0, "Clases", "en FIUBA", fechaInicio, Duration.ofHours(2), false, fechaFinal, frecuencia);
    }
}
