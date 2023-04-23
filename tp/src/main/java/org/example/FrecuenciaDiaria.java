package org.example;

import java.time.LocalDateTime;

public class FrecuenciaDiaria extends Frecuencia {

    public FrecuenciaDiaria(Integer frecuenciaRepeticiones) {
        super(frecuenciaRepeticiones);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusDays(this.valorRepeticion);
    }
}
