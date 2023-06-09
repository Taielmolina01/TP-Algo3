package org.example;

import org.example.Actividades.Evento;
import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.time.Duration;
import java.time.LocalDateTime;


public class prueba {

    public static void main(String[] args) {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 6, 8, 10, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 7, 8, 10, 0, 0);
        Frecuencia frecuencia = new FrecuenciaDiaria(7);
        Duration duracion = Duration.ofHours(2);
        Evento evento = new Evento(0, "Almuerzo", "con mi abuela",
                fechaInicio, duracion, false, fechaFinal, frecuencia);
        evento.agregarAlarma(Alarma.Efecto.NOTIFICACION, Duration.ofHours(2));
        var eventos = evento.actividadesEntreFechas(fechaInicio, fechaFinal);
        for (var e : eventos) {
            System.out.println(e.obtenerFechaInicio());
        }
        System.out.println();
        var fechaInicio2 = LocalDateTime.of(2023, 7, 1, 0, 0, 0);
        var fechaFin2 = LocalDateTime.of(2023, 7, 31, 23, 59, 59);
        var eventos2 = evento.actividadesEntreFechas(fechaInicio2, fechaFin2);
        for (var e : eventos2) {
            System.out.println(e.obtenerFechaInicio());
        }

    }
}
