package org.example.VistaActividades;

import org.example.Actividades.Tarea;

import java.util.ArrayList;

public class vistaTarea extends vistaActividad {

    Tarea tarea;

    public vistaTarea(Tarea t) {
        super(t);
        this.tarea = t;
        this.setInfoResumida();
        this.setInfoCompleta();
    }

    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "Nombre: " + this.actividad.obtenerNombre() + ".\n\n";
        if (!this.actividad.obtenerDescripcion().equals("")) {
            this.infoCompleta += "Descripción: " + this.actividad.obtenerDescripcion() + ".\n\n";
        }
        this.infoCompleta += "Fecha: " + this.obtenerStringFechaInicio()  + "." + todoElDia + "\n\n"
                + this.obtenerStringAlarmas() + "\n\n";
        if (this.tarea.estaCompletada()) {
            this.infoCompleta += "La tarea está completada.";
        } else {
            this.infoCompleta += "La tarea no está completada.";
        }
    }

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.TAREA.obtenerCodigoColor();
    }
}
