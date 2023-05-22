package org.example.ElementosCalendario;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Tarea extends ElementoCalendario implements Serializable {

    private boolean completada;

    public Tarea(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
    }

    public void cambiarEstadoTarea() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }
}
