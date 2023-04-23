package org.example;

import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Calendario {
    public HashMap<Evento, String> eventos;
    public HashMap<Tarea, String> tareas;
    private PriorityQueue<Alarma> alarmas; // puede ser period el tipo tambien, no estoy seguro si funcionaria
    // igual o no

    private Comparator<Alarma> comparador;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
        this.comparador = (alarma1, alarma2) -> {
            if (alarma1.obtenerFechaAbsoluta().isBefore(alarma2.obtenerFechaAbsoluta())) {
                return 1;
            }
            return -1;
        };
    }

    public PriorityQueue<Alarma> obtenerAlarmas() {
        return this.alarmas;
    }

    public Alarma obtenerProximaAlarma() {
        return this.alarmas.peek();
    }
}
