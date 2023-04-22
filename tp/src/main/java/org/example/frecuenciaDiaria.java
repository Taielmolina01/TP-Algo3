package org.example;

import java.time.LocalDateTime;

public class frecuenciaDiaria extends Frecuencia {

    public frecuenciaDiaria(Integer frecuenciaRepeticiones) {
        super(frecuenciaRepeticiones);
    }

   @Override
    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusDays(this.frecuenciaRepeticiones);
    }
}
