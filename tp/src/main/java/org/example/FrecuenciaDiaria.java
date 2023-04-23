package org.example;

import java.time.LocalDateTime;

public class FrecuenciaDiaria implements Frecuencia {

    private int valorRepeticion;

    public FrecuenciaDiaria(int valorRepeticion) {
        this.valorRepeticion = valorRepeticion;
    }

   @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fecha) {
        return fecha.plusDays(this.valorRepeticion);
    }
}
