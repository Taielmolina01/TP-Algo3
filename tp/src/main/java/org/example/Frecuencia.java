package org.example;

import java.time.LocalDateTime;

public abstract class Frecuencia {

    protected int frecuenciaRepeticiones;

    public Frecuencia(int frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

    public int getFrecuenciaRepeticiones() {
        return this.frecuenciaRepeticiones;
    }

    public void modificarFrecuenciaRepeticiones(Integer frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

    public abstract LocalDateTime getProximaFecha(LocalDateTime fechaInicial);

}
