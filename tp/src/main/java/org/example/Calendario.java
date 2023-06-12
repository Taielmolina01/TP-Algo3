package org.example;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.Alarma.Alarma;
import org.example.Frecuencia.Frecuencia;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendario implements Serializable {

    public final HashMap<Integer, Actividad> actividadesCalendario;
    public int indiceActividad;

    public Calendario() {
        this.actividadesCalendario = new HashMap<>();
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                           boolean todoElDia) {
        Evento evento = new Evento(this.indiceActividad, nombre, descripcion, fechaInicio, duracion, todoElDia);
        this.actividadesCalendario.put(this.indiceActividad++, evento);
        return this.indiceActividad - 1;
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                           boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        Evento evento = new Evento(this.indiceActividad, nombre, descripcion, fechaInicio, duracion, todoElDia,
                fechaFinalRepeticion, frecuencia);
        this.actividadesCalendario.put(this.indiceActividad++, evento);
        return this.indiceActividad - 1;
    }

    public int crearEvento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                           boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        Evento evento = new Evento(this.indiceActividad, nombre, descripcion, fechaInicio, duracion, todoElDia, ocurrencias, frecuencia);
        this.actividadesCalendario.put(this.indiceActividad++, evento);
        return this.indiceActividad - 1;
    }

    public int crearTarea(String nombre, String descripcion, LocalDateTime fecha, boolean todoElDia) {
        Tarea tarea = new Tarea(this.indiceActividad, nombre, descripcion, fecha, todoElDia);
        this.actividadesCalendario.put(this.indiceActividad++, tarea);
        return this.indiceActividad - 1;
    }

    public void modificarNombre(int id, String nuevoNombre) {
        this.actividadesCalendario.get(id).modificarNombre(nuevoNombre);
    }

    public String obtenerNombre(int id) {
        return this.actividadesCalendario.get(id).obtenerNombre();
    }

    public void modificarDescripcion(int id, String nuevaDescripcion) {
        this.actividadesCalendario.get(id).modificarDescripcion(nuevaDescripcion);
    }

    public String obtenerDescripcion(int id) {
        return this.actividadesCalendario.get(id).obtenerDescripcion();
    }

    public void modificarFechaInicio(int id, LocalDateTime nuevaFechaInicio) {
        this.actividadesCalendario.get(id).modificarFechaInicio(nuevaFechaInicio);
    }

    public void modificarTodoElDia(int id, boolean todoElDia) {
        this.actividadesCalendario.get(id).modificarTodoElDia(todoElDia);
    }

    public void modificarDuracion(int id, Duration nuevaDuracion) {
        Evento evento = (Evento) this.actividadesCalendario.get(id);
        evento.modificarDuracion(nuevaDuracion);
    }

    public void modificarFechaFinal(int id, LocalDateTime nuevaFechaFinal) {
        Evento evento = (Evento) this.actividadesCalendario.get(id);
        evento.modificarFechaFinal(nuevaFechaFinal);
    }

    public void modificarFrecuencia(int id, Frecuencia nuevaFrecuencia) {
        Evento evento = (Evento) this.actividadesCalendario.get(id);
        evento.modificarFrecuencia(nuevaFrecuencia);
    }

    public void modificarOcurrencias(int id, int ocurrencias) {
        Evento evento = (Evento) this.actividadesCalendario.get(id);
        evento.modificarOcurrencias(ocurrencias);
    }

    public void eliminarElementoCalendario(int id) {
        this.actividadesCalendario.remove(id);
    }

    public void cambiarEstadoTarea(int id) {
        Tarea tarea = (Tarea) this.actividadesCalendario.get(id);
        tarea.cambiarEstadoCompletado();
    }

    public boolean obtenerEstadoTarea(int id) {
        Tarea tarea = (Tarea) this.actividadesCalendario.get(id);
        return tarea.estaCompletada();
    }

    public ArrayList<Actividad> obtenerActividadesEntreFechas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        ArrayList<Actividad> actividades = new ArrayList<>();
        for (Actividad actividad : this.actividadesCalendario.values()) {
            actividades.addAll(actividad.actividadesEntreFechas(fechaInicio, fechaFinal));
        }
        return actividades;
    }


    public void agregarAlarma(int id, Alarma.Efecto efecto, LocalDateTime fechaActivacion) {
        Actividad elemento = this.actividadesCalendario.get(id);
        elemento.agregarAlarma(efecto, fechaActivacion);
    }

    public void agregarAlarma(int id, Alarma.Efecto efecto, Duration intervaloTiempo) {
        Actividad elemento = this.actividadesCalendario.get(id);
        elemento.agregarAlarma(efecto, intervaloTiempo);
    }

    public void modificarEfectoAlarma(int idElemento, int idAlarma, Alarma.Efecto nuevoEfecto) {
        this.actividadesCalendario.get(idElemento).modificarNotificacionAlarma(idAlarma, nuevoEfecto);
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaAbsoluta) {
        this.actividadesCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaAbsoluta);
    }

    public void modificarFechaActivacionAlarma(int idElemento, int idAlarma, LocalDateTime fechaArbitraria, Duration intervaloTiempoNuevo) {
        this.actividadesCalendario.get(idElemento).modificarFechaActivacionAlarma(idAlarma, fechaArbitraria, intervaloTiempoNuevo);
    }


    public Alarma obtenerSiguienteAlarma(LocalDateTime fechaActual, LocalDateTime fechaFinal) {
        AbstractMap.SimpleEntry<Integer, Alarma> parActividadAlarma = this.obtenerSiguienteAlarmaPorActividad(fechaActual, fechaFinal);
        if (parActividadAlarma != null) {
            return parActividadAlarma.getValue();
        }
        return null;
    }

    public AbstractMap.SimpleEntry<Integer, Alarma> obtenerSiguienteAlarmaPorActividad(LocalDateTime fechaActual, LocalDateTime fechaFinal) {
        HashMap<Integer, ArrayList<Alarma>> alarmasActividades = this.obtenerAlarmasLapso(fechaActual, fechaFinal);
        if (alarmasActividades.size() == 0) {
            return null;
        }
        Integer IDMaximo = null;
        Integer posMaxima = null;
        for (var entry : alarmasActividades.entrySet()) {
            var clave = entry.getKey();
            var valor = entry.getValue();
            if (IDMaximo == null && valor.size() > 0) {
                IDMaximo = clave;
                posMaxima = 0;
            }
            for (int j = 0; j < valor.size(); j++) {
                if (Alarma.compararAlarmas(alarmasActividades.get(IDMaximo).get(posMaxima), alarmasActividades.get(clave).get(j)) > 0) {
                    if (alarmasActividades.get(clave).get(j).obtenerFechaActivacion().isBefore(fechaActual)) {
                        continue;
                    }
                    IDMaximo = clave;
                    posMaxima = j;
                }
            }
        }
        if (IDMaximo == null || posMaxima == null || alarmasActividades.get(IDMaximo).get(posMaxima).obtenerFechaActivacion().isBefore(fechaActual)) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(IDMaximo, this.obtenerAlarmasActividad(IDMaximo).get(posMaxima));
    }

    public HashMap<Integer, ArrayList<Alarma>> obtenerAlarmasLapso(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        ArrayList<Actividad> actividades = this.obtenerActividadesEntreFechas(fechaInicial, fechaFinal);
        HashMap<Integer, ArrayList<Alarma>> alarmas = new HashMap<>();
        for (Actividad a : actividades) {
            ArrayList<Alarma> alarmasActuales = new ArrayList<>();
            alarmasActuales.addAll(a.obtenerAlarmas().values());
            alarmas.put(a.obtenerID(), alarmasActuales);
        }
        return alarmas;
    }

    public HashMap<Integer, Alarma> obtenerAlarmasActividad(int ID) {
        return this.actividadesCalendario.get(ID).obtenerAlarmas();
    }

    public void eliminarAlarma(int idElemento, int idAlarma) {
        this.actividadesCalendario.get(idElemento).eliminarAlarma(idAlarma);
    }


    public void guardarEstado(ManejadorGuardado manejador) throws IOException {
        manejador.guardarEstado(this);
    }

    public Calendario recuperarEstado(ManejadorGuardado manejador) throws IOException, ClassNotFoundException {
        return manejador.recuperarEstado();
    }

    public void borrarEstadoGuardado(ManejadorGuardado manejador) {
        manejador.borrarEstadoGuardado();
    }

    protected void serializar(PrintStream salida, OutputStream os) throws IOException {
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(os);
            o.writeObject(this);
            o.flush();
        } catch (IOException e) {
            salida.println("El flujo de salida no existe.");
            throw new IOException("El flujo de salida no existe");
        } finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    //
                }
            }

        }
    }

    protected Calendario deserializar(PrintStream salida, InputStream is) throws ClassNotFoundException {
        Calendario calendarioNuevo = new Calendario();
        try {
            ObjectInputStream objectInStream = new ObjectInputStream(is);
            calendarioNuevo = (Calendario) objectInStream.readObject();
            return calendarioNuevo;
        } catch (IOException e) {
            salida.println("El flujo de entrada está vacío.");
            // por que lanzaria una excepcion aca? esta excepcion se va a dar solo cuando el inputStream
            // esté vacío, lo cual es algo valido, no tengo nada que leer? bueno te devuelvo un calendario vacio
            return calendarioNuevo;
        } catch (ClassNotFoundException e) {
            salida.println("La clase Calendario no se encuentra en este paquete.");
            throw new ClassNotFoundException("La clase Calendario no se encuentra en este paquete.");
        }
    }
}