package org.example;

import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void alarmasEnEventoyTarea() {
        Calendario nuevoCalendario = new Calendario();

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

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.EMAIL, nuevoCalendario.obtenerSiguienteAlarma().dispararAlarma());

        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.SONIDO, Duration.ofHours(3));

        assertEquals(LocalDateTime.of(2023, 4, 29, 21, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());

        nuevoCalendario.eliminarAlarma(1, 0);

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());

        nuevoCalendario.modificarEfectoAlarma(0, 1, Alarma.Efecto.NOTIFICACION);
        assertEquals(Alarma.Efecto.NOTIFICACION, nuevoCalendario.obtenerSiguienteAlarma().dispararAlarma());
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
    public void testGuardarCalendarioVacio() throws IOException{
        Calendario nuevoCalendario = new Calendario();
        nuevoCalendario.guardarEstado();
    }

    @Test
    public void testRecuperarCalendarioVacio() throws IOException, ClassNotFoundException {
        Calendario nuevoCalendario = new Calendario();
        nuevoCalendario.recuperarEstado();
        assertThrows(NullPointerException.class, () -> nuevoCalendario.obtenerNombre(0));
    }

    @Test
    public void testGuardarEstadoCalendario() throws IOException, ClassNotFoundException {

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

        nuevoCalendario.guardarEstado();
    }

    @Test
    public void TestRecuperarEstado() throws IOException, ClassNotFoundException{
        Calendario nuevoCalendario2 = new Calendario();

        nuevoCalendario2.recuperarEstado();

        assertEquals("Evento", nuevoCalendario2.obtenerNombre(0));
        assertEquals("descripcion del evento", nuevoCalendario2.obtenerDescripcion(0));

        assertEquals("Tarea", nuevoCalendario2.obtenerNombre(1));
        assertEquals("descripcion de la tarea", nuevoCalendario2.obtenerDescripcion(1));

        assertEquals("Evento2", nuevoCalendario2.obtenerNombre(2));
        assertEquals("descripcion del evento2", nuevoCalendario2.obtenerDescripcion(2));

        assertEquals("Tarea2", nuevoCalendario2.obtenerNombre(3));
        assertEquals("descripcion de la tarea2", nuevoCalendario2.obtenerDescripcion(3));
    }
}