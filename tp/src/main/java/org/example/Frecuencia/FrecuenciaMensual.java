package org.example.Frecuencia;

import org.example.Visitadores.visitorFrecuencia;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FrecuenciaMensual extends Frecuencia implements Serializable {

    public FrecuenciaMensual(int frecuenciaMensual) {
        super(frecuenciaMensual);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusMonths(this.obtenerValorRepeticion());
    }

    @Override
    public void obtenerTipoFrecuencia(visitorFrecuencia v) {
        v.obtenerTipoFrecuencia(this);
    }
}
