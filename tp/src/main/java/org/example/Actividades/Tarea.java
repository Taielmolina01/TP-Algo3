package org.example.Actividades;

import org.example.Visitadores.visitadorElementosCalendario;
import org.example.Visitadores.visitorElementos;

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

    public ArrayList<LocalDateTime> elementosEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return new ArrayList<>(Arrays.asList(this.fechaInicio));
    }
    public String obtenerInfoResumida(visitorElementos visitante) {
        return visitante.obtenerInfoResumida(this);
    }

    public String obtenerInfoCompleta(visitorElementos visitante) {
        return visitante.obtenerInfoCompleta(this);
    }

    @Override
    public visitadorElementosCalendario.colorFondo obtenerColor(visitorElementos visitante) {
        return visitante.obtenerColor(this);
    }
}
