package org.example;

import org.example.ElementosCalendario.Evento;
import org.example.ElementosCalendario.Tarea;

public interface VisitorElementos {

    String obtenerInfoResumida(Evento evento);

    String obtenerInfoResumida(Tarea tarea);

    String obtenerInfoCompleta(Evento evento);

    String obtenerInfoCompleta(Tarea tarea);

}