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
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);
        var otraFecha = LocalDateTime.of(1,1,1,1,1);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);

        assertEquals(Alarma.efecto.NOTIFICACION, alarma.dispararAlarma(fechaAbsoluta));
        assertEquals(Alarma.efecto.NADA, alarma.dispararAlarma(otraFecha));
    }

    @Test
    public void crearAlarmaTiempoRelativoTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);
        var fechaDisparador = LocalDateTime.of(2019,12,31,23,30);
        var tiempo = Duration.of(30, ChronoUnit.MINUTES);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta, tiempo);

        assertEquals(Alarma.efecto.NOTIFICACION, alarma.dispararAlarma(fechaDisparador));
        assertEquals(Alarma.efecto.NADA, alarma.dispararAlarma(fechaAbsoluta));
    }

    @Test
    public void dispararAlarmaNotificacionTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.NOTIFICACION, fechaAbsoluta);
        assertEquals(Alarma.efecto.NOTIFICACION, alarma.dispararAlarma(fechaAbsoluta));
    }

    @Test
    public void dispararAlarmaSonidoTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.SONIDO, fechaAbsoluta);
        assertEquals(Alarma.efecto.SONIDO, alarma.dispararAlarma(fechaAbsoluta));
    }

    @Test
    public void dispararAlarmaEmailTest() {
        var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);
        var alarma = new Alarma(Alarma.efecto.EMAIL, fechaAbsoluta);
        assertEquals(Alarma.efecto.EMAIL, alarma.dispararAlarma(fechaAbsoluta));
    }
}