package org.example;

import java.time.LocalDateTime;

public class FrecuenciaDiaria extends Frecuencia {

    public FrecuenciaDiaria(int frecuenciaRepeticiones) {
        super(frecuenciaRepeticiones);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusDays(this.obtenerValorRepeticion());
    }
}
