package org.example.Frecuencia;

import org.example.Visitadores.VisitorFrecuencia;

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

    @Override
    public void obtenerTipoFrecuencia(VisitorFrecuencia v) {
        v.obtenerTipoFrecuencia(this);
    }
}
