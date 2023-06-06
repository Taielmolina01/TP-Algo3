package org.example.Visitadores;

import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.Alarma.Alarma;
import org.example.formateador;
import org.example.vistaActividad;
import org.example.vistaEvento;
import org.example.vistaTarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class visitadorActividades implements visitorActividades {

    private static final String colorAzul = "#85C1E9";
    private static final String colorVerde = "#58D68D";

    public ArrayList<vistaActividad> visitarActividades(List<Actividad> actividades) {
        ArrayList<vistaActividad> vistaActividades = new ArrayList<>();
        for (Actividad a : actividades) {
            vistaActividades.add(a.visitarActividad(this));
        }
        return vistaActividades;
    }

    public vistaActividad visitarActividad(Evento e) {
        ArrayList<String> infoEvento = new ArrayList<>();
        infoEvento.add(obtenerColor(e));
        this.crearListaDatosComunes(infoEvento, e);
        infoEvento.add(e.obtenerFechaFinalDefinitivo().format(formateador.formatterConHoras));
        infoEvento.add(e.visitarFrecuencia(new visitadorEventosFrecuencia()));
        return new vistaEvento(infoEvento);
    }

    public vistaActividad visitarActividad(Tarea t) {
        ArrayList<String> infoTarea = new ArrayList<>();
        infoTarea.add(obtenerColor(t));
        this.crearListaDatosComunes(infoTarea, t);
        infoTarea.add(String.valueOf(t.estaCompletada()));
        return new vistaTarea(infoTarea);
    }

    private void crearListaDatosComunes(ArrayList<String> infoActividad, Actividad a) {
        infoActividad.add(String.valueOf(a.obtenerID()));
        infoActividad.add(a.obtenerNombre());
        infoActividad.add(a.obtenerDescripcion());
        infoActividad.add(a.obtenerFechaInicio().format(formateador.formatterConHoras));
        infoActividad.add(a.obtenerTodoElDia().toString());
        HashMap<Integer, Alarma> alarmas = a.obtenerAlarmas();
        String stringAlarmas = "";
        if (alarmas.size() == 0) {
            stringAlarmas += "Esta actividad no tiene alarmas configuradas.";
        } else {
            stringAlarmas += "Fechas alarmas: ";
            for (Alarma alarma : alarmas.values()) {
                stringAlarmas += alarma.obtenerFechaActivacion().format(formateador.formatterConHoras);
            }
            stringAlarmas += ".";
        }
        infoActividad.add(stringAlarmas);
    }


    public String obtenerColor(Evento evento) {
        return colorActividad.EVENTO.getClaveColor();
    }

    public String obtenerColor(Tarea tarea) {
        return colorActividad.TAREA.getClaveColor();
    }

    public enum colorActividad {

        EVENTO(colorAzul),

        TAREA(colorVerde);

        private final String claveColor;

        colorActividad(String claveColor) {
            this.claveColor = claveColor;
        }

        public String getClaveColor() {
            return this.claveColor;
        }
    }

}
