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

        assertTrue(nuevoCalendario.eventos.isEmpty());
        assertTrue(nuevoCalendario.tareas.isEmpty());
        assertNull(nuevoCalendario.obtenerAlarmas().peek());
    }

    @Test
    public void name() {
        Calendario nuevoCalendario = new Calendario();
        LocalDateTime fechaEvento = LocalDateTime.of(2023, 4, 24, 16, 30, 0);
        Duration duracion = Duration.ofHours(3);
        String nombreEvento = "Juntada";
        String descripcionEvento = "con los chicos del secundario";
        Evento evento1 = new Evento(nombreEvento, descripcionEvento, fechaEvento, duracion, false);
        TemporalAmount tiempoRelativo = new
        Alarma alarmaEvento1 = new Alarma(Alarma.efecto.NADA, fechaEvento, );
        evento1.agregarAlarma(alarmaEvento1); // ??

        nuevoCalendario.eventos.put(evento);

    }
    @Test
    public void name() {
    }
    @Test
    public void name() {
    }
    @Test
    public void name() {
    }
    @Test
    public void name() {
    }
}