package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class ElementoCalendario {

    private String nombre;
    private String descripcion;
    protected boolean todoElDia;
    protected LocalDateTime fechaInicio;
    private final ArrayList<Alarma> alarmas;

    public ElementoCalendario(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.todoElDia = todoElDia;
        this.modificarFechaInicio(fechaInicio);
        this.alarmas = new ArrayList<>();
    }

    public Alarma agregarAlarma(Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        Alarma alarma = new Alarma(efecto, fechaActivacion);
        this.alarmas.add(alarma);
        return alarma;
    }

    public Alarma agregarAlarma(Alarma.Efecto efecto, Duration intervaloTiempo) {
        Alarma alarma = new Alarma(efecto, this.obtenerFechaInicio(), intervaloTiempo);
        this.alarmas.add(alarma);
        return alarma;
    }

    protected ArrayList<Alarma> obtenerAlarmas() { return this.alarmas; }

    public String obtenerNombre() { return this.nombre; }

    public String obtenerDescripcion() { return this.descripcion; }

    public Boolean obtenerTodoElDia() { return this.todoElDia; }

    public LocalDateTime obtenerFechaInicio() { return this.fechaInicio; }

    public void modificarNombre(String nombre) { this.nombre = nombre; }

    public void modificarDescripcion(String descripcion) {  this.descripcion = descripcion; }

    public void modificarTodoElDia(Boolean todoElDia) {
        this.todoElDia = todoElDia;
        this.modificarFechaInicio(this.fechaInicio);
    }

    public void modificarFechaInicio(LocalDateTime fechaInicio) {
        if (this.todoElDia) {
            this.fechaInicio = fechaInicio.toLocalDate().atStartOfDay();
        } else {
            this.fechaInicio = fechaInicio;
        }
    }
}