package org.example.Actividades;

import org.example.Visitadores.visitadorActividades;
import org.example.Visitadores.visitorActividades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Tarea extends Actividad implements Serializable {

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

    public ArrayList<LocalDateTime> actividadesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return new ArrayList<>(Arrays.asList(this.obtenerFechaInicio()));
    }

    public String obtenerInfoResumida(visitorActividades visitante) {
        return visitante.obtenerInfoResumida(this);
    }

    public String obtenerInfoCompleta(visitorActividades visitante) {
        return visitante.obtenerInfoCompleta(this);
    }

    @Override
    public visitadorActividades.colorFondo obtenerColor(visitorActividades visitante) {
        return visitante.obtenerColor(this);
    }
}
