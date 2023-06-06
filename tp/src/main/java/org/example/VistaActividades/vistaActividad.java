package org.example.VistaActividades;

import java.util.ArrayList;

public abstract class vistaActividad {

    ArrayList<String> infoActividad;

    String infoResumida;
    String infoCompleta;
    boolean todoElDia;

    public vistaActividad(ArrayList<String> infoActividad) {
        this.infoActividad = infoActividad;
        this.todoElDia = Boolean.parseBoolean(this.infoActividad.get(5));
    }

    public void setInfoResumida() {
        this.infoResumida = "ID: " + this.infoActividad.get(1) +
                ". Nombre: " +  this.infoActividad.get(2);
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoResumida += ". Fecha de inicio: " + this.infoActividad.get(4) + "." + todoElDia;
    }

    public abstract void setInfoCompleta();

    protected String setearTextoDiaCompleto() {
        String todoElDia = "";
        if (this.todoElDia) {
            todoElDia = " Es de día completo";
        }
        return todoElDia;
    }

    public String obtenerCodigoColorFondo() {
        return this.infoActividad.get(0);
    }

    public String obtenerInfoResumida() {
        return this.infoResumida;
    }

    public String obtenerInfoCompleta() {
        return this.infoCompleta;
    }

}

