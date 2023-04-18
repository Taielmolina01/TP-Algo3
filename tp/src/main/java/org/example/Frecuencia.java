package org.example;

public abstract class Frecuencia {
    private Integer frecuenciaRepeticiones;

    public Frecuencia(Integer frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

    public Integer getFrecuenciaRepeticiones() {
        return this.frecuenciaRepeticiones;
    }

    public void modificarFrecuenciaRepeticiones(Integer frecuenciaRepeticiones) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
    }

}
