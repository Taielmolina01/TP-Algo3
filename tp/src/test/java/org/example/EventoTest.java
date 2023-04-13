package org.example;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void testGetters() {
        LocalDateTime fechaCumple = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        LocalTime duracion = LocalTime.of(23, 59);
        Evento evento = new Evento("Cumpleaños", "Cumple Facu 22 años", fechaCumple, duracion);

        assertEquals("Cumpleaños", evento.getNombre());
        assertEquals("Cumple Facu 22 años", evento.getDescripcion());

        /* evento.modificarNombre("Cumpleañitos");
        evento.modificarDescripcion("Cumple Facu 21 años");

        assertEquals("Cumpleañitos", evento.getNombre());
        assertEquals("Cumple Facu 21 años", evento.getDescripcion());
         */
    }
}