package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Alarma {

    private final Efecto efectoProducido;
    private final LocalDateTime fechaArbitraria;
    private final LocalDateTime fechaActivacion;

    public enum Efecto {
        NOTIFICACION,
        SONIDO,
        EMAIL,
    }

    public Alarma (Efecto efectoProducido, LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
        this.efectoProducido = efectoProducido;
        this.fechaArbitraria = fechaArbitraria;
        this.fechaActivacion = fechaArbitraria.minus(intervaloTiempo);
    }

    public Alarma (Efecto efectoProducido, LocalDateTime fechaAbsoluta) {
        this.efectoProducido = efectoProducido;
        this.fechaArbitraria = fechaAbsoluta;
        this.fechaActivacion = fechaAbsoluta;
    }

    public Duration cuantoFaltaParaDisparar(LocalDateTime fechaActual) {
        return Duration.between(fechaActual, this.fechaActivacion);
    }

    public Efecto dispararAlarma() {
        return  efectoProducido;
    }

    public LocalDateTime obtenerFechaActivacion() {
        return this.fechaActivacion;
    }
}
