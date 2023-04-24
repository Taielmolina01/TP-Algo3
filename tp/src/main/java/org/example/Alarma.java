package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public class Alarma {

    private efecto efectoProducido;
    private LocalDateTime fechaAbsoluta;
    private TemporalAmount tiempoRelativo;
    private LocalDateTime fechaDisparador;

    public enum efecto {
        NOTIFICACION,
        SONIDO,
        EMAIL,
    }

    public Alarma (efecto efectoProducido, LocalDateTime fechaAbsoluta, TemporalAmount tiempoRelativo) {
        this.efectoProducido = efectoProducido;
        this.fechaAbsoluta = fechaAbsoluta;
        this.tiempoRelativo = tiempoRelativo;
        this.fechaDisparador = fechaAbsoluta.minus(tiempoRelativo);
    }

    public Alarma (efecto efectoProducido, LocalDateTime fechaAbsoluta) {
        this.efectoProducido = efectoProducido;
        this.fechaAbsoluta = fechaAbsoluta;
        this.fechaDisparador = fechaAbsoluta;
    }

    public Duration cuantoFaltaParaDisparar(LocalDateTime fechaActual) {
        return Duration.between(fechaActual, this.fechaDisparador);
    }

    public efecto dispararAlarma() {
        return  efectoProducido;
    }

    public LocalDateTime obtenerFechaActivacion() {
        return this.fechaDisparador;
    }
}
