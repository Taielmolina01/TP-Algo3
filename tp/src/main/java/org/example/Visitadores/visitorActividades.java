package org.example.Visitadores;

import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;

public interface visitorActividades {

    void visitarActividad(Evento e);

    void visitarActividad(Tarea t);
}