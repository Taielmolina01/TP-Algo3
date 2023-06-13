package org.example.VistaActividades;

import org.example.Actividades.Evento;
import org.example.Visitadores.visitadorEventosFrecuencia;
import org.example.formateador;

public class vistaEvento extends vistaActividad {

    Evento evento;

    public vistaEvento(Evento e) {
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
            this.infoCompleta += " Duración: " + formateador.formatearDuracion(this.evento.obtenerDuracion()) + "hs" + "\n\n";
        }
        this.infoCompleta += this.obtenerStringAlarmas() + "\n\n"
                + "Fecha final: " + this.evento.obtenerFechaFinalDefinitivo().format(formateador.formatterConHoras) + "hs" + ".\n\n";
        var v = new visitadorEventosFrecuencia();
        this.evento.visitarFrecuencia(v);
        if (this.evento.obtenerFrecuencia() != null) {
            this.infoCompleta += v.obtenerMensajeFrecuencia();
        }
    }

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.EVENTO.obtenerCodigoColor();
    }

}
