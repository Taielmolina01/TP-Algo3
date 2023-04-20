package org.example;

import java.time.LocalDateTime;

public class Tarea extends elementoCalendario {

    private Boolean estaCompletada;

    public Tarea(String nombre, String descripcion, LocalDateTime fechaInicio) {
        super(nombre, descripcion, fechaInicio, false);
        this.estaCompletada = false;
    }


}
