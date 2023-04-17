package org.example;

import java.time.DayOfWeek;

public class frecuenciaSemanal extends Frecuencia{

    private Integer cantidadSemanas;
    public frecuenciaSemanal(DayOfWeek[] frecuencia, Integer cantidadSemanas) {
        super(frecuencia);
        this.cantidadSemanas = cantidadSemanas;
    }

    public void modificarCantidadSemanas(Integer cantidadSemanas){
        this.cantidadSemanas = cantidadSemanas;
    }

    public Integer getCantidadSemanas() {
        return this.cantidadSemanas;
    }

}
