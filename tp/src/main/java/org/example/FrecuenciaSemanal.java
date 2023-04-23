package org.example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class FrecuenciaSemanal implements Frecuencia {

    private DayOfWeek[] diasSemana;

    public FrecuenciaSemanal(DayOfWeek[] diasSemana) {
        this.diasSemana = diasSemana;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fecha) { // Está mal
        DayOfWeek fechaDia = fecha.getDayOfWeek();
        DayOfWeek proximoDia = diasSemana[0];

        for (DayOfWeek dia: diasSemana) {
            if (dia.ordinal() > fechaDia.ordinal()) {
                proximoDia = dia;
                break;
            }
        }

        return fecha.with(TemporalAdjusters.nextOrSame(proximoDia));
    }

    public void modificarDiasSemana(DayOfWeek[] nuevosDiasSemana) {
        this.diasSemana = nuevosDiasSemana;
    }

}
