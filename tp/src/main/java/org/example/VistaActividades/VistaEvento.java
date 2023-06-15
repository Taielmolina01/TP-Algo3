package org.example.VistaActividades;

import org.example.Actividades.Evento;
import org.example.Formateador;
import org.example.Visitadores.VisitadorEventosFrecuencia;

import java.time.LocalDateTime;

public class VistaEvento extends VistaActividad {

    Evento evento;

    public VistaEvento(Evento e) {
        super(e);
        this.evento = e;
        this.setInfoResumida();
        this.setInfoCompleta();
    }

    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "Nombre: " + this.actividad.obtenerNombre() + ".\n\n";
        if (!this.actividad.obtenerDescripcion().equals("")) {
            this.infoCompleta += "Descripción: " + this.actividad.obtenerDescripcion() + ".\n\n";
        }
        this.infoCompleta += "Fecha: " + this.obtenerStringFechaInicio() + ".";
        if (this.actividad.obtenerTodoElDia()) {
            this.infoCompleta += todoElDia + "\n\n";
        } else {
            this.infoCompleta += " Duración: " + Formateador.formatearDuracion(this.evento.obtenerDuracion()) + "hs" + "\n\n";
        }
        this.infoCompleta += this.obtenerStringAlarmas() + "\n\n";
        if (this.evento.obtenerFechaFinalDefinitivo().equals(LocalDateTime.MAX)) {
            this.infoCompleta += "Se repite infinitamente el evento.\n\n";
        } else {
            this.infoCompleta += "Fecha final: " + this.evento.obtenerFechaFinalDefinitivo().format(Formateador.formatterConHoras) + "hs" + ".\n\n";
        }
        var v = new VisitadorEventosFrecuencia();
        this.evento.visitarFrecuencia(v);
        if (this.evento.obtenerFrecuencia() != null) {
            this.infoCompleta += v.obtenerMensajeFrecuencia();
        }
    }

    @Override
    public boolean llevaCheckbox() {
        return false;
    }

    @Override
    public boolean checkBoxSeleccionado() {
        return false;
    }

    public void cambiarSeleccionCheckBox() {
    }

    ;

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.EVENTO.obtenerCodigoColor();
    }

}
