package org.example;

import java.time.LocalDateTime;

public abstract class Frecuencia {
    protected Integer frecuenciaRepeticiones;

    public Frecuencia(Integer frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

    public Integer getFrecuenciaRepeticiones() {
        return this.frecuenciaRepeticiones;
    }

    public void modificarFrecuenciaRepeticiones(Integer frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial;
    }

}
