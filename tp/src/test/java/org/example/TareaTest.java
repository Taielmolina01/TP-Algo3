package org.example;

import org.example.Actividades.Tarea;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void testGetNombre() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descripcion", fecha, true);
        assertEquals("nombre", tarea.obtenerNombre());
    }

    @Test
    public void testGetDescripcion() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descripcion", fecha, true);
        assertEquals("descripcion", tarea.obtenerDescripcion());
    }

    @Test
    public void testObtenerTodoElDia() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descipcion", fecha, true);
        assertTrue(tarea.obtenerTodoElDia());
    }

    @Test
    public void testObtenerFechaTarea() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descipcion", fecha, true);
        assertEquals(fecha, tarea.obtenerFechaInicio());
    }

    @Test
    public void testModificarNombre() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descripcion", fecha, true);
        tarea.modificarNombre("nuevo nombre");
        assertEquals("nuevo nombre", tarea.obtenerNombre());
    }

    @Test
    public void testModificarDescripcion() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descripcion", fecha, true);
        tarea.modificarDescripcion("nueva descripcion");
        assertEquals("nueva descripcion", tarea.obtenerDescripcion());
    }

    @Test
    public void testModificarTodoElDia() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descipcion", fecha, true);

        tarea.modificarTodoElDia(false);
        assertFalse(tarea.obtenerTodoElDia());

        tarea.modificarTodoElDia(true);
        assertTrue(tarea.obtenerTodoElDia());
    }

    @Test
    public void testModificarFechaTarea() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var nuevaFecha = LocalDateTime.of(2023, 4, 24, 0, 0);
        var tarea = new Tarea(0, "nombre", "descipcion", fecha, true);

        tarea.modificarFechaInicio(nuevaFecha);
        assertEquals(nuevaFecha, tarea.obtenerFechaInicio());
        assertNotEquals(fecha, tarea.obtenerFechaInicio());
    }

    @Test
    public void testCambiarEstadoTarea() {
        var fecha = LocalDateTime.of(2020, 1, 1, 0, 0);
        var tarea = new Tarea(0, "nombre", "descripcion", fecha, true);
        assertFalse(tarea.estaCompletada());
        tarea.cambiarEstadoCompletado();
        assertTrue(tarea.estaCompletada());
        tarea.cambiarEstadoCompletado();
        assertFalse(tarea.estaCompletada());
    }
}