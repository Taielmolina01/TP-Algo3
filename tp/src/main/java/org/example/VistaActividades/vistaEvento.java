package org.example.VistaActividades;

import java.util.ArrayList;

public class vistaEvento extends vistaActividad {

    /*
        infoEvento : [ID, nombre, descripcion, fechaInicio, todoElDia, alarmas, fechaFinal, frecuencia]
    */

    public vistaEvento(ArrayList<String> infoEvento) {
        super(infoEvento);
        this.setInfoCompleta();
        this.setInfoResumida();
    }
    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "ID: " + this.infoActividad.get(0) + ".\n\n"
                + "Nombre: " + this.infoActividad.get(1) + ".\n\n"
                + "Descripción: " + this.infoActividad.get(2) + ".\n\n"
                + "Fecha de inicio: " + this.infoActividad.get(3) + "." + todoElDia + "\n\n"
                + this.infoActividad.get(5) + "\n\n"
                + "Fecha final: " + this.infoActividad.get(6) + ".\n\n"
                + this.infoActividad.get(7);
    }

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.EVENTO.getClaveColor();
    }

}
