package org.example;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;
import org.example.Frecuencia.FrecuenciaDiaria;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class prueba {

    public static void main(String[] args) {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 6, 8, 10, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 7, 8, 10, 0, 0);
        Frecuencia frecuencia = new FrecuenciaDiaria(7);
        Duration duracion = Duration.ofHours(2);
        Evento evento = new Evento(0, "Almuerzo", "con mi abuela",
                fechaInicio, duracion, false, fechaFinal, frecuencia);
        evento.agregarAlarma(Alarma.Efecto.NOTIFICACION, Duration.ofHours(2));
        evento.agregarAlarma(Alarma.Efecto.NOTIFICACION, evento.obtenerAlarma(0).cuantoFaltaParaDisparar(evento.obtenerFechaInicio()));
        ArrayList<Actividad> actividades = evento.actividadesEntreFechas(fechaInicio, fechaFinal);
        for (var e : actividades) {
            System.out.println(e.obtenerAlarma(0).obtenerFechaActivacion());
        }
    }
}
