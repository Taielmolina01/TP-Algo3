package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class elementoCalendario {

    private String nombre;
    private String descripcion;

    protected boolean deDiaCompleto;
    protected LocalDateTime fechaInicio;

    private final ArrayList<Alarma> alarmas;

    public elementoCalendario(String nombre, String descripcion, boolean deDiaCompleto, LocalDateTime fechaInicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.deDiaCompleto = deDiaCompleto;
        this.fechaInicio = fechaInicio;
        this.alarmas = new ArrayList<>();
    }

    public void agregarAlarma(Alarma alarma) {
        this.alarmas.add(alarma);
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    public void modificarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void modificarFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}