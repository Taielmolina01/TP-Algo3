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

        nuevoCalendario.crearEvento("Evento1", "este es el evento1", fechaInicio, false, Duration.ofHours(2));
        assertTrue(nuevoCalendario.modificarEvento(0));
        nuevoCalendario.crearEvento("Evento1", "este es el evento1", fechaInicio, false, Duration.ofHours(2));
        assertTrue(nuevoCalendario.modificarEvento(1));
    }

}