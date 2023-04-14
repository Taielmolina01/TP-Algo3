package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void tareaTituloTest() {
        Tarea t = new Tarea("Nueva Tarea", "");
        assertEquals("Nueva Tarea", t.verTitulo());
    }

    @Test
    public void tareaDescripcionTest() {
        Tarea t = new Tarea("", "Esta es la descripcion de una tarea");
        assertEquals("Esta es la descripcion de una tarea", t.verDescripcion());
    }

    @Test
    public void completarTareaTest() {
        Tarea t = new Tarea("", "");

        t.toggleTarea();
        assertTrue(t.estaCompletada());

        t.toggleTarea();
        assertFalse(t.estaCompletada());
    }

}