package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ElementoCalendario {

    private String nombre;
    private String descripcion;
    protected boolean todoElDia;
    protected LocalDateTime fechaInicio;
    private final HashMap<Integer, Alarma> alarmas;
    private int indiceAlarmas;

    public ElementoCalendario(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.todoElDia = todoElDia;
        this.modificarFechaInicio(fechaInicio);
        this.alarmas = new HashMap<>();
    }

    public Alarma agregarAlarma(Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        Alarma alarma = new Alarma(efecto, fechaActivacion);
        this.alarmas.put(indiceAlarmas++, alarma);
        return alarma;
    }

    public Alarma agregarAlarma(Alarma.Efecto efecto, Duration intervaloTiempo) {
        Alarma alarma = new Alarma(efecto, this.obtenerFechaInicio(), intervaloTiempo);
        this.alarmas.put(indiceAlarmas++, alarma);
        return alarma;
    }

    protected HashMap<Integer, Alarma> obtenerAlarmas() {
        return this.alarmas;
    }

    public String obtenerNombre() {
        return this.nombre;
    }

    public String obtenerDescripcion() {
        return this.descripcion;
    }

    public Boolean obtenerTodoElDia() {
        return this.todoElDia;
    }

    public LocalDateTime obtenerFechaInicio() {
        return this.fechaInicio;
    }

    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    public void modificarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void modificarTodoElDia(Boolean todoElDia) {
        this.todoElDia = todoElDia;
        this.modificarFechaInicio(this.fechaInicio);
    }

    public void modificarFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = this.todoElDia ? fechaInicio.toLocalDate().atStartOfDay() : fechaInicio;
    }

    public void modificarNotificacionAlarma(int idAlarma,
                                            Alarma.Efecto nuevoEfecto) {
        this.alarmas.get(idAlarma).modificarEfecto(nuevoEfecto);
    }

    public void modificarFechaActivacionAlarma(int idAlarma, LocalDateTime fechaArbitrariaNueva, Duration intervaloTiempoNuevo) {
        this.alarmas.get(idAlarma).modificarFechaActivacion(fechaArbitrariaNueva, intervaloTiempoNuevo);
    }

    public void modificarFechaActivacionAlarma(int idAlarma, LocalDateTime fechaAbsolutaNueva) {
        this.alarmas.get(idAlarma).modificarFechaActivacion(fechaAbsolutaNueva);
    }

    public void eliminarAlarma(int idAlarma) {
        this.alarmas.remove(idAlarma);
    }
}
