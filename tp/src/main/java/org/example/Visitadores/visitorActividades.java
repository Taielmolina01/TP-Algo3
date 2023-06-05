package org.example.Visitadores;

import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;

public interface visitorActividades {

    String obtenerInfoResumida(Evento evento);

    String obtenerInfoResumida(Tarea tarea);

    String obtenerInfoCompleta(Evento evento);

    String obtenerInfoCompleta(Tarea tarea);

    visitadorActividades.colorFondo obtenerColor(Evento evento);

    visitadorActividades.colorFondo obtenerColor(Tarea tarea);
}