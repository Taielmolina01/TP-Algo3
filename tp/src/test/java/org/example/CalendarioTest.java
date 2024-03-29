package org.example;

import org.example.Alarma.Alarma;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CalendarioTest {

    @Test
    public void testAlarmasEnEventoyTarea() {
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


        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0),
                nuevoCalendario.obtenerSiguienteAlarma(fechaActual, fechaActual.plusWeeks(2)).obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.EMAIL, nuevoCalendario.obtenerSiguienteAlarma(fechaActual, fechaActual.plusWeeks(2)).dispararAlarma());

        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.SONIDO, Duration.ofHours(3));

        assertEquals(LocalDateTime.of(2023, 4, 29, 21, 0, 0),
                nuevoCalendario.obtenerSiguienteAlarma(fechaActual, fechaActual.plusWeeks(2)).obtenerFechaActivacion());

        nuevoCalendario.eliminarAlarma(1, 0);

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0),
                nuevoCalendario.obtenerSiguienteAlarma(fechaActual, fechaActual.plusWeeks(2)).obtenerFechaActivacion());

        nuevoCalendario.modificarEfectoAlarma(0, 1, Alarma.Efecto.NOTIFICACION);
        assertEquals(Alarma.Efecto.NOTIFICACION, nuevoCalendario.obtenerSiguienteAlarma(fechaActual, fechaActual.plusWeeks(2)).dispararAlarma());

        LocalDateTime fechaActualNueva = LocalDateTime.of(2023, 5, 1, 0, 0, 0);
        assertNull(nuevoCalendario.obtenerSiguienteAlarma(fechaActualNueva, fechaActual.plusWeeks(2)));
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
}