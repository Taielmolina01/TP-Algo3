package org.example.Visitadores;

import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.VistaActividades.vistaActividad;

public interface visitorActividades {

    vistaActividad visitarActividad(Evento e);
    vistaActividad visitarActividad(Tarea t);
    String obtenerColor(Evento evento);
    String obtenerColor(Tarea tarea);
}