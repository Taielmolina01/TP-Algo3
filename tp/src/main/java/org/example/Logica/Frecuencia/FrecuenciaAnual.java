package org.example.Frecuencia;

import org.example.Visitadores.visitorFrecuencia;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FrecuenciaAnual extends Frecuencia implements Serializable {

    public FrecuenciaAnual(int frecuenciaAnual) {
        super(frecuenciaAnual);
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        return fechaInicial.plusYears(this.obtenerValorRepeticion());
    }

    public String obtenerTipoFrecuencia(visitorFrecuencia v) {
        return v.obtenerTipoFrecuencia(this);
    }
}
