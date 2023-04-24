package org.example;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void calendarioVacio() {
        Calendario nuevoCalendario = new Calendario();

        String nombreEvento = "Evento1";
        String descripcion = "descripcion del evento1";
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 4, 24, 0, 0, 0);
        Duration duracion = Duration.ofHours(2);

        String nombreEvento2 = "Evento2";
        String descripcion2 = "descripcion del evento2";
        LocalDateTime fechaInicio2 = LocalDateTime.of(2023, 4, 23, 0, 0, 0);

        nuevoCalendario.crearEvento(nombreEvento, descripcion, fechaInicio, false, duracion);
        nuevoCalendario.crearEvento(nombreEvento2, descripcion2, fechaInicio2, false, duracion);

        nuevoCalendario.configurarAlarma(0, Alarma.efecto.), ;

    }

}