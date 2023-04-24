package org.example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class FrecuenciaSemanal extends Frecuencia {

    private DayOfWeek[] diasSemana;

    public FrecuenciaSemanal(DayOfWeek[] diasSemana, int frecuenciaSemanal) {
        super(frecuenciaSemanal);
        this.diasSemana = diasSemana;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) { // Está mal
        DayOfWeek diaActual = fechaInicial.getDayOfWeek();
        LocalDateTime fechaProxima = null;
        for (int i = 0; i < this.diasSemana.length; i++) {
            DayOfWeek dia = this.diasSemana[i];
            if (diaActual == dia) {
                if (i != this.diasSemana.length - 1) {
                    fechaProxima = fechaInicial.plusDays(this.diasSemana[i+1].getValue() - diaActual.getValue());
                } else {
                    fechaProxima = fechaInicial.plusDays((diaActual.getValue() - this.diasSemana[0].getValue() - 1) % 8 + ((this.obtenerValorRepeticion() - 1) * 7));
                }
            }
        }
        return fechaProxima;
    }

    public DayOfWeek[] obtenerDiasSemana() {
        return this.diasSemana;
    }

    public void modificarDiasSemana(DayOfWeek[] diasSemana) {
        this.diasSemana = diasSemana;
    }

}
