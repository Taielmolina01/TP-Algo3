package org.example;

import java.time.LocalDateTime;

public class FrecuenciaAnual extends Frecuencia {

    public FrecuenciaAnual(int frecuenciaAnual) {
        super(frecuenciaAnual);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusYears(this.obtenerValorRepeticion());
    }

}
