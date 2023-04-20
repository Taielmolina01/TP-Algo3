package org.example;

import java.time.LocalDateTime;

public class Alarma {

    private efecto efectoProducido;
    private LocalDateTime fechaAbsoluta;

    public enum efecto {
        NOTIFICACION,
        SONIDO,
        EMAIL,
        NADA,
    }

    public Alarma (efecto efectoProducido, LocalDateTime fechaAbsoluta) {
        this.efectoProducido = efectoProducido;
        this.fechaAbsoluta = fechaAbsoluta;
    }

    public efecto dispararAlarma(LocalDateTime fechaActual) {
        if (fechaActual != fechaAbsoluta) {
            return  efecto.NADA;
        }
        return  efectoProducido;
    }
}
