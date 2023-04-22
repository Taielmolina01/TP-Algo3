package org.example;

import java.time.LocalDateTime;

public class frecuenciaMensual extends Frecuencia {

    public frecuenciaMensual(Integer frecuenciaMensual) {
        super(frecuenciaMensual);
    }

    @Override
    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusMonths(this.frecuenciaRepeticiones);
    }

}
