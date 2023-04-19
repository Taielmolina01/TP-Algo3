package org.example;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public class Alarma {

    private LocalDateTime fechaAbsoluta;
    private TemporalAmount tiempoRelativo;
    private LocalDateTime fechaDisparador;

    public Alarma(LocalDateTime fechaAbsoluta, TemporalAmount tiempoRelativo) {
        this.fechaAbsoluta = fechaAbsoluta;
        this.tiempoRelativo = tiempoRelativo;
        this.fechaDisparador = fechaAbsoluta.minus(tiempoRelativo);
    }

    public void dispararAlarma() {

    }
}
