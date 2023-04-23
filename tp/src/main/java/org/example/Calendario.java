package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;

public class Calendario {
    public HashMap<Evento, ArrayList<Alarma>> eventos;
    public HashMap<Tarea, ArrayList<Alarma>> tareas;
    private PriorityQueue<Alarma> alarmas;
    private Comparator<Alarma> comparador;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
        this.comparador = (alarma1, alarma2) -> {
            Duration faltaParaAlarma1 = alarma1.cuantoFaltaParaDisparar(LocalDateTime.of(1000, 1, 1, 0, 0, 0)).abs();
            Duration faltaParaAlarma2 = alarma2.cuantoFaltaParaDisparar(LocalDateTime.of(1000, 1, 1, 0, 0, 0)).abs();
            if (faltaParaAlarma1.compareTo(faltaParaAlarma2) > 0) { // capaz sea al reves el 1 y -1
                return 1;
            }
            return -1;
        };
        this.alarmas = new PriorityQueue<>(this.comparador);
    }

    public PriorityQueue<Alarma> obtenerAlarmas() {
        return this.alarmas;
    }

    public Alarma obtenerProximaAlarma() {
        // capaz aca tendria que llamar a un metodo privado que me agregue todas las alarmas existentes
        // en cada Evento/Tarea, al arrayList correspondiente en su valor en el hash, y despues hacer todo esto.
        // IDEA: cuando llamamos aca hacemos por todas las claves de ambos hash evento.obtenerAlarmas()/tarea.obtenerAlarmas()
        // y rehasheamos las claves con los valores nuevos (los resultados de obtenerAlarmas()) o utilizar el metodo replace
        // de HashMap que creo que hace eso (asocia un nuevo valor a una clave existente).
        Iterator<Entry<Evento, ArrayList<Alarma>> iteradorEventos = eventos.entrySet().iterator();
        Iterator<Entry<Tarea, ArrayList<Alarma>> iteradorTareas = tareas.entrySet().iterator();
        while (iteradorEventos.hasNext()) {
            Entry<Evento, ArrayList<Alarma>> entradaClaveValor = iteradorEventos.next();
            for (Alarma alarma : entradaClaveValor.getValue()) {
                if (!this.alarmas.contains(alarma)) {
                    this.alarmas.add(alarma);
                }
            }
        }
        while (iteradorTareas.hasNext()) {
            Entry<Tarea, ArrayList<Alarma>> entradaClaveValor = iteradorTareas.next();
            for (Alarma alarma : entradaClaveValor.getValue()) {
                if (!this.alarmas.contains(alarma)) {
                    this.alarmas.add(alarma);
                }
            }
        }
        return this.alarmas.peek();
    }
}
