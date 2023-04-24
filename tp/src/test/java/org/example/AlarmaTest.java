package org.example;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;

import static org.junit.Assert.*;

public class AlarmaTest {

    @Test
    public void crearAlarmaFechaAbsolutaTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var otraFecha = LocalDateTime.of(1, 1, 1, 1, 1);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);
    }

    @Test
    public void testCrearAlarmaTiempoRelativo() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var fechaDisparador = LocalDateTime.of(2019, 12, 31, 23, 30);
        var tiempo = Duration.of(30, ChronoUnit.MINUTES);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta, tiempo);
    }

    @Test
    public void testCuantoFaltaAntesDeFechaAlarma() {
        var fechaAntes = LocalDateTime.of(2020, 1, 5, 0, 0, 0, 0);
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(5, alarma.cuantoFaltaParaDisparar(fechaAntes).toDays());

        assertTrue(alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isZero());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isNegative());
    }

    @Test
    public void testCuantoFaltaEnFechaAlarma() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(0, alarma.cuantoFaltaParaDisparar(fechaAbsoluta).toDays());

        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isPositive());
        assertTrue(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isZero());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isNegative());
    }

    @Test
    public  void testCuantoFaltaDespuesDeFechaAlarma() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);
        var fechaDespues = LocalDateTime.of(2020, 1, 15, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(-5, alarma.cuantoFaltaParaDisparar(fechaDespues).toDays());

        assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isPositive());
        assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isZero());
        assertTrue(alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative());
    }

    @Test
    public void testDispararAlarmaNotificacion() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);
        assertEquals(Alarma.efecto.NOTIFICACION, alarma.dispararAlarma());
    }

    @Test
    public void testDispararAlarmaSonido() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.SONIDO, fechaAbsoluta);
        assertEquals(Alarma.efecto.SONIDO, alarma.dispararAlarma());
    }

    @Test
    public void testDispararAlarmaEmailTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.EMAIL, fechaAbsoluta);
        assertEquals(Alarma.efecto.EMAIL, alarma.dispararAlarma());
    }

    @Test
    public void testIntentarDispararAlarmaAntesDeFecha() {
        var fechaAntes = LocalDateTime.of(2020, 1, 5, 0, 0, 0, 0);
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertTrue(alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive());
    }

    @Test
    public void testIntentarDispararAlarmaEnFecha() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(Alarma.efecto.NOTIFICACION, alarma.dispararAlarma());
    }

    @Test
    public void testIntentarDispararAlarmaDespuesDeFecha() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 00, 0, 0, 0);
        var fechaDespues = LocalDateTime.of(2020, 1, 15, 00, 0, 0, 0);

        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertTrue(alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative());
    }
}