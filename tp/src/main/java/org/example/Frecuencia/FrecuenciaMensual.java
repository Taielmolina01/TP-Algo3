package org.example.Frecuencia;

import org.example.Visitadores.visitorFrecuencia;

import java.time.LocalDateTime;

public class FrecuenciaMensual extends Frecuencia {

    public FrecuenciaMensual(int frecuenciaMensual) {
        super(frecuenciaMensual);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusMonths(this.obtenerValorRepeticion());
    }

    public String obtenerTipoFrecuencia(visitorFrecuencia v) {
        return v.obtenerTipoFrecuencia(this);
    }
}
