package tp;

import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ManejadorGuardadoTest {

    private final String nombreArchivo = "MiCalendario.txt";

    @Test
    public void testRecuperarCalendarioArchivoVacio() {
        Calendario nuevoCalendario = new Calendario();
        assertThrows(NullPointerException.class, () -> nuevoCalendario.obtenerNombre(0));
    }

    @Test
    public void testGuardarCalendarioVacio() {
        Calendario nuevoCalendario = new Calendario();
        nuevoCalendario.guardarEstado();
    }

    @Test
    public void testRecuperarCalendarioVacio() {
        Calendario nuevoCalendario = Calendario.recuperarEstado(nombreArchivo);
        assertThrows(NullPointerException.class, () -> nuevoCalendario.obtenerNombre(0));
    }

    @Test
    public void testGuardarEstadoCalendario() {

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
    public void TestRecuperarEstado() {
        Calendario nuevoCalendario2 = Calendario.recuperarEstado(nombreArchivo);

        assertEquals("Evento", nuevoCalendario2.obtenerNombre(0));
        assertEquals("descripcion del evento", nuevoCalendario2.obtenerDescripcion(0));

        assertEquals("Tarea", nuevoCalendario2.obtenerNombre(1));
        assertEquals("descripcion de la tarea", nuevoCalendario2.obtenerDescripcion(1));

        assertEquals("Evento2", nuevoCalendario2.obtenerNombre(2));
        assertEquals("descripcion del evento2", nuevoCalendario2.obtenerDescripcion(2));

        assertEquals("Tarea2", nuevoCalendario2.obtenerNombre(3));
        assertEquals("descripcion de la tarea2", nuevoCalendario2.obtenerDescripcion(3));

        assertThrows(NullPointerException.class, () -> nuevoCalendario2.obtenerNombre(4));
        assertThrows(NullPointerException.class, () -> nuevoCalendario2.obtenerDescripcion(4));
    }

    @Test
    public void testBorrarEstado() {
        Calendario calendario1 = new Calendario();

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


        calendario1.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        calendario1.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);
        calendario1.crearEvento(nombreEvento2, descripcionEvento2, fechaInicioEvento, duracion, false);
        calendario1.crearTarea(nombreTarea2, descripcionTarea2, fechaInicioTarea, false);

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            calendario1.serializar(bytes);
            Calendario calendario2 = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));

            assertNotNull(calendario2);

            assertEquals(calendario1.obtenerNombre(0), calendario2.obtenerNombre(0));
            assertEquals(calendario1.obtenerDescripcion(0), calendario2.obtenerDescripcion(0));

            assertEquals(calendario1.obtenerNombre(1), calendario2.obtenerNombre(1));
            assertEquals(calendario1.obtenerDescripcion(1), calendario2.obtenerDescripcion(1));

            assertEquals(calendario1.obtenerNombre(2), calendario2.obtenerNombre(2));
            assertEquals(calendario1.obtenerDescripcion(2), calendario2.obtenerDescripcion(2));

            assertEquals(calendario1.obtenerNombre(3), calendario2.obtenerNombre(3));
            assertEquals(calendario1.obtenerDescripcion(3), calendario2.obtenerDescripcion(3));

            assertThrows(NullPointerException.class, () -> calendario1.obtenerNombre(4));
            assertThrows(NullPointerException.class, () -> calendario1.obtenerDescripcion(4));
            assertThrows(NullPointerException.class, () -> calendario2.obtenerNombre(4));
            assertThrows(NullPointerException.class, () -> calendario2.obtenerDescripcion(4));
        } catch (Exception e) {
            //
        }
    }
}