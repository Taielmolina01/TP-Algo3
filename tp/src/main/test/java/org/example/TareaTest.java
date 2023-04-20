package org.example;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void getNombreTest() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", true, fecha);
        assertEquals("nombre", tarea.getNombre());
    }

    @Test
    public void getDescripcionTest() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", true, fecha);
        assertEquals("descripcion", tarea.getDescripcion());
    }

    @Test
    public void modificarNombreTest() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", true, fecha);
        tarea.modificarNombre("nuevo nombre");
        assertEquals("nuevo nombre", tarea.getNombre());
    }

    @Test
    public void modificarDescripcionTest() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", true, fecha);
        tarea.modificarDescripcion("nueva descripcion");
        assertEquals("nueva descripcion", tarea.getDescripcion());
    }

    @Test
    public void toggleTareaTest() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea("nombre", "descripcion", true, fecha);
        assertFalse(tarea.estaCompletada());
        tarea.toggleTarea();
        assertTrue(tarea.estaCompletada());
        tarea.toggleTarea();
        assertFalse(tarea.estaCompletada());
    }
}