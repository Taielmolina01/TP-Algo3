package org.example;

import java.time.LocalDateTime;

public class FrecuenciaAnual implements Frecuencia {

    private final int valorRepeticion;

    public FrecuenciaAnual() {
        this.valorRepeticion = 1;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fecha) {
        return fecha.plusYears(this.valorRepeticion);
    }

}
