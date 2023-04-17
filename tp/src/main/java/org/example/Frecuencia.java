package org.example;

import java.time.DayOfWeek;

public class Frecuencia {

    protected DayOfWeek[] frecuencia;

    public Frecuencia(DayOfWeek[] frecuencia){
        this.frecuencia = frecuencia;
    }

    public DayOfWeek[] getFrecuencia() {
        return this.frecuencia;
    }

    public void modificarFrecuencia(DayOfWeek[] frecuencia) {
        this.frecuencia = frecuencia;
    }
}
