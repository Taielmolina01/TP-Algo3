package org.example.Alarma;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Alarma implements Serializable, alarmaClonable {

    private Efecto efectoProducido;
    private LocalDateTime fechaActivacion;

    public Alarma(Efecto efectoProducido, LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
        this.efectoProducido = efectoProducido;
        this.establecerFechas(fechaArbitraria, intervaloTiempo);
    }

    public Alarma(Efecto efectoProducido, LocalDateTime fechaAbsoluta) {
        this.efectoProducido = efectoProducido;
        this.establecerFechas(fechaAbsoluta);
    }

    public static int compararAlarmas(Alarma alarma1, Alarma alarma2) {
        LocalDateTime fechaActivacion1 = alarma1.obtenerFechaActivacion();
        LocalDateTime fechaActivacion2 = alarma2.obtenerFechaActivacion();
        if (fechaActivacion1.isEqual(fechaActivacion2)) {
            return 0;
        } else if (fechaActivacion2.isBefore(fechaActivacion1)) {
            return 1;
        } else {
            return -1;
        }
    }

    public Duration cuantoFaltaParaDisparar(LocalDateTime fechaActual) {
        return Duration.between(fechaActual, this.fechaActivacion);
    }

    public Efecto dispararAlarma() {
        return this.efectoProducido;
    }

    public LocalDateTime obtenerFechaActivacion() {
        return this.fechaActivacion;
    }

    public void modificarEfecto(Efecto nuevoEfecto) {
        this.efectoProducido = nuevoEfecto;
    }

    public void modificarFechaActivacion(LocalDateTime fechaArbitrariaNueva, Duration intervaloTiempoNuevo) {
        this.establecerFechas(fechaArbitrariaNueva, intervaloTiempoNuevo);
    }

    public void modificarFechaActivacion(LocalDateTime fechaAbsolutaNueva) {
        this.establecerFechas(fechaAbsolutaNueva);
    }

    private void establecerFechas(LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
        this.fechaActivacion = fechaArbitraria.minus(intervaloTiempo);
    }

    private void establecerFechas(LocalDateTime fechaAbsoluta) {
        this.fechaActivacion = fechaAbsoluta;
    }

    public alarmaClonable clonar() {
        Alarma clonAlarma = null;
        try {
            clonAlarma = (Alarma) clone();
        } catch (CloneNotSupportedException e) {
            //
        }
        return clonAlarma;
    }

    public enum Efecto {
        NOTIFICACION,
        SONIDO,
        EMAIL,
    }
}
