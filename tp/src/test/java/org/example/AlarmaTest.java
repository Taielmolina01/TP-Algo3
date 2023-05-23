package org.example;

import org.example.Alarma.Alarma;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class AlarmaTest {

    @Test
    public void testCrearAlarmaFechaAbsoluta() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var otraFecha = LocalDateTime.of(1, 1, 1, 1, 1);
        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);
        assertEquals(fechaAbsoluta, alarma.obtenerFechaActivacion());
    }

    @Test
    public void testCrearAlarmaTiempoRelativo() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var fechaDisparador = LocalDateTime.of(2019, 12, 31, 23, 30);
        var tiempo = Duration.of(30, ChronoUnit.MINUTES);
        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta, tiempo);
        assertEquals(fechaDisparador, alarma.obtenerFechaActivacion());
    }

    @Test
    public void testCuantoFaltaAntesDeFechaAlarma() {
        var fechaAntes = LocalDateTime.of(2020, 1, 5, 0, 0, 0, 0);
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(5, alarma.cuantoFaltaParaDisparar(fechaAntes).toDays());

        assertTrue(alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isZero());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isNegative());
    }

    @Test
    public void testCuantoFaltaEnFechaAlarma() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(0, alarma.cuantoFaltaParaDisparar(fechaAbsoluta).toDays());

        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isPositive());
        assertTrue(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isZero());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isNegative());
    }

    @Test
    public  void testCuantoFaltaDespuesDeFechaAlarma() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);
        var fechaDespues = LocalDateTime.of(2020, 1, 15, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(-5, alarma.cuantoFaltaParaDisparar(fechaDespues).toDays());

        assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isPositive());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isZero());
        assertTrue(alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative());
    }

    @Test
    public void testDispararAlarmaNotificacion() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);
        assertEquals(Alarma.Efecto.NOTIFICACION, alarma.dispararAlarma());
    }

    @Test
    public void testDispararAlarmaSonido() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.Efecto.SONIDO, fechaAbsoluta);
        assertEquals(Alarma.Efecto.SONIDO, alarma.dispararAlarma());
    }

    @Test
    public void testDispararAlarmaEmailTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.Efecto.EMAIL, fechaAbsoluta);
        assertEquals(Alarma.Efecto.EMAIL, alarma.dispararAlarma());
    }

    @Test
    public void testIntentarDispararAlarmaAntesDeFecha() {
        var fechaAntes = LocalDateTime.of(2020, 1, 5, 00, 0, 0, 0);
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 00, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        if (alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive()) {
            assertTrue(true);
        } else if (alarma.cuantoFaltaParaDisparar(fechaAntes).isZero()) {
            assertTrue(false);
        } else if (alarma.cuantoFaltaParaDisparar(fechaAntes).isNegative()) {
            assertTrue(false);
        }
    }

    @Test
    public void testIntentarDispararAlarmaEnFecha() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isPositive()) {
            assertTrue(false);
        } else if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isZero()) {
            assertEquals(Alarma.Efecto.NOTIFICACION, alarma.dispararAlarma());
        } else if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isNegative()) {
            assertTrue(false);
        }
    }

    @Test
    public void testIntentarDispararAlarmaDespuesDeFecha() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 00, 0, 0, 0);
        var fechaDespues = LocalDateTime.of(2020, 1, 15, 00, 0, 0, 0);

        var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

        if (alarma.cuantoFaltaParaDisparar(fechaDespues).isPositive()) {
            assertTrue(false);
        } else if (alarma.cuantoFaltaParaDisparar(fechaDespues).isZero()) {
            assertTrue(false);
        } else if (alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative()) {
            assertTrue(true);
        }
    }

    @Test
    public void testModificarAlarmas() {
        var fechaAbsoluta = LocalDateTime.of(2023, 5, 10, 0, 0, 0, 0);
        var fechaDisparador = LocalDateTime.of(2023, 5, 9, 23, 30, 0);
        var tiempo = Duration.of(30, ChronoUnit.MINUTES);
        var efecto = Alarma.Efecto.NOTIFICACION;
        var alarma = new Alarma(efecto, fechaAbsoluta, tiempo);

        assertEquals(fechaDisparador, alarma.obtenerFechaActivacion());
        assertEquals(efecto, alarma.dispararAlarma());

        var fechaNueva = LocalDateTime.of(2023, 5, 8, 0, 0, 0);
        var efectoNuevo = Alarma.Efecto.SONIDO;

        alarma.modificarFechaActivacion(fechaNueva);
        alarma.modificarEfecto(efectoNuevo);

        assertEquals(fechaNueva, alarma.obtenerFechaActivacion());
        assertEquals(efectoNuevo, alarma.dispararAlarma());
    }
}