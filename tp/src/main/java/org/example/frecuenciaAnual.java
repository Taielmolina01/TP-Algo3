package org.example;

import java.time.LocalDateTime;

public class frecuenciaAnual extends Frecuencia {

    public frecuenciaAnual(Integer frecuenciaAnual) {
        super(frecuenciaAnual);
    }

    @Override
    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusYears(this.frecuenciaRepeticiones);
    }

}
