package org.example;

import java.time.LocalDateTime;

public class FrecuenciaMensual implements Frecuencia {

    private final int valorRepeticion;

    public  FrecuenciaMensual() {
        valorRepeticion = 1;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fecha) {
        return fecha.plusMonths(this.valorRepeticion);
    }
}
