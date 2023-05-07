package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class FrecuenciaSemanal extends Frecuencia {

    private DayOfWeek[] diasSemana;

    public FrecuenciaSemanal(DayOfWeek[] diasSemana, int frecuenciaSemanal) {
        super(frecuenciaSemanal);
        this.diasSemana = diasSemana;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) { // Está mal
        DayOfWeek fechaDia = fechaInicial.getDayOfWeek();
        DayOfWeek proximoDia = diasSemana[0];

        for (DayOfWeek dia: diasSemana) {
            if (dia.getValue() > fechaDia.getValue()) {
                proximoDia = dia;
                break;
            }
        }

        int saltarSemanas = fechaDia == diasSemana[diasSemana.length - 1] ? 1 : 0;
        return fechaInicial.with(TemporalAdjusters.nextOrSame(proximoDia)).plusWeeks((this.obtenerValorRepeticion() - 1) * saltarSemanas);
    }

    public DayOfWeek[] obtenerDiasSemana() {
        return this.diasSemana;
    }

    public void modificarDiasSemana(DayOfWeek[] diasSemana) {
        this.diasSemana = diasSemana;
    }

}