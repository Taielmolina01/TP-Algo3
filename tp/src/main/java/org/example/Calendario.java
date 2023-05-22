package org.example;

import org.example.Alarma.Alarma;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.example.ElementosCalendario.ElementoCalendario;
import org.example.ElementosCalendario.Evento;
import org.example.ElementosCalendario.Tarea;
import org.example.Frecuencia.Frecuencia;

public class Calendario implements Serializable {

    private HashMap<Integer, ElementoCalendario> elementosCalendario;
    private int indiceElementoCalendario;
    private final ArrayList<Alarma> alarmas;

    public Calendario() {
        this.elementosCalendario = new HashMap<>();
        this.alarmas = new ArrayList<>();
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, fechaFinalRepeticion, frecuencia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
    }

    public void crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
    }

    public void crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(nombre, descripcion, fecha, todoElDia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, tarea);
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
        Evento evento = (Evento) this.elementosCalendario.get(id);
        evento.modificarDuracion(nuevaDuracion);
    }

    public void modificarFechaFinal(int id, LocalDateTime nuevaFechaFinal) {
        Evento evento = (Evento) this.elementosCalendario.get(id);
        evento.modificarFechaFinal(nuevaFechaFinal);
    }

    public void moficiarFrecuencia(int id, Frecuencia nuevaFrecuencia) {
        Evento evento = (Evento) this.elementosCalendario.get(id);
        evento.modificarFrecuencia(nuevaFrecuencia);
    }

    public void modificarOcurrencias(int id, int ocurrencias) {
        Evento evento = (Evento) this.elementosCalendario.get(id);
        evento.modificarOcurrencias(ocurrencias);
    }

    public void eliminarElementoCalendario(int id) {
        ElementoCalendario elementoEliminado = this.elementosCalendario.remove(id);
        if (elementoEliminado != null) {
            for (Alarma alarma : elementoEliminado.obtenerAlarmas().values()) {
                this.alarmas.remove(alarma);
            }
        }
    }



    public void agregarAlarma(int id, Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        ElementoCalendario elemento = this.elementosCalendario.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, fechaActivacion));
    }

    public void agregarAlarma(int id, Alarma.Efecto efecto, Duration intervaloTiempo) {
        ElementoCalendario elemento = this.elementosCalendario.get(id);
        this.alarmas.add(elemento.agregarAlarma(efecto, intervaloTiempo));
    }

    public void modificarEfectoAlarma(int idElemento, int idAlarma, Alarma.Efecto nuevoEfecto) {
        this.elementosCalendario.get(idElemento).modificarNotificacionAlarma(idAlarma, nuevoEfecto);
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarEfecto(nuevoEfecto);
        }
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaAbsoluta) {
        this.elementosCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaAbsoluta);
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarFechaActivacion(fechaAbsoluta);
        }
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaArbitraria, Duration intervaloTiempoNuevo) {
        this.elementosCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaArbitraria, intervaloTiempoNuevo);
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarFechaActivacion(fechaArbitraria, intervaloTiempoNuevo);
        }
    }

    public Alarma obtenerSiguienteAlarma(LocalDateTime fechaActual) {
        if (this.alarmas.isEmpty()) {
            return null;
        }
        int posMaxima = 0;
        for (int i = 0; i < alarmas.size(); i++) {
            if (Alarma.compararAlarmas(this.alarmas.get(posMaxima), this.alarmas.get(i)) > 0) {
                if (this.alarmas.get(i).obtenerFechaActivacion().isBefore(fechaActual)) { // La hora de la última alarma ya pasó.
                    continue;
                }
                posMaxima = i;
            }
        }
        if (this.alarmas.get(posMaxima).obtenerFechaActivacion().isBefore(fechaActual)) { // La hora de la última alarma ya pasó.
            return null;
        }
        return this.alarmas.get(posMaxima);
    }

    private int obtenerPosicionAlarma(Alarma alarmaBuscada) {
        for (int i = 0; i < this.alarmas.size(); i++) {
            if (this.alarmas.get(i).equals(alarmaBuscada)) {
                return i;
            }
        }
        return -1;
    }

    public void eliminarAlarma(int idElemento, int idAlarma) {
        Alarma alarmaEliminada = this.elementosCalendario.get(idElemento).eliminarAlarma(idAlarma);
        if (alarmaEliminada != null) {
            this.alarmas.remove(alarmaEliminada);
        }
    }



    public void guardarEstado(ManejadorGuardado manejador) {
        manejador.guardarEstado(this);
    }

    public Calendario recuperarEstado(ManejadorGuardado manejador) {
        return manejador.recuperarEstado();
    }

    public void borrarEstadoGuardado(ManejadorGuardado manejador) {
        manejador.borrarEstadoGuardado();
    }

    protected void serializar(PrintStream salida, OutputStream os) {
        try { // Hacerlo con un finally el close.
            ObjectOutputStream o = new ObjectOutputStream(os);
            o.writeObject(this.elementosCalendario);
            o.writeObject(this.indiceElementoCalendario);
            o.flush();
            o.close();
        } catch (IOException e) {
            salida.println("El flujo de salida no existe.");
        }
    }

    protected Calendario deserializar(PrintStream salida, InputStream is) {
        try {
            ObjectInputStream objectInStream = new ObjectInputStream(is);
            HashMap<Integer, ElementoCalendario> elementos = (HashMap<Integer, ElementoCalendario>) objectInStream.readObject();
            Calendario calendarioNuevo = new Calendario();
            calendarioNuevo.elementosCalendario.putAll(elementos);
            for (ElementoCalendario elemento : calendarioNuevo.elementosCalendario.values()) {
                calendarioNuevo.alarmas.addAll(elemento.obtenerAlarmas().values());
            }
            calendarioNuevo.indiceElementoCalendario = (Integer) objectInStream.readObject();
            return calendarioNuevo;
        } catch (IOException e) {
            salida.println("El flujo de entrada no existe o está vacío.");
        } catch (ClassNotFoundException e) {
            salida.println("La clase Calendario no se encuentra en este paquete.");
        }
        return this;
    }
}