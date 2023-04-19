package org.example;

import java.time.LocalDateTime;

public class Tarea extends elementoCalendario {

    private boolean completada;

    public Tarea(String nombre, String descripcion, LocalDateTime fechaInicio) {
        super(nombre, descripcion, true, fechaInicio);
    }

    public void toggleTarea() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }
}
