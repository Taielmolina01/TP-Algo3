package org.example;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendario implements Serializable {
    protected HashMap<Integer, ElementoCalendario> elementosCalendario;
    private int indiceElementoCalendario;
    private final ArrayList<Alarma> alarmas;
    private final ManejadorGuardado manejador;
    private final String rutaArchivoGuardado;

    public Calendario() {
        this.rutaArchivoGuardado = "MiCalendario.txt";
        this.manejador = new ManejadorGuardado();
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
        for (Alarma alarma : elementoEliminado.obtenerAlarmas().values()) {
            this.alarmas.remove(alarma);
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
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        this.elementosCalendario.get(idElemento).modificarNotificacionAlarma(idAlarma, nuevoEfecto);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarEfecto(nuevoEfecto);
        }
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaAbsoluta) {
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        this.elementosCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaAbsoluta);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarFechaActivacion(fechaAbsoluta);
        }
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaArbitraria, Duration intervaloTiempoNuevo) {
        Alarma alarma = this.elementosCalendario.get(idElemento).obtenerAlarma(idAlarma);
        this.elementosCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaArbitraria, intervaloTiempoNuevo);
        int posicionAlarma = this.obtenerPosicionAlarma(alarma);
        if (posicionAlarma != -1) {
            this.alarmas.get(posicionAlarma).modificarFechaActivacion(fechaArbitraria, intervaloTiempoNuevo);
        }
    }

    // Ver repeticion de codigo

    public void eliminarAlarma(int idElemento, int idAlarma) {
        Alarma alarmaEliminada = this.elementosCalendario.get(idElemento).eliminarAlarma(idAlarma);
        this.alarmas.remove(alarmaEliminada);
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

    public void guardarEstado() {
        this.manejador.guardarEstado(this.rutaArchivoGuardado, this);
    }

    public Calendario recuperarEstado() {
        return this.manejador.recuperarEstado(this.rutaArchivoGuardado);
    }

    public void borrarEstadoGuardado() {
        this.manejador.borrarEstadoGuardado(this.rutaArchivoGuardado);
    }

    public PrintStreamMock obtenerSalidaManejador() {
        return this.manejador.salida;
    }

    public void serializar(OutputStream os) {
        try {
            ObjectOutputStream elementos = new ObjectOutputStream(os);
            elementos.writeObject(this.elementosCalendario);
            elementos.flush();
        } catch (IOException e) {
            //
        }
    }

    public Calendario deserializar(InputStream is) {
        try {
            ObjectInputStream objectInStream = new ObjectInputStream(is);
            HashMap<Integer, ElementoCalendario> elementosCalendario = (HashMap<Integer, ElementoCalendario>) objectInStream.readObject();
            Calendario calendarioNuevo = new Calendario();
            calendarioNuevo.elementosCalendario.putAll(elementosCalendario);
            for (ElementoCalendario elemento : calendarioNuevo.elementosCalendario.values()) {
                calendarioNuevo.alarmas.addAll(elemento.obtenerAlarmas().values());
            }
            calendarioNuevo.indiceElementoCalendario = calendarioNuevo.elementosCalendario.size();
            return calendarioNuevo;
        } catch (IOException e) {
            System.out.println("La entrada se encuentra vacía.");
        } catch (ClassNotFoundException e) {
            System.out.println("El objeto guardado no es de tipo calendario.");
        }
        return null;
    }
}