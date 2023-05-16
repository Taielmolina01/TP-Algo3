package org.example;

import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void alarmasEnEventoyTarea() {
        Calendario nuevoCalendario = new Calendario();
        LocalDateTime fechaActual = LocalDateTime.of(2023, 4, 29, 0, 0, 0);

        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 4, 30, 0, 0, 0);
        Duration duracion = Duration.ofHours(2);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 4, 30, 0, 0, 0);

        nuevoCalendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);

        nuevoCalendario.agregarAlarma(0, Alarma.Efecto.EMAIL, Duration.ofMinutes(30));
        nuevoCalendario.agregarAlarma(0, Alarma.Efecto.EMAIL, Duration.ofHours(2));

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma(fechaActual).obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.EMAIL, nuevoCalendario.obtenerSiguienteAlarma(fechaActual).dispararAlarma());

        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.SONIDO, Duration.ofHours(3));

        assertEquals(LocalDateTime.of(2023, 4, 29, 21, 0, 0), nuevoCalendario.obtenerSiguienteAlarma(fechaActual).obtenerFechaActivacion());

        nuevoCalendario.eliminarAlarma(1, 0);

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma(fechaActual).obtenerFechaActivacion());

        nuevoCalendario.modificarEfectoAlarma(0, 1, Alarma.Efecto.NOTIFICACION);
        assertEquals(Alarma.Efecto.NOTIFICACION, nuevoCalendario.obtenerSiguienteAlarma(fechaActual).dispararAlarma());

        LocalDateTime fechaActualNueva = LocalDateTime.of(2023, 5, 1, 0, 0, 0);
        assertNull(nuevoCalendario.obtenerSiguienteAlarma(fechaActualNueva));
    }

    @Test
    public void testSetters() {
        Calendario nuevoCalendario = new Calendario();

        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 4, 1, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 4, 30, 0, 0, 0);

        nuevoCalendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);

        assertEquals(nombreEvento, nuevoCalendario.obtenerNombre(0));
        assertEquals(nombreTarea, nuevoCalendario.obtenerNombre(1));

        String nuevaDescripcionEvento = "descripcion del evento actualizada";
        String nuevaDescripcionTarea = "descripcion de la tarea actualizada";


        nuevoCalendario.modificarDescripcion(0, nuevaDescripcionEvento);
        nuevoCalendario.modificarDescripcion(1, nuevaDescripcionTarea);

        assertEquals(nuevaDescripcionEvento, nuevoCalendario.obtenerDescripcion(0));
        assertEquals(nuevaDescripcionTarea, nuevoCalendario.obtenerDescripcion(1));
    }

    @Test
    public void testDeserializadoYOriginalSonIguales() {
        Calendario calendario1 = crearCalendarioDosEventos();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        calendario1.serializar(bytes);
        Calendario calendario2 = (new Calendario()).deserializar(new ByteArrayInputStream(bytes.toByteArray()));

        assertEquals(calendario1.obtenerNombre(0), calendario2.obtenerNombre(0));
        assertEquals(calendario1.obtenerDescripcion(0), calendario2.obtenerDescripcion(0));

        assertEquals(calendario1.obtenerNombre(1), calendario2.obtenerNombre(1));
        assertEquals(calendario1.obtenerDescripcion(1), calendario2.obtenerDescripcion(1));

        assertEquals(calendario1.obtenerNombre(2), calendario2.obtenerNombre(2));
        assertEquals(calendario1.obtenerDescripcion(2), calendario2.obtenerDescripcion(2));

        assertEquals(calendario1.obtenerNombre(3), calendario2.obtenerNombre(3));
        assertEquals(calendario1.obtenerDescripcion(3), calendario2.obtenerDescripcion(3));

        assertThrows(NullPointerException.class, () -> calendario2.obtenerNombre(4));
        assertThrows(NullPointerException.class, () -> calendario2.obtenerDescripcion(4));
        assertThrows(NullPointerException.class, () -> calendario1.obtenerNombre(4));
        assertThrows(NullPointerException.class, () -> calendario1.obtenerDescripcion(4));

        /*
            Dos alarmas en el calendario:
            1. Suena el 29/3/2023 a las 12:00 (Efecto: EMAIL)
            2. Suena el 31/3/2023 a las 23:30 (Efecto: NOTIFICACION)
        */

        LocalDateTime fechaAnteriorAAlarmas = LocalDateTime.of(2023, 3, 28, 0, 0, 0);
        LocalDateTime fechaPosteriorAPrimeraAlarma = LocalDateTime.of(2023, 3, 29, 22, 0, 0);
        LocalDateTime fechaPosteriorAAmbasAlarmas = LocalDateTime.of(2023, 4, 1, 0, 0, 0);

        assertNotNull(calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas));
        assertNotNull(calendario2.obtenerSiguienteAlarma(fechaAnteriorAAlarmas));
        assertEquals(LocalDateTime.of(2023, 3, 29, 12, 0, 0), calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).obtenerFechaActivacion());
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).obtenerFechaActivacion(), calendario2.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.EMAIL, calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).dispararAlarma());
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).dispararAlarma(), calendario2.obtenerSiguienteAlarma(fechaAnteriorAAlarmas).dispararAlarma());

        assertNotNull(calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas));
        assertNotNull(calendario2.obtenerSiguienteAlarma(fechaAnteriorAAlarmas));
        assertEquals(LocalDateTime.of(2023, 3, 31, 23, 30, 0), calendario1.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).obtenerFechaActivacion());
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).obtenerFechaActivacion(), calendario2.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.NOTIFICACION, calendario1.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).dispararAlarma());
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).dispararAlarma(), calendario2.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma).dispararAlarma());

        assertNull(calendario1.obtenerSiguienteAlarma(fechaPosteriorAAmbasAlarmas));
        assertNull(calendario2.obtenerSiguienteAlarma(fechaPosteriorAAmbasAlarmas));
    }

    @Test
    public void testDeserializarEntradaVacía() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Calendario calendario = new Calendario().deserializar(new ByteArrayInputStream(bytes.toByteArray()));
        assertEquals("El flujo de entrada no existe o está vacío.", calendario.obtenerSalida());
    }

    private Calendario crearCalendarioDosEventos() {

        Calendario nuevoCalendario = new Calendario();

        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 4, 1, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 4, 30, 0, 0, 0);

        String nombreEvento2 = "Evento2";
        String descripcionEvento2 = "descripcion del evento2";


        String nombreTarea2 = "Tarea2";
        String descripcionTarea2 = "descripcion de la tarea2";

        nuevoCalendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);
        nuevoCalendario.crearEvento(nombreEvento2, descripcionEvento2, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea2, descripcionTarea2, fechaInicioTarea, false);

        nuevoCalendario.agregarAlarma(0, Alarma.Efecto.NOTIFICACION, Duration.ofMinutes(30));
        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.EMAIL, LocalDateTime.of(2023, 3, 29, 12, 0, 0));

        return nuevoCalendario;
    }




}