package org.example.Visitadores;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.Alarma.Alarma;
import org.example.VistaActividades.vistaActividad;
import org.example.VistaActividades.vistaEvento;
import org.example.VistaActividades.vistaTarea;
import org.example.formateador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class visitadorActividades implements visitorActividades {

    public ArrayList<vistaActividad> visitarActividades(List<Actividad> actividades) {
        ArrayList<vistaActividad> vistaActividades = new ArrayList<>();
        for (Actividad a : actividades) {
            vistaActividades.add(a.visitarActividad(this));
        }
        return vistaActividades;
    }

    public vistaActividad visitarActividad(Evento e) {
        ArrayList<String> infoEvento = new ArrayList<>();
        this.crearListaDatosComunes(infoEvento, e);
        infoEvento.add(e.obtenerFechaFinalDefinitivo().format(formateador.formatterConHoras));
        infoEvento.add(e.visitarFrecuencia(new visitadorEventosFrecuencia()));
        return new vistaEvento(infoEvento);
    }

    public vistaActividad visitarActividad(Tarea t) {
        ArrayList<String> infoTarea = new ArrayList<>();
        this.crearListaDatosComunes(infoTarea, t);
        infoTarea.add(String.valueOf(t.estaCompletada()));
        return new vistaTarea(infoTarea);
    }

    private void crearListaDatosComunes(ArrayList<String> infoActividad, Actividad a) {
        infoActividad.add(String.valueOf(a.obtenerID()));
        infoActividad.add(a.obtenerNombre());
        infoActividad.add(a.obtenerDescripcion());
        if (a.obtenerTodoElDia()) {
            infoActividad.add(a.obtenerFechaInicio().format(formateador.formatterSinHoras));
        } else {
            infoActividad.add(a.obtenerFechaInicio().format(formateador.formatterConHoras));
        }
        infoActividad.add(a.obtenerTodoElDia().toString());
        HashMap<Integer, Alarma> alarmas = a.obtenerAlarmas();
        String stringAlarmas = "";
        if (alarmas.size() == 0) {
            stringAlarmas += "Esta actividad no tiene alarmas configuradas.";
        } else {
            stringAlarmas += "Fechas alarmas: ";
            for (Alarma alarma : alarmas.values()) {
                stringAlarmas += alarma.obtenerFechaActivacion().format(formateador.formatterConHoras) + ", ";
            }
        }
        infoActividad.add(stringAlarmas);
    }
}
