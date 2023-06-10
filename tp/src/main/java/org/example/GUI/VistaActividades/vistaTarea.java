package org.example.VistaActividades;

import java.util.ArrayList;

public class vistaTarea extends vistaActividad {

    /*
        Info tarea [ID, nombre, descripcion, fechaInicio, todoElDia, alarmas, estaCompletada]
     */

    private boolean estaCompletada;

    public vistaTarea(ArrayList<String> infoTarea) {
        super(infoTarea);
        this.setInfoResumida();
        this.setInfoCompleta();
        this.estaCompletada = Boolean.parseBoolean(infoActividad.get(this.infoActividad.size()-1));
    }

    /*
    En la celda deberia agregar el checkbox para
     */

    public void setInfoCompleta() {
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoCompleta = "Nombre: " + this.infoActividad.get(1) + ".\n\n"
                + "Descripción: " + this.infoActividad.get(2) + ".\n\n"
                + "Fecha de inicio: " + this.infoActividad.get(3) + "." + todoElDia + "\n\n"
                + this.infoActividad.get(5) + "\n\n";
        if (this.estaCompletada) {
            this.infoCompleta += "La tarea está completada.";
        } else {
            this.infoCompleta += "La tarea no está completada.";
        }
    }

    @Override
    public String obtenerCodigoColorFondo() {
        return colorActividad.TAREA.getClaveColor();
    }


}
