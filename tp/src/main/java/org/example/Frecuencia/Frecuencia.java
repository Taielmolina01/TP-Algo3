package org.example.Frecuencia;


import org.example.Visitadores.visitorFrecuencia;

import java.time.LocalDateTime;

public abstract class Frecuencia {

    private int valorRepeticion;

    public Frecuencia(int frecuenciaRepeticiones) {
        this.valorRepeticion = frecuenciaRepeticiones;
    }

    public int obtenerValorRepeticion() {
        return this.valorRepeticion;
    }

    public void modificarValorRepeticion(int valorRepeticion) {
        this.valorRepeticion = valorRepeticion;
    }

    public abstract LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial);

    public abstract String obtenerTipoFrecuencia(visitorFrecuencia visitante);
}
