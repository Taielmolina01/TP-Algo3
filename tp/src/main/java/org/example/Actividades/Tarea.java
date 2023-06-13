package org.example.Actividades;

import org.example.Visitadores.visitadorActividades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Tarea extends Actividad implements Serializable {

    private boolean completada;

    public Tarea(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
    }

    public void cambiarEstadoCompletado() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }

    public ArrayList<Actividad> actividadesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        if (estaEntreFechas(this.obtenerFechaInicio(), fechaInicio, fechaFinal)) {
            return new ArrayList<>(Collections.singletonList(this));
        }
        return new ArrayList<>();
    }

    @Override
    public void visitarActividad(visitadorActividades v) {
        v.visitarActividad(this);
    }
}
