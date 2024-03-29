package org.example;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Frecuencia.*;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class EventoTest {
    @Test
    public void testGettersAndSettersCompartidos() {

        LocalDateTime fechaEvento = LocalDateTime.of(2023, 4, 24, 16, 30, 0);
        Duration duracion = Duration.ofHours(3);
        String nombreEvento = "Juntada";
        String descripcionEvento = "con los chicos del secundario";
        Evento evento = new Evento(0, nombreEvento, descripcionEvento, fechaEvento, duracion, false);

        assertEquals(nombreEvento, evento.obtenerNombre());
        assertEquals(descripcionEvento, evento.obtenerDescripcion());
        assertEquals(duracion, evento.obtenerDuracion());
        assertEquals(fechaEvento, evento.obtenerFechaInicio());
        assertEquals(false, evento.obtenerTodoElDia());

        LocalDateTime fechaEventoModificada = LocalDateTime.of(2023, 4, 24, 14, 30, 0);
        Duration duracionModificada = Duration.ofHours(2);
        String nombreEventoModificado = "Reunión de trabajo";
        String descripcionEventoMModificada = "con Diego Corsi";

        evento.modificarNombre(nombreEventoModificado);
        evento.modificarDescripcion(descripcionEventoMModificada);
        evento.modificarDuracion(duracionModificada);
        evento.modificarFechaInicio(fechaEventoModificada);
        evento.modificarTodoElDia(true);

        assertEquals(nombreEventoModificado, evento.obtenerNombre());
        assertEquals(descripcionEventoMModificada, evento.obtenerDescripcion());
        assertEquals(duracionModificada, evento.obtenerDuracion());
        assertEquals(fechaEventoModificada.toLocalDate().atStartOfDay(), evento.obtenerFechaInicio());
        assertEquals(true, evento.obtenerTodoElDia());
    }

    @Test
    public void testModificarFrecuencia() {
        LocalDateTime fechaInicio = LocalDateTime.of(2019, 1, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.MAX;
        Duration duracion = Duration.ofHours(12);
        Frecuencia frecuencia = new FrecuenciaAnual(1);
        Evento evento = new Evento(0, "Cumpleaños", "de Fede Esteban", fechaInicio, duracion, true, fechaFinal, frecuencia);

        assertEquals(frecuencia, evento.obtenerFrecuencia());
        for (int i = 2019; i < 2091; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(i, 1, 22, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(i, 1, 23, 0, 0, 0)));
        }

        Frecuencia nuevaFrecuencia = new FrecuenciaMensual(1);

        evento.modificarFrecuencia(nuevaFrecuencia);
        evento.modificarTodoElDia(false);
        evento.modificarDuracion(Duration.ofHours(1));
        evento.modificarNombre("Futbol 5");
        evento.modificarDescripcion("con los compañeros de FIUBA");
        evento.modificarFechaFinal(LocalDateTime.of(2021, 1, 22, 0, 0, 0));

        assertEquals(nuevaFrecuencia, evento.obtenerFrecuencia());
        for (int i = 1; i < 12; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2019, i, 22, 0, 0, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2019, i, 23, 0, 0, 0)));
        }

        assertEquals(1, evento.obtenerFrecuencia().obtenerValorRepeticion());
        assertEquals(LocalDateTime.of(2021, 1, 22, 0, 0, 0), evento.obtenerFechaFinalDefinitivo());
    }

    @Test
    public void testFrecuenciaAnual() {
        LocalDateTime fechaInicio = LocalDateTime.of(2001, 12, 22, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.MAX;
        Duration duracion = Duration.ofHours(12);
        Frecuencia frecuencia = new FrecuenciaAnual(1);
        Evento evento = new Evento(0, "Cumpleaños", "de Diego Essaya", fechaInicio, duracion, true, fechaFinal, frecuencia);

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
        Frecuencia frecuencia = new FrecuenciaMensual(1);
        Evento evento = new Evento(0, "Reunión", "de FIUBA", fechaInicio, duracion, false, fechaFinal, frecuencia);

        for (int i = 3; i < 7; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, i, 15, 14, 0, 0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 7, 15, 14, 0, 0)));
    }

    @Test
    public void testFrecuenciaSemanal() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2020, 4, 1, 0, 0, 0);
        DayOfWeek[] dias = new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        SortedSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));
        FrecuenciaSemanal frecuencia = new FrecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento(0, "Clases", "en FIUBA", fechaInicio, null, true, fechaFinal, frecuencia);

        for (int i = 0; i < frecuencia.obtenerDiasSemana().size(); i++) {
            assertEquals(dias[i], frecuencia.obtenerDiasSemana().toArray()[i]);
        }

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

        DayOfWeek[] nuevosDias = {DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
        SortedSet<DayOfWeek> nuevosDiasSemana = new TreeSet<>(Arrays.asList(nuevosDias));
        frecuencia.modificarDiasSemana(nuevosDiasSemana);
        evento.modificarFechaInicio(LocalDateTime.of(2020, 3, 18, 0, 0, 0));

        for (int i = 0; i < frecuencia.obtenerDiasSemana().size(); i++) {
            assertEquals(nuevosDias[i], frecuencia.obtenerDiasSemana().toArray()[i]);
        }

        for (int dia : diasFalse) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 12, 0, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 23, 59, 59)));
        }
    }

    @Test
    public void testFrecuenciaSemanal2() {
        LocalDateTime fechaInicio = LocalDateTime.of(2020, 3, 16, 0, 0, 0);
        DayOfWeek[] dias = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        TreeSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));
        FrecuenciaSemanal frecuencia = new FrecuenciaSemanal(diasSemana, 2);
        Evento evento = new Evento(0, "Clases", "en FIUBA", fechaInicio, null, true, 6, frecuencia);

        int[] diasTrue = {16, 17, 19, 20, 30, 31};
        int[] diasFalse = {18, 21, 22, 23, 24, 25, 26, 27, 28, 29};

        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
        for (int dia : diasFalse) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
        assertFalse(evento.hayEvento(LocalDateTime.of(2020, 4, 1, 0, 0, 0)));

        DayOfWeek[] diasNuevos = {DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
        TreeSet<DayOfWeek> diasSemanaNuevos = new TreeSet<>(Arrays.asList(diasNuevos));
        FrecuenciaSemanal frecuenciaNueva = new FrecuenciaSemanal(diasSemanaNuevos, 1);
        evento.modificarFechaInicio(LocalDateTime.of(2020, 3, 18, 0, 0, 0));
        evento.modificarFrecuencia(frecuenciaNueva);

        int[] diasTrueNuevos = {18, 21, 22, 25, 28, 29};
        int[] diasFalseNuevos = {19, 20, 23, 24, 26, 27, 30};

        for (int dia : diasTrueNuevos) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
        for (int dia : diasFalseNuevos) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2020, 3, dia, 0, 0, 0)));
        }
    }

    @Test
    public void testFrecuenciaDiaria() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 16, 12, 30, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 4, 16, 0, 0, 0);
        Frecuencia frecuencia = new FrecuenciaDiaria(2);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento(0, "Almuerzo", "con mi abuela", fechaInicio, duracion, false, fechaFinal, frecuencia);


        for (int i = 16; i < 31; i = i + 2) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 12, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 13, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 50, 0)));

            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 12, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 13, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 50, 0)));
        }

        evento.modificarFrecuencia(new FrecuenciaDiaria(3));

        for (int i = 16; i < 31; i = i + 3) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 12, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 13, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 50, 0)));

            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 12, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 13, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 50, 0)));
        }
    }

    @Test
    public void testEventoOcurrencias() {
        LocalDateTime fechaInicio = LocalDateTime.of(2021, 5, 10, 21, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2021, 7, 11, 21, 0, 0);
        Frecuencia frecuencia = new FrecuenciaDiaria(1);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento(0, "Cena", "con decano FIUBA", fechaInicio, duracion, false, 20, frecuencia);

        for (int i = 10; i < 30; i++) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2021, 5, i, 21, 0, 0)));
        }
        for (int i = 30; i < 32; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021, 5, i, 21, 0, 0)));
        }
        for (int i = 1; i < 31; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021, 6, i, 21, 0, 0)));
        }
        for (int i = 1; i < 11; i++) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2021, 7, i, 21, 0, 0)));
        }
    }


    @Test
    public void testModificarTipoFrecuencia() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 6, 17, 30, 0);
        DayOfWeek[] dias = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        TreeSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));
        Frecuencia frecuencia = new FrecuenciaSemanal(diasSemana, 1);
        Duration duracion = Duration.ofHours(2);
        Evento evento = new Evento(0, "Ir al gimnasio", "de a la vuelta de casa", fechaInicio, duracion, false, 11, frecuencia);

        int[] diasTrue = {6, 7, 9, 10, 13, 14, 16, 17, 20, 21, 23};

        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 17, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 18, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 19, 30, 0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, 24, 17, 30, 0)));

        LocalDateTime fechaFinal = LocalDateTime.of(2023, 4, 1, 0, 0, 0);
        evento.modificarFechaFinal(fechaFinal);

        int[] diasTrueNuevos = {6, 7, 9, 10, 13, 14, 16, 17, 20, 21, 23, 24, 27, 28, 30, 31};

        for (int dia : diasTrueNuevos) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 17, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 18, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 19, 30, 0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 4, 1, 17, 30, 0)));

        evento.modificarOcurrencias(11);

        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 17, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 18, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 19, 30, 0)));
        }

        assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, 24, 17, 30, 0)));
    }

    @Test
    public void testFrecuenciaDiariaASemanal() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 3, 16, 12, 30, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 4, 16, 0, 0, 0);
        Frecuencia frecuencia = new FrecuenciaDiaria(2);
        Duration duracion = Duration.ofHours(2).plusMinutes(20);
        Evento evento = new Evento(0, "Almuerzo", "con mi abuela", fechaInicio, duracion, false, fechaFinal, frecuencia);

        for (int i = 16; i < 31; i = i + 2) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 12, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 13, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, i, 14, 50, 0)));

            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 12, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 13, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, i + 1, 14, 50, 0)));
        }

        DayOfWeek[] dias = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        TreeSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));
        Frecuencia frecuenciaNueva = new FrecuenciaSemanal(diasSemana, 1);

        evento.modificarFrecuencia(frecuenciaNueva);
        int[] diasTrue = {16, 17, 20, 21, 23, 24};
        for (int dia : diasTrue) {
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 12, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 13, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 14, 30, 0)));
            assertTrue(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 14, 50, 0)));
        }

        int[] diasFalse = {18, 22, 25, 26, 29};
        for (int dia : diasFalse) {
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 12, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 13, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 14, 30, 0)));
            assertFalse(evento.hayEvento(LocalDateTime.of(2023, 3, dia, 14, 50, 0)));
        }
    }

    @Test
    public void testEventosEntreFechas() {
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 9, 11, 21, 0, 0);
        LocalDateTime fechaFinal = LocalDateTime.of(2023, 9, 26, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);
        DayOfWeek[] dias = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
        TreeSet<DayOfWeek> diasSemana = new TreeSet<>(Arrays.asList(dias));

        Frecuencia frecuenciaSemanal = new FrecuenciaSemanal(diasSemana, 1);
        Evento evento = new Evento(0, "Conciertos", "de Mora", fechaInicio, duracion, false, fechaFinal, frecuenciaSemanal);

        int[] diasTrue = {11, 12, 14, 15, 18, 19, 21, 22, 25};
        ArrayList<LocalDateTime> fechasTrue = new ArrayList<>();
        for (int dia : diasTrue) {
            fechasTrue.add(LocalDateTime.of(2023, 9, dia, 21, 0, 0));
        }

        ArrayList<LocalDateTime> fechasActividades1 = new ArrayList<>();
        ArrayList<Actividad> actividades1 = evento.actividadesEntreFechas(fechaInicio.minusMonths(2), fechaFinal);
        for (int i = 0; i < actividades1.size(); i++) {
            fechasActividades1.add(actividades1.get(i).obtenerFechaInicio());
        }
        ArrayList<LocalDateTime> fechasActividades2 = new ArrayList<>();
        ArrayList<Actividad> actividades2 = evento.actividadesEntreFechas(fechaInicio, fechaFinal);
        for (int i = 0; i < actividades2.size(); i++) {
            fechasActividades2.add(actividades2.get(i).obtenerFechaInicio());
        }
        ArrayList<LocalDateTime> fechasActividades3 = new ArrayList<>();
        ArrayList<Actividad> actividades3 = evento.actividadesEntreFechas(fechaInicio.plusWeeks(1), fechaFinal);
        for (int i = 0; i < actividades3.size(); i++) {
            fechasActividades3.add(actividades3.get(i).obtenerFechaInicio());
        }
        ArrayList<LocalDateTime> fechasActividades4 = new ArrayList<>();
        ArrayList<Actividad> actividades4 = evento.actividadesEntreFechas(fechaInicio.plusWeeks(4), fechaFinal);
        for (int i = 0; i < actividades4.size(); i++) {
            fechasActividades4.add(actividades4.get(i).obtenerFechaInicio());
        }

        assertEquals(fechasActividades1, fechasTrue);
        assertEquals(fechasActividades2, fechasTrue);
        assertEquals(fechasActividades3, fechasTrue.subList(4, diasTrue.length));
        assertEquals(fechasActividades4, new ArrayList<>());
    }
}