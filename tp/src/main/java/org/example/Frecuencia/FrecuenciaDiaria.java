package org.example.Frecuencia;

import org.example.Visitadores.VisitorFrecuencia;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FrecuenciaDiaria extends Frecuencia implements Serializable {

    public FrecuenciaDiaria(int frecuenciaRepeticiones) {
        super(frecuenciaRepeticiones);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusDays(this.obtenerValorRepeticion());
    }

    @Override
    public void obtenerTipoFrecuencia(VisitorFrecuencia v) {
        v.obtenerTipoFrecuencia(this);
    }

    @Override
    public LocalDateTime definirFechaInicio(LocalDateTime fechaInicial) {
        return fechaInicial;
    }
}
