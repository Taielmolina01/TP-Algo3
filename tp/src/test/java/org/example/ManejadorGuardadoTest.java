package org.example;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.Assert.*;


public class ManejadorGuardadoTest {

    @Test
    public void testArchivoRecuperadoNoExiste() {
        new File("MiCalendario.txt").delete();
        Calendario calendario = new Calendario();
        assertNull(calendario.recuperarEstado());
        assertEquals("El archivo de recuperado no existe.", calendario.manejador.salida.obtenerLoQueSeImprimio());
    }

    @Test
    public void testArchivoGuardadoNoExiste() {
        Calendario calendario = new Calendario();

        ManejadorGuardado manejador = new ManejadorGuardado();

        manejador.guardarEstado("carpetaInexistente/MICALENDARIO.txt", calendario); // Si envio un directorio valido, FileOutputStream crea
        // el archivo con el nombre enviado y nunca caería en el FileNotFoundException.

        assertEquals("El archivo de guardado no existe.", manejador.salida.obtenerLoQueSeImprimio());
    }

    @Test
    public void testGuardarYRecuperarCalendarioVacio() {
        Calendario calendario1 = new Calendario();
        calendario1.guardarEstado();
        Calendario calendario2 = (new Calendario()).recuperarEstado();
        assertThrows(NullPointerException.class, () -> calendario2.obtenerNombre(0));
    }

    @Test
    public void testGuardadoYOriginalSonIguales() {
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

        LocalDateTime fechaAnteriorAAlarmas = LocalDateTime.of(2023, 4, 28, 0, 0, 0);
        LocalDateTime fechaPosteriorAPrimeraAlarma = LocalDateTime.of(2023, 4, 29, 15, 0, 0);
        LocalDateTime fechaPosteriorAAmbasAlarmas = LocalDateTime.of(2023, 5, 1, 0, 0, 0);

        assertEquals(calendario1.obtenerSiguienteAlarma(fechaAnteriorAAlarmas), calendario2.obtenerSiguienteAlarma(fechaAnteriorAAlarmas));
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma), calendario2.obtenerSiguienteAlarma(fechaPosteriorAPrimeraAlarma));
        assertEquals(calendario1.obtenerSiguienteAlarma(fechaPosteriorAAmbasAlarmas), calendario2.obtenerSiguienteAlarma(fechaPosteriorAAmbasAlarmas));
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
        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.EMAIL, LocalDateTime.of(2023, 4, 29, 12, 0, 0));

        return nuevoCalendario;
    }
}