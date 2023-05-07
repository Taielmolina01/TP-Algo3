package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Calendario {
    private final HashMap<Integer, ElementoCalendario> elementosCalendario;
    private int indice;
    private final PriorityQueue<Alarma> alarmas;

    public Calendario() {
        this.elementosCalendario = new HashMap<>();
        this.indice = 0;

        Comparator<Alarma> funcComparacion = (alarma1, alarma2) -> {
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
        this.alarmas = new PriorityQueue<>(funcComparacion);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia);
        elementosCalendario.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, fechaFinalRepeticion, frecuencia);
        elementosCalendario.put(indice++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        elementosCalendario.put(indice++, evento);
    }

    public void crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(nombre, descripcion, fecha, todoElDia);
        elementosCalendario.put(indice++, tarea);
    }

    public void modificarNombre(int id, String nuevoNombre) {
        this.elementosCalendario.get(id).modificarNombre(nuevoNombre);
    }

    public String obtenerNombre(int id) {
        return this.elementosCalendario.get(id).obtenerNombre();
    }

    public void modificarDescripcion(int id, String nuevaDescripcion) {
        this.elementosCalendario.get(id).modificarDescripcion(nuevaDescripcion);
    }

    public String obtenerDescripcion(int id) {
        return this.elementosCalendario.get(id).obtenerDescripcion();
    }

    public void modificarFechaInicio(int id, LocalDateTime nuevaFechaInicio) {
        this.elementosCalendario.get(id).modificarFechaInicio(nuevaFechaInicio);
    }

    public void modificarTodoElDia(int id, boolean todoElDia) {
        this.elementosCalendario.get(id).modificarTodoElDia(todoElDia);
    }

    public void modificarDuracion(int id, Duration nuevaDuracion) {
        Evento evento = (Evento)this.elementosCalendario.get(id);
        evento.modificarDuracion(nuevaDuracion);
    }

    public void modificarFechaFinal(int id, LocalDateTime nuevaFechaFinal) {
        Evento evento = (Evento)this.elementosCalendario.get(id);
        evento.modificarFechaFinal(nuevaFechaFinal);
    }

    public void moficiarFrecuencia(int id, Frecuencia nuevaFrecuencia) {
        Evento evento = (Evento)this.elementosCalendario.get(id);
        evento.modificarFrecuencia(nuevaFrecuencia);
    }

    public void modificarOcurrencias(int id, int ocurrencias) {
        Evento evento = (Evento)this.elementosCalendario.get(id);
        evento.modificarOcurrencias(ocurrencias);
    }

    public void eliminarElementoCalendario(int id) {
        ElementoCalendario elementoEliminado = this.elementosCalendario.remove(id);
        for (Alarma alarma : elementoEliminado.obtenerAlarmas()) {
            this.alarmas.remove(alarma);
        }
    }

    public void configurarAlarma(int id, Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        ElementoCalendario elemento = this.elementosCalendario.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, fechaActivacion));
    }

    public void configurarAlarma(int id, Alarma.Efecto efecto, Duration intervaloTiempo) {
        ElementoCalendario elemento = this.elementosCalendario.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, intervaloTiempo));
    }

    public Alarma obtenerSiguienteAlarma() {
        return this.alarmas.peek();
    }
}

