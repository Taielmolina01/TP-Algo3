package org.example;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;

public class frecuenciaSemanal extends Frecuencia {

    private DayOfWeek[] diasRepeticion;

    public frecuenciaSemanal(DayOfWeek[] diasRepeticion, Integer frecuenciaDiaria) {
        super(frecuenciaDiaria);
        this.diasRepeticion = diasRepeticion;
    }

    public DayOfWeek[] getDiasRepeticion() {
        return this.diasRepeticion;
    }

    public void modificarDiasRepeticion(DayOfWeek[] diasRepeticion) {
        this.diasRepeticion = diasRepeticion;
    }

}
