package org.example;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void testGettersAndSettersCompartidos() {

        LocalDateTime fechaEvento = LocalDateTime.of(2023, 4, 24, 16, 30, 0);
        Duration duracion = Duration.ofHours(3);
        String nombreEvento = "Juntada";
        String descripcionEvento = "con los chicos del secundario";
        Evento evento = new Evento(nombreEvento, descripcionEvento, fechaEvento, duracion, false);

        assertEquals(nombreEvento, evento.getNombre());
        assertEquals(descripcionEvento, evento.getDescripcion());
        assertEquals(duracion, evento.getDuracion());
        assertEquals(fechaEvento, evento.getFechaInicio());
        assertEquals(false, evento.getTodoElDia());

        LocalDateTime fechaEventoModificada = LocalDateTime.of(2023, 4, 24, 14, 30, 0);
        Duration duracionModificada = Duration.ofHours(2);
        String nombreEventoModificado = "Reunión de trabajo";
        String descripcionEventoMModificada = "con Diego Corsi";

        evento.modificarNombre(nombreEventoModificado);
        evento.modificarDescripcion(descripcionEventoMModificada);
        evento.modificarDuracion(duracionModificada);
        evento.modificarFechaInicio(fechaEventoModificada);
        evento.modificarTodoElDia(true);

        assertEquals(nombreEventoModificado, evento.getNombre());
        assertEquals(descripcionEventoMModificada, evento.getDescripcion());
        assertEquals(duracionModificada, evento.getDuracion());
        assertEquals(fechaEventoModificada, evento.getFechaInicio());
        assertEquals(true, evento.getTodoElDia());
    }

    @Test
    public void testModificarFrecuencia() {
        LocalDateTime fechaInicio = LocalDateTime.of(2019, 1, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.MAX;
        Duration duracion = Duration.ofHours(12);
        Frecuencia frecuencia = new frecuenciaAnual(1);
        Evento evento = new Evento("Cumpleaños", "de Fede Esteban", fechaInicio, duracion, true, fechaFinal, frecuencia);

        assertEquals(frecuencia, evento.getFrecuencia());
        for (int i = 2019; i < 2091; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(i, 1, 22, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(i, 1, 23, 0, 0, 0)));
        }

        Frecuencia nuevaFrecuencia = new frecuenciaMensual(1);

        evento.modificarFrecuencia(nuevaFrecuencia);
        evento.modificarTodoElDia(false);
        evento.modificarDuracion(Duration.ofHours(1));
        evento.modificarNombre("Futbol 5");
        evento.modificarDescripcion("con los compañeros de FIUBA");
        evento.modificarFechaFinal(LocalDateTime.of(2021, 1, 22, 0, 0, 0));

        assertEquals(nuevaFrecuencia, evento.getFrecuencia());
        for (int i = 1; i < 12; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2019, i, 22, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2019, i, 23, 0, 0, 0)));
        }

        assertEquals(1, evento.getFrecuencia().getFrecuenciaRepeticiones());
        assertEquals(LocalDateTime.of(2021, 1, 22, 0, 0, 0), evento.getFechaFinal());
    }

    @Test
    public void testFrecuenciaAnual() {
        LocalDateTime fechaInicio = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.MAX;
        Duration duracion = Duration.ofHours(12);
        Frecuencia frecuencia = new frecuenciaAnual(1);
        Evento evento = new Evento("Cumpleaños", "de Diego Essaya", fechaInicio, duracion, true, fechaFinal, frecuencia);

        for (int i = 2001; i < 2091; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(i, 12, 22, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(i, 12, 23, 0, 0, 0)));
        }
    }

    @Test
    public void testFrecuenciaMensual() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 15, 14, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 7, 1, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);
        Frecuencia frecuencia = new frecuenciaMensual(1);
        Evento evento = new Evento("Reunión", "de FIUBA", fechaInicio, duracion, false, fechaFinal, frecuencia);

        for (int i = 3; i < 7; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, i, 15, 14, 0, 0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 7, 15, 14, 0, 0)));
    }

    @Test
    public void testFrecuenciaSemanal() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0, 0, 0);
        DayOfWeek[] diasSemana = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        Frecuencia frecuencia = new frecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento("Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        int[] diasTrue = {16, 17, 19, 20, 23, 24, 26, 27, 30, 31};
        int[] diasFalse = {18, 21, 22, 25, 28, 29};

        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 12, 0, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 23, 59, 59)));
        }
        for (int dia : diasFalse) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 12, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 23, 59, 59)));
        }
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 0, 0, 0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 12, 0, 0)));
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 23, 59, 59)));
    }

    @Test
    public void testFrecuenciaSemanal2() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0, 0, 0);
        DayOfWeek[] diasSemana = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        Frecuencia frecuencia = new frecuenciaSemanal(diasSemana, 2);
        Evento evento = new Evento("Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        int[] diasTrue = {16, 17, 19, 20, 30, 31};
        int[] diasFalse = {18, 21, 22, 23, 24, 25, 26, 27, 28, 29};

        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
        for (int dia : diasFalse) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 0, 0, 0)));
    }

    @Test
    public void testFrecuenciaDiaria() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 16, 12, 30, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 4, 16, 0, 0, 0);
        Frecuencia frecuencia = new frecuenciaDiaria(2);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento("Almuerzo", "con mi abuela", fechaInicio, duracion, false, fechaFinal, frecuencia);


        for (int i = 16; i < 31; i = i + 2) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 12, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 13, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 14, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023,3, i, 14, 50, 0)));
        }

        for (int i = 17; i < 31; i = i + 2) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 12, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 13, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 14, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023,3, i, 14, 50, 0)));
        }
    }

    @Test
    public void testEventoOcurrencias() {
        LocalDateTime fechaInicio = LocalDateTime.of(2021, 5, 10, 21, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2021, 7, 11, 21, 0, 0);
        Frecuencia frecuencia = new frecuenciaDiaria(1);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento("Cena", "con decano FIUBA", fechaInicio, duracion, false, 20, frecuencia);

        for (int i = 10; i < 30; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2021,5, i, 21, 0, 0)));
        }
        for (int i = 30; i < 32; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021, 5, i, 21, 0, 0)));
        }
        for (int i = 1; i < 31; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021,6, i, 21, 0, 0)));
        }
        for (int i = 1; i < 11; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021,7, i, 21, 0, 0)));
        }
    }
}