package org.example.Visitadores;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.VistaActividades.vistaActividad;
import org.example.VistaActividades.vistaEvento;
import org.example.VistaActividades.vistaTarea;

import java.util.ArrayList;
import java.util.List;

public class visitadorActividades implements visitorActividades {

    private vistaActividad vistaActual;

    public ArrayList<vistaActividad> visitarActividades(List<Actividad> actividades) {
        ArrayList<vistaActividad> vistaActividades = new ArrayList<>();
        for (Actividad a : actividades) {
            a.visitarActividad(this);
            vistaActividades.add(this.vistaActual);
        }
        return vistaActividades;
    }

    public void visitarActividad(Evento e) {
        this.vistaActual = new vistaEvento(e);
    }

    public void visitarActividad(Tarea t) {
        this.vistaActual = new vistaTarea(t);
    }


}
