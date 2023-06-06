package org.example.Actividades;

import org.example.Visitadores.visitadorActividades;
import org.example.vistaActividad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Tarea extends Actividad implements Serializable {

    private boolean completada;

    public Tarea(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        super(ID, nombre, descripcion, fechaInicio, todoElDia);
    }

    public void cambiarEstadoTarea() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }

    public ArrayList<LocalDateTime> actividadesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return new ArrayList<>(Collections.singletonList(this.obtenerFechaInicio()));
    }

    @Override
    public vistaActividad visitarActividad(visitadorActividades v) {
        return v.visitarActividad(this);
    }
}
