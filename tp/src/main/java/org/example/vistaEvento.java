package org.example;

import java.util.ArrayList;

public class vistaEvento extends vistaActividad {

    /*
        infoEvento : [color, ID, nombre, descripcion, fechaInicio, todoElDia, alarmas, fechaFinal, frecuencia]
    */

    public vistaEvento(ArrayList<String> infoEvento) {
        super(infoEvento);
    }
    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "ID: " + this.infoActividad.get(1) + ".\n\n"
                + "Nombre: " + this.infoActividad.get(2) + ".\n\n"
                + "Descripción: " + this.infoActividad.get(3) + ".\n\n"
                + "Fecha de inicio: " + this.infoActividad.get(4) + "." + todoElDia + "\n\n"
                + this.infoActividad.get(6) + "\n\n"
                + "Fecha final: " + this.infoActividad.get(7) + ".\n\n"
                + this.infoActividad.get(8);
    }

}
