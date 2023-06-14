package org.example.Visitadores;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.VistaActividades.VistaActividad;
import org.example.VistaActividades.VistaEvento;
import org.example.VistaActividades.VistaTarea;

import java.util.ArrayList;
import java.util.List;

public class VisitadorActividades implements VisitorActividades {

    private VistaActividad vistaActual;

    public ArrayList<VistaActividad> visitarActividades(List<Actividad> actividades) {
        ArrayList<VistaActividad> vistaActividades = new ArrayList<>();
        for (Actividad a : actividades) {
            a.visitarActividad(this);
            vistaActividades.add(this.vistaActual);
        }
        return vistaActividades;
    }

    public void visitarActividad(Evento e) {
        this.vistaActual = new VistaEvento(e);
    }

    public void visitarActividad(Tarea t) {
        this.vistaActual = new VistaTarea(t);
    }


}
