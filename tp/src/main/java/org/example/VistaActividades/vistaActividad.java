package org.example.VistaActividades;

import java.util.ArrayList;

public abstract class vistaActividad {

    ArrayList<String> infoActividad;
    String infoResumida;
    String infoCompleta;
    boolean todoElDia;

    public vistaActividad(ArrayList<String> infoActividad) {
        this.infoActividad = infoActividad;
        this.todoElDia = Boolean.parseBoolean(this.infoActividad.get(4));
    }

    public void setInfoResumida() {
        this.infoResumida = "ID: " + this.infoActividad.get(0) +
                ". Nombre: " +  this.infoActividad.get(1);
        String todoElDia = this.setearTextoDiaCompleto();
        this.infoResumida += ". Fecha de inicio: " + this.infoActividad.get(3) + "." + todoElDia;
    }

    public abstract void setInfoCompleta();

    protected String setearTextoDiaCompleto() {
        String todoElDia = "";
        if (this.todoElDia) {
            todoElDia = " Es de día completo";
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

    public enum colorActividad {

        EVENTO("#85C1E9"),

        TAREA("#58D68D");

        private final String claveColor;

        colorActividad(String claveColor) {
            this.claveColor = claveColor;
        }

        public String getClaveColor() {
            return this.claveColor;
        }
    }

}

