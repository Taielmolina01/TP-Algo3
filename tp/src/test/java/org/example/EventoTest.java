package org.example;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void testGettersandSetters() {

        LocalDateTime fechaEvento = LocalDateTime.of(2023, 4, 24, 16, 30, 0);
        Duration duracion = Duration.ofHours(3);
        String nombreEvento = "Juntada";
        String descripcionEvento = "con los chicos del secundario";
        Evento evento = new Evento(nombreEvento, descripcionEvento, fechaEvento, duracion, false);

        assertEquals(nombreEvento, evento.getNombre());
        assertEquals(descripcionEvento, evento.getDescripcion());
        assertEquals(duracion, evento.getDuracion());
        assertEquals(fechaEvento, evento.getFechaInicio());

        LocalDateTime fechaEventoModificada = LocalDateTime.of(2023, 4, 24, 14, 30, 0);
        Duration duracionModificada = Duration.ofHours(2);
        String nombreEventoModificado = "Reunión de trabajo";
        String descripcionEventoMModificada = "con Diego Corsi";

        evento.modificarNombre(nombreEventoModificado);
        evento.modificarDescripcion(descripcionEventoMModificada);
        evento.modificarDuracion(duracionModificada);
        evento.modificarFechaInicio(fechaEventoModificada);

        assertEquals(nombreEventoModificado, evento.getNombre());
        assertEquals(descripcionEventoMModificada, evento.getDescripcion());
        assertEquals(duracionModificada, evento.getDuracion());
        assertEquals(fechaEventoModificada, evento.getFechaInicio());
    }

    @Test
    public void testFrecuenciaAnual() {
        LocalDateTime fechaInicio = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.MAX;
        Duration duracion = Duration.ofHours(12);
        Frecuencia frecuencia = new frecuenciaAnual(1);
        Evento evento = new Evento("Cumpleaños", "de Diego Essaya", fechaInicio, duracion, true, fechaFinal, frecuencia);

        for (int i = 2001; i < 2091; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(i, 12, 22, 0,0,0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(i, 12, 23, 0,0,1)));
        }
    }

    @Test
    public void testFrecuenciaMensual() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 15, 14, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 7, 1, 0,0,0);
        Duration duracion = Duration.ofHours(3);
        Frecuencia frecuencia = new frecuenciaMensual(1);
        Evento evento = new Evento("Reunión", "de FIUBA", fechaInicio, duracion, false, fechaFinal, frecuencia);

        for (int i = 3; i < 7; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, i, 15, 14, 0,0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 7, 15, 14, 0, 0)));
    }

    @Test
    public void testFrecuenciaSemanal() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0,0,0);
        DayOfWeek[] diasSemana = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        Frecuencia frecuencia = new frecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento("Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        // marzo: 16,17,19,20,23,24,26,27, 30, 31

        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 16, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 17, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 18, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 19, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 20, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 21, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 22, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 23, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 24, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 25, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 26, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 27, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 28, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 29, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 30, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 31, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 0,0,1)));
    }

    @Test
    public void testFrecuenciaSemanal2() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0,0,0);
        DayOfWeek[] diasSemana = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        Frecuencia frecuencia = new frecuenciaSemanal(diasSemana, 2);
        Evento evento = new Evento("Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        // marzo: 16,17,19,20, 30, 31

        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 16, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 17, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 18, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 19, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 20, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 21, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 22, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 23, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 24, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 25, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 26, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 27, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 28, 0,0,1)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, 29, 0,0,1)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 30, 0,0,0)));
        assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, 31, 0,0,0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 0,0,1)));

    }

    @Test
    public void testFrecuenciaDiaria() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 16, 12, 30, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 4, 16, 0,0,0);
        Frecuencia frecuencia = new frecuenciaDiaria(2);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento("Almuerzo", "con mi abuela", fechaInicio, duracion, false, fechaFinal, frecuencia);


        for (int i = 16; i < 31; i = i + 2) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 12,30,0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 13,30,0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 14,30,0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 14,49,0)));
        }

        for (int i = 17; i < 31; i = i + 2) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 12,30,0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 13,30,0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 14,30,0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 14,49,0)));
        }

    }
}