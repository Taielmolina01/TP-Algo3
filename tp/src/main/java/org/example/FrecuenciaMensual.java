package org.example;

import java.time.LocalDateTime;

public class FrecuenciaMensual extends Frecuencia {

    public FrecuenciaMensual(int frecuenciaMensual) {
        super(frecuenciaMensual);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusMonths(this.obtenerValorRepeticion());
    }
}
