package org.example;

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
        NADA,
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

    public efecto dispararAlarma(LocalDateTime fechaActual) {
        if (!fechaActual.isEqual(fechaDisparador)) {
            return efecto.NADA;
        }
        return efectoProducido;
    }
}
