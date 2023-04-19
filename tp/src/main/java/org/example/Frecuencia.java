package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Frecuencia {
    private Integer frecuenciaRepeticiones;
    private frecuencia tipoDeFrecuencia;

    public Frecuencia(Integer frecuenciaRepeticiones, frecuencia tipoDeFrecuencia) {
        this.frecuenciaRepeticiones = frecuenciaRepeticiones;
        this.tipoDeFrecuencia = tipoDeFrecuencia;
    }

    public frecuencia getTipoDeFrecuencia() {
        return this.tipoDeFrecuencia;
    }

    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial) {
        return this.tipoDeFrecuencia.getProximaFecha(fechaInicial, this.frecuenciaRepeticiones);
    }

    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, DayOfWeek[] diasSemana) {
        return this.tipoDeFrecuencia.getProximaFecha(fechaInicial, this.frecuenciaRepeticiones, diasSemana);
    }

}
