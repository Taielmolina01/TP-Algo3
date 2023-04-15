package org.example;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void testGetters() {
        LocalDateTime fechaCumple = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        Duration duracion = Duration.ofHours(24);
        Evento evento = new Evento("Cumpleaños", "Cumple Facu 22 años", fechaCumple, duracion);

        assertEquals("Cumpleaños", evento.getNombre());
        assertEquals("Cumple Facu 22 años", evento.getDescripcion());

        /* evento.modificarNombre("Cumpleañitos");
        evento.modificarDescripcion("Cumple Facu 21 años");

        assertEquals("Cumpleañitos", evento.getNombre());
        assertEquals("Cumple Facu 21 años", evento.getDescripcion());
         */
    }

    @Test
    public void testFechas() {
        LocalDateTime fechaInicio = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2002, 12, 22, 0, 0, 0);
        Duration duracion = Duration.ofHours(26);
        String[] frecuencia = {"D", "3"};
        Evento evento = new Evento("Evento1", "Este es el evento1", fechaInicio, duracion, fechaFinal, frecuencia);
        LocalDateTime fechaAAnalizar = LocalDateTime.of(2001, 12, 23, 0, 0, 0);

        assertTrue(evento.hayEvento(fechaAAnalizar));
    }
}