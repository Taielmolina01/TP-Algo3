package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Calendario {
    private final HashMap<Integer, Evento> eventos;
    private final HashMap<Integer, Tarea> tareas;
    private Integer indice;

    private final PriorityQueue<Alarma> alarmas;
    private final Comparator<Alarma> funcComparacion;

    public Calendario() {
        this.eventos = new HashMap<>();
        this.tareas = new HashMap<>();
        this.indice = 0;

        this.funcComparacion = (alarma1, alarma2) -> {
            LocalDateTime fechaArbitraria = LocalDateTime.of(2000, 1, 1, 0, 0);
            var duracion1 = alarma1.cuantoFaltaParaDisparar(fechaArbitraria);
            var duracion2 = alarma2.cuantoFaltaParaDisparar(fechaArbitraria);
            if (duracion1.minus(duracion2).isZero()) {
                return 0;
            } else if (duracion1.minus(duracion2).isPositive()) {
                return 1;
            } else {
                return -1;
            }
        };
        this.alarmas = new PriorityQueue<>(this.funcComparacion);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia,
                            Duration duracion) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia);
        eventos.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia,
                            Duration duracion, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, fechaFinalRepeticion, frecuencia);
        eventos.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, boolean todoElDia,
                            Duration duracion, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        eventos.put(indice++, evento);
    }

    public void crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(nombre, descripcion, fecha, todoElDia);
        tareas.put(indice++, tarea);
    }

    public void modificarEvento(int id) {

    }

    public void modificarTarea(int id) {

    }

    public void eliminarEvento(int id) {
        Evento eventoEliminado = this.eventos.remove(id);
        for (Alarma alarma:eventoEliminado.obtenerAlarmas()) {
            this.alarmas.remove(alarma);
        }
    }

    public void eliminarTarea(int id) {
        Tarea tareaEliminada = this.tareas.remove(id);
        for (Alarma alarma:tareaEliminada.obtenerAlarmas()) {
            this.alarmas.remove(alarma);
        }
    }

    public void configurarAlarma(int id, Alarma.efecto efecto, LocalDateTime fechaActivacion) {
        ElementoCalendario elemento = eventos.containsKey(id) ? eventos.get(id) : tareas.get(id);
        Alarma alarma = new Alarma(efecto, fechaActivacion);
        elemento.agregarAlarma(alarma);
        this.alarmas.add(alarma);
    }

    public void configurarAlarma(int id, Alarma.efecto efecto, Duration intervaloTiempo) {
        ElementoCalendario elemento = eventos.containsKey(id) ? eventos.get(id) : tareas.get(id);
        Alarma alarma = new Alarma(efecto, elemento.obtenerFechaInicio(), intervaloTiempo);
        elemento.agregarAlarma(alarma);
        this.alarmas.add(alarma);
    }

    public Alarma obtenerSiguienteAlarma() {
        return this.alarmas.peek();
    }
}
