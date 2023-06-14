package org.example.Frecuencia;

import org.example.Visitadores.VisitorFrecuencia;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.SortedSet;

public class FrecuenciaSemanal extends Frecuencia implements Serializable {

    private SortedSet<DayOfWeek> diasSemana;

    public FrecuenciaSemanal(SortedSet<DayOfWeek> diasSemana, int frecuenciaSemanal) {
        super(frecuenciaSemanal);
        this.diasSemana = diasSemana;
    }

    @Override
    public LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial) {
        DayOfWeek fechaDia = fechaInicial.getDayOfWeek();
        DayOfWeek proximoDia = this.diasSemana.first();

        for (DayOfWeek dia : DayOfWeek.values()) {
            if (this.diasSemana.contains(dia) && dia.getValue() > fechaDia.getValue()) {
                proximoDia = dia;
                break;
            }
        }

        int saltarSemanas = fechaDia == this.diasSemana.last() ? 1 : 0;
        return fechaInicial.with(TemporalAdjusters.nextOrSame(proximoDia)).plusWeeks((this.obtenerValorRepeticion() - 1) * saltarSemanas);
    }

    public SortedSet<DayOfWeek> obtenerDiasSemana() {
        return this.diasSemana;
    }

    public void modificarDiasSemana(SortedSet<DayOfWeek> diasSemana) {
        this.diasSemana = diasSemana;
    }

    @Override
    public void obtenerTipoFrecuencia(VisitorFrecuencia v) {
        v.obtenerTipoFrecuencia(this);
    }
}