package org.example;

import java.time.LocalDateTime;

public class Tarea extends elementoCalendario {

    private boolean completada;

    public Tarea(String nombre, String descripcion, boolean deDiaCompleto, LocalDateTime fechaInicio) {
        super(nombre, descripcion, deDiaCompleto, fechaInicio);
    }

    public void toggleTarea() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }
}
