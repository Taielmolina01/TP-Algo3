package org.example.Actividades;

import org.example.Alarma.Alarma;
import org.example.Visitadores.visitadorActividades;
import org.example.VistaActividades.vistaActividad;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Actividad implements Serializable {
    private final HashMap<Integer, Alarma> alarmas;
    private final int ID;
    private String nombre;
    private String descripcion;
    private boolean todoElDia;
    private LocalDateTime fechaInicio;
    private int indiceAlarmas;

    public Actividad(int ID, String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.todoElDia = todoElDia;
        this.modificarFechaInicio(fechaInicio);
        this.alarmas = new HashMap<>();
    }

    public static boolean estaEntreFechas(LocalDateTime diaAAnalizar, LocalDateTime diaInicio, LocalDateTime diaFin) {
        return diaAAnalizar.equals(diaInicio) || diaAAnalizar.equals(diaFin) || (diaAAnalizar.isAfter(diaInicio) && diaAAnalizar.isBefore(diaFin));
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

    public HashMap<Integer, Alarma> obtenerAlarmas() {
        return this.alarmas;
    }

    public Alarma obtenerAlarma(int id) {
        return this.alarmas.get(id);
    }

    public int obtenerID() {
        return this.ID;
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

    public void modificarNotificacionAlarma(int idAlarma, Alarma.Efecto nuevoEfecto) {
        this.alarmas.get(idAlarma).modificarEfecto(nuevoEfecto);
    }

    public void modificarFechaActivacionAlarma(int idAlarma, LocalDateTime fechaArbitrariaNueva, Duration intervaloTiempoNuevo) {
        this.alarmas.get(idAlarma).modificarFechaActivacion(fechaArbitrariaNueva, intervaloTiempoNuevo);
    }

    public void modificarFechaActivacionAlarma(int idAlarma, LocalDateTime fechaAbsolutaNueva) {
        this.alarmas.get(idAlarma).modificarFechaActivacion(fechaAbsolutaNueva);
    }

    public Alarma eliminarAlarma(int idAlarma) {
        return this.alarmas.remove(idAlarma);
    }

    public abstract ArrayList<Actividad> actividadesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    public abstract vistaActividad visitarActividad(visitadorActividades v);
}
