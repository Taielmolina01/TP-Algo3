package org.example;

import org.example.Alarma.Alarma;
import org.example.ElementosCalendario.ElementoCalendario;
import org.example.ElementosCalendario.Evento;
import org.example.ElementosCalendario.Tarea;
import org.example.Frecuencia.Frecuencia;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendario implements Serializable {

    public final HashMap<Integer, ElementoCalendario> elementosCalendario;
    public int indiceElementoCalendario;
    private final ArrayList<Alarma> alarmas;

    public Calendario() {
        this.elementosCalendario = new HashMap<>();
        this.alarmas = new ArrayList<>();
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
        return this.indiceElementoCalendario - 1;
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, fechaFinalRepeticion, frecuencia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
        return this.indiceElementoCalendario - 1;
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                            boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, evento);
        return this.indiceElementoCalendario - 1;
    }

    public int crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(nombre, descripcion, fecha, todoElDia);
        this.elementosCalendario.put(this.indiceElementoCalendario++, tarea);
        return this.indiceElementoCalendario - 1;
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

    public ArrayList<ElementoCalendario> obtenerElementosCalendarioEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        ArrayList<ElementoCalendario> elementos = new ArrayList<>();
        for (ElementoCalendario elemento : this.elementosCalendario.values()) {
            if (ElementoCalendario.estaEntreFechas(elemento.obtenerFechaInicio(), fechaInicio, fechaFinal)) {
                elementos.add(elemento);
            }
        }
        return elementos;
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
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(os);
            o.writeObject(this.elementosCalendario);
            o.writeObject(this.indiceElementoCalendario);
            o.flush();
        } catch (IOException e) {
            salida.println("El flujo de salida no existe.");
        }
        finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    //
                }
            }

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