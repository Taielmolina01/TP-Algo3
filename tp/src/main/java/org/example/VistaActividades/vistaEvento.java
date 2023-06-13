package org.example.VistaActividades;

import java.util.ArrayList;

public class vistaEvento extends vistaActividad {

    /*
        infoEvento : [ID, nombre, descripcion, fechaInicio, todoElDia, alarmas, duracion, fechaFinal, frecuencia]
    */

    public vistaEvento(ArrayList<String> infoEvento) {
        super(infoEvento);
        this.setInfoCompleta();
        this.setInfoResumida();
    }

    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "Nombre: " + this.infoActividad.get(1) + ".\n\n"
                + "Descripción: " + this.infoActividad.get(2) + ".\n\n"
                + "Fecha: " + this.infoActividad.get(3) + "hs" + ".";
        if (this.todoElDia) {
            this.infoCompleta += todoElDia + "\n\n";
        } else {
            this.infoCompleta += " Duración: " + this.infoActividad.get(6) + "hs" + "\n\n";
        }
        this.infoCompleta += this.infoActividad.get(5) + "\n\n"
                + "Fecha final: " + this.infoActividad.get(7) + "hs" + ".\n\n";
        if (this.infoActividad.get(8) != null) {
            this.infoCompleta += this.infoActividad.get(8);
        }

    }

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.EVENTO.obtenerCodigoColor();
    }

}
