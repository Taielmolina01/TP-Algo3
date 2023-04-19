package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void getNombreTest() {
        var t = new Tarea("nombre", "descripcion", null);
        assertEquals("nombre", t.getNombre());
    }

    @Test
    public void getDescripcionTest() {
        var t = new Tarea("nombre", "descripcion", null);
        assertEquals("descripcion", t.getDescripcion());
    }

    @Test
    public void modificarNombreTest() {
        var t = new Tarea("nombre", "descripcion", null);
        t.modificarNombre("nuevo nombre");
        assertEquals("nuevo nombre", t.getNombre());
    }

    @Test
    public void modificarDescripcionTest() {
        var t = new Tarea("nombre", "descripcion", null);
        t.modificarDescripcion("nueva descripcion");
        assertEquals("nueva descripcion", t.getDescripcion());
    }

    @Test
    public void toggleTareaTest() {
        var t = new Tarea("nombre", "descripcion", null);
        assertFalse(t.estaCompletada());
        t.toggleTarea();
        assertTrue(t.estaCompletada());
        t.toggleTarea();
        assertFalse(t.estaCompletada());
    }
}