package org.example.VistaActividades;

import org.example.Actividades.Actividad;
import org.example.Alarma.Alarma;
import org.example.Formateador;

import java.util.HashMap;

public abstract class VistaActividad {

    Actividad actividad;
    String infoResumida;
    String infoCompleta;

    public VistaActividad(Actividad a) {
        this.actividad = a;
    }

    public Actividad obtenerActividad() {
        return this.actividad;
    }

    public void setInfoResumida() {
        this.infoResumida = "Nombre: " + this.actividad.obtenerNombre();
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoResumida += ". Fecha: " + this.obtenerStringFechaInicio() + "." + todoElDia;
    }

    public abstract void setInfoCompleta();

    public abstract boolean llevaCheckbox();

    public abstract boolean checkBoxSeleccionado();

    public abstract void cambiarSeleccionCheckBox();

    protected String setearTextoDiaCompleto() {
        String todoElDia = "";
        if (this.actividad.obtenerTodoElDia()) {
            todoElDia = " Es de día completo.";
        }
        return todoElDia;
    }

    public abstract String obtenerCodigoColorFondo();

    public String obtenerInfoResumida() {
        return this.infoResumida;
    }

    public String obtenerInfoCompleta() {
        return this.infoCompleta;
    }

    public String obtenerStringAlarmas() {
        HashMap<Integer, Alarma> alarmas = this.actividad.obtenerAlarmas();
        String stringAlarmas = "";
        if (alarmas.size() == 0) {
            stringAlarmas += "Esta actividad no tiene alarmas configuradas.";
        } else {
            stringAlarmas += "Fechas alarmas: ";
            for (Alarma alarma : alarmas.values()) {
                stringAlarmas += alarma.obtenerFechaActivacion().format(Formateador.formatterConHoras) + ", ";
            }
        }
        return stringAlarmas;
    }

    public String obtenerStringFechaInicio() {
        if (this.actividad.obtenerTodoElDia()) {
            return this.actividad.obtenerFechaInicio().format(Formateador.formatterSinHoras);
        } else {
            return this.actividad.obtenerFechaInicio().format(Formateador.formatterConHoras) + "hs";
        }
    }

    protected void actualizarInfo() {
        this.setInfoCompleta();
        this.setInfoResumida();
    }

    public enum colorActividad {

        EVENTO("#85C1E9"),

        TAREA("#58D68D");

        private final String claveColor;

        colorActividad(String claveColor) {
            this.claveColor = claveColor;
        }

        public String obtenerCodigoColor() {
            return this.claveColor;
        }
    }
}

