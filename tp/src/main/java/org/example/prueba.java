package org.example;


import org.example.Alarma.Alarma;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class prueba {

    public static void main(String[] args) {
        Calendario calendar = new Calendario();
        var fecha = LocalDateTime.of(2023, 6, 11, 12, 0, 0);
        System.out.println(calendar.obtenerSiguienteAlarmaPorActividad(fecha, fecha.plusMonths(1)));
        calendar.crearTarea("", "", fecha, false);

        calendar.agregarAlarma(0, Alarma.Efecto.NOTIFICACION, Duration.ofHours(2));
        calendar.agregarAlarma(0, Alarma.Efecto.NOTIFICACION, Duration.ofHours(5));
        System.out.println(calendar.obtenerSiguienteAlarmaPorActividad(fecha.minusMonths(1), fecha.plusMonths(1)).getKey());
        calendar.crearTarea(",", ",", fecha.minusDays(3), false);
        calendar.agregarAlarma(1, Alarma.Efecto.NOTIFICACION, Duration.ofHours(2));

        System.out.println(calendar.obtenerSiguienteAlarmaPorActividad(fecha.minusMonths(1), fecha.plusMonths(1)).getKey());
    }
}
