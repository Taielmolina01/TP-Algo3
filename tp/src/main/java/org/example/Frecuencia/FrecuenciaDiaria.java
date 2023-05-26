package org.example.Frecuencia;

import org.example.VisitorFrecuencia;

import java.time.LocalDateTime;

public class FrecuenciaDiaria extends Frecuencia {

    public FrecuenciaDiaria(int frecuenciaRepeticiones) {
        super(frecuenciaRepeticiones);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusDays(this.obtenerValorRepeticion());
    }

    public String obtenerTipoFrecuencia(VisitorFrecuencia visitante) {
        return visitante.obtenerFrecuencia(this);
    }
}
