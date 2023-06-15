package org.example.Frecuencia;

import org.example.Visitadores.VisitorFrecuencia;

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
    public void obtenerTipoFrecuencia(VisitorFrecuencia v) {
        v.obtenerTipoFrecuencia(this);
    }

    @Override
    public LocalDateTime definirFechaInicio(LocalDateTime fechaInicial) {
        return fechaInicial;
    }
}
