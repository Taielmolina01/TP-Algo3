package org.example;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void testGetNombre() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", fecha, true);
        assertEquals("nombre", tarea.obtenerNombre());
    }

    @Test
    public void testGetDescripcion() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", fecha, true);
        assertEquals("descripcion", tarea.obtenerDescripcion());
    }

    @Test
    public void testModificarNombre() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", fecha, true);
        tarea.modificarNombre("nuevo nombre");
        assertEquals("nuevo nombre", tarea.obtenerNombre());
    }

    @Test
    public void testModificarDescripcion() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", fecha, true);
        tarea.modificarDescripcion("nueva descripcion");
        assertEquals("nueva descripcion", tarea.obtenerDescripcion());
    }

    @Test
    public void testCambiarEstadoTarea() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", fecha, true);
        assertFalse(tarea.estaCompletada());
        tarea.cambiarEstadoTarea();
        assertTrue(tarea.estaCompletada());
        tarea.cambiarEstadoTarea();
        assertFalse(tarea.estaCompletada());
    }
}