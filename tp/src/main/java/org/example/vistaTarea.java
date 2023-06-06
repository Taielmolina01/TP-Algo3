package org.example;

import java.util.ArrayList;

public class vistaTarea extends vistaActividad {

    /*
        Info tarea [color, ID, nombre, descripcion, fechaInicio, todoElDia, alarmas, estaCompletada]
     */

    public vistaTarea(ArrayList<String> infoTarea) {
        super(infoTarea);
        this.setInfoResumida();
        this.setInfoCompleta();
    }

    /*
    En la celda deberia agregar el checkbox para
     */

    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "ID: " + this.infoActividad.get(1) + ".\n\n"
                    + "Nombre: " + this.infoActividad.get(2) + ".\n\n"
                    + "Descripción: " + this.infoActividad.get(3) + ".\n\n"
                    + "Fecha de inicio: " + this.infoActividad.get(4) + "." + todoElDia + "\n\n"
                    + this.infoActividad.get(6);
    }


}
