package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class elementoCalendario {
    protected String nombre;
    protected String descripcion;
    protected LocalDateTime fechaInicio;
    protected final ArrayList<Alarma> alarmas;
    protected Boolean todoElDia;

    public elementoCalendario(String nombre, String descripcion, LocalDateTime fechaInicio, Boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.todoElDia = todoElDia;
        this.definirFechaInicio(fechaInicio);
        this.alarmas = new ArrayList<>();
    }

   private void definirFechaInicio(LocalDateTime fechaInicio) {
        if (this.todoElDia) {
            this.fechaInicio = fechaInicio.toLocalDate().atStartOfDay();
        } else {
            this.fechaInicio = fechaInicio;
        }
    }

    public void agregarAlarma(Alarma alarma) {
        this.alarmas.add(alarma);
    }

    public Boolean getTodoElDia() { return this.todoElDia; }

    public void modificarTodoElDia(Boolean todoElDia) { this.todoElDia = todoElDia; }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public LocalDateTime getFechaInicio() { return this.fechaInicio; }

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