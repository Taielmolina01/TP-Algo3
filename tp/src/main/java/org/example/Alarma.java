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
}
