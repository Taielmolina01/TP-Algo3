package org.example.Visitadores;

import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;

public interface visitorElementos {

    String obtenerInfoResumida(Evento evento);

    String obtenerInfoResumida(Tarea tarea);

    String obtenerInfoCompleta(Evento evento);

    String obtenerInfoCompleta(Tarea tarea);

    visitadorElementosCalendario.colorFondo obtenerColor(Evento evento);

    visitadorElementosCalendario.colorFondo obtenerColor(Tarea tarea);


}