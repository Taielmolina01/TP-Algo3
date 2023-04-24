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

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia);
        eventos.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, fechaFinalRepeticion, frecuencia);
        eventos.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        eventos.put(indice++, evento);
    }

    public void crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(nombre, descripcion, fecha, todoElDia);
        tareas.put(indice++, tarea);
    }

    public void modificarNombre(int idElemento, String nuevoNombre) {
        ElementoCalendario elemento = this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento) : tareas.get(idElemento);
        elemento.modificarNombre(nuevoNombre);
    }

    public String obtenerNombre(int idElemento) {
        return this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento).obtenerNombre() : this.tareas.get(idElemento).obtenerNombre();
    }

    public void modificarDescripcion(int idElemento, String nuevaDescripcion) {
        ElementoCalendario elemento = this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento) : this.tareas.get(idElemento);
        elemento.modificarDescripcion(nuevaDescripcion);
    }

    public String obtenerDescripcion(int idElemento) {
        return this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento).obtenerDescripcion() : this.tareas.get(idElemento).obtenerDescripcion();
    }

    public void modificarFechaInicio(int idElemento, LocalDateTime nuevaFechaInicio) {
        ElementoCalendario elemento = this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento) : this.tareas.get(idElemento);
        elemento.modificarFechaInicio(nuevaFechaInicio);
    }

    public void modificarTodoElDia(int idElemento, boolean todoElDia) {
        ElementoCalendario elemento = this.eventos.containsKey(idElemento) ? this.eventos.get(idElemento) : this.tareas.get(idElemento);
        elemento.modificarTodoElDia(todoElDia);
    }

    public void modificarDuracion(int idEvento, Duration nuevaDuracion) {
        Evento evento = this.eventos.get(idEvento);
        evento.modificarDuracion(nuevaDuracion);
    }

    public void modificarFechaFinal(int idEvento, LocalDateTime nuevaFechaFinal) {
        Evento evento = this.eventos.get(idEvento);
        evento.modificarFechaFinal(nuevaFechaFinal);
    }

    public void moficiarFrecuencia(int idEvento, Frecuencia nuevaFrecuencia) {
        Evento evento = this.eventos.get(idEvento);
        evento.modificarFrecuencia(nuevaFrecuencia);
    }

    public void modificarOcurrencias(int idEvento, int ocurrencias) {
        Evento evento = this.eventos.get(idEvento);
        evento.modificarOcurrencias(ocurrencias);
    }

    public void eliminarEvento(int id) {
        Evento eventoEliminado = this.eventos.remove(id);
        for (Alarma alarma : eventoEliminado.obtenerAlarmas()) {
            this.alarmas.remove(alarma);
        }
    }

    public void eliminarTarea(int id) {
        Tarea tareaEliminada = this.tareas.remove(id);
        for (Alarma alarma : tareaEliminada.obtenerAlarmas()) {
            this.alarmas.remove(alarma);
        }
    }

    public void configurarAlarma(int id, Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        ElementoCalendario elemento = this.eventos.containsKey(id) ? this.eventos.get(id) : this.tareas.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, fechaActivacion));
    }

    public void configurarAlarma(int id, Alarma.Efecto efecto, Duration intervaloTiempo) {
        ElementoCalendario elemento = this.eventos.containsKey(id) ? this.eventos.get(id) : this.tareas.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, intervaloTiempo));
    }

    public Alarma obtenerSiguienteAlarma() {
        return this.alarmas.peek();
    }
}

