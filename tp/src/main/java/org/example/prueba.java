package org.example;


import org.example.Actividades.Actividad;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class prueba {

    public static void main(String[] args) {
        Calendario calendar = new Calendario();
        var fecha = LocalDateTime.of(2023, 6, 10, 10, 0, 0);
        calendar.crearEvento("", "", fecha, Duration.ofHours(2), false, fecha.plusMonths(1), new FrecuenciaDiaria(7));


        var actividades = calendar.obtenerActividadesEntreFechas(fecha.plusWeeks(1).with(LocalTime.MIN), fecha.plusWeeks(2).with(LocalTime.MAX));
        System.out.println(actividades);


        for (Actividad a : actividades) {
            System.out.println(a.obtenerFechaInicio());
        }

    }
}
