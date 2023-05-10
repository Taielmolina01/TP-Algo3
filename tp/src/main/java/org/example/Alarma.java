package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Alarma {

    private Efecto efectoProducido;
    private LocalDateTime fechaArbitraria;
    private LocalDateTime fechaActivacion;

    public enum Efecto {
        NOTIFICACION,
        SONIDO,
        EMAIL,
    }

    public Alarma(Efecto efectoProducido, LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
        this.efectoProducido = efectoProducido;
        this.establecerFechas(fechaArbitraria, intervaloTiempo);
    }

    public Alarma(Efecto efectoProducido, LocalDateTime fechaAbsoluta) {
        this.efectoProducido = efectoProducido;
        this.establecerFechas(fechaAbsoluta);
    }

    public Duration cuantoFaltaParaDisparar(LocalDateTime fechaActual) {
        return Duration.between(fechaActual, this.fechaActivacion);
    }

    public Efecto dispararAlarma() {
        return efectoProducido;
    }

    public LocalDateTime obtenerFechaActivacion() {
        return this.fechaActivacion;
    }

    protected static int compararAlarmas(Alarma alarma1, Alarma alarma2) {
        LocalDateTime fechaArbitraria = LocalDateTime.of(2000, 1, 1, 0, 0);
        var duracion1 = alarma1.cuantoFaltaParaDisparar(fechaArbitraria);
        var duracion2 = alarma2.cuantoFaltaParaDisparar(fechaArbitraria);
        if (duracion1.minus(duracion2).isZero()) {
            return 0;
        } else if (duracion1.minus(duracion2).isPositive()) {
            return 1;
        } else {
            return -1;
        }
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
        this.fechaArbitraria = fechaArbitraria;
        this.fechaActivacion = fechaArbitraria.minus(intervaloTiempo);
    }

    private void establecerFechas(LocalDateTime fechaAbsoluta) {
        this.fechaArbitraria = fechaAbsoluta;
        this.fechaActivacion = fechaAbsoluta;
    }
}
