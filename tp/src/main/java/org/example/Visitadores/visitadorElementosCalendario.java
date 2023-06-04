package org.example.Visitadores;

import org.example.Alarma.Alarma;
import org.example.Actividades.Actividad;
import org.example.Actividades.Evento;
import org.example.Actividades.Tarea;
import org.example.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class visitadorElementosCalendario implements visitorElementos {

    public enum colorFondo {
        AZUL,
        VERDE;
    }

    private visitorFrecuencia visitanteFrecuencia = new visitadorEventosFrecuencia();

    public ArrayList<ArrayList<String>> visitarElementos(List<Actividad> elementos) { // ver bien como imprimir la info
        ArrayList<ArrayList<String>> infoElementosActuales = new ArrayList<>();
        ArrayList<String> infoResumida = new ArrayList<>();
        ArrayList<String> infoCompleta = new ArrayList<>();
        ArrayList<String> colores = new ArrayList<>();
        for (Actividad elemento : elementos) {
            String resumida = elemento.obtenerInfoResumida(this);
            String completa = elemento.obtenerInfoCompleta(this);
            String color = elemento.obtenerColor(this).toString();
            infoResumida.add(resumida);
            infoCompleta.add(completa);
            colores.add(color);
        }
        infoElementosActuales.add(infoResumida);
        infoElementosActuales.add(infoCompleta);
        infoElementosActuales.add(colores);
        return infoElementosActuales;
    }

    public String obtenerInfoResumida(Evento evento) {
        if (evento.obtenerTodoElDia()) {
            return "Nombre: " + evento.obtenerNombre() + ". Fecha de inicio: " + evento.obtenerFechaInicio().toLocalDate().format(Main.formatterSinHoras)
                    + ". Es de dia completo.";
        }
        return "Nombre: " + evento.obtenerNombre() + ". Fecha de inicio: " + evento.obtenerFechaInicio().format(Main.formatterConHoras) + ". Fecha fin: " +
                evento.obtenerFechaFinalDefinitivo().format(Main.formatterConHoras) + ".";
    }

    public String obtenerInfoCompleta(Evento evento) {
        String resultado;
        String fechaInicio = evento.obtenerTodoElDia() ? evento.obtenerFechaInicio().toLocalDate().format(Main.formatterSinHoras) : evento.obtenerFechaInicio().format(Main.formatterConHoras);
        String esDeDiaCompleto = evento.obtenerTodoElDia() ? " Es de dia completo." : "";
        resultado = "Nombre: " + evento.obtenerNombre() + "." + "\nDescripción: " + evento.obtenerDescripcion() + "." + "\nFecha de inicio: "
        + fechaInicio + "." + esDeDiaCompleto + "\nFecha fin: " + evento.obtenerFechaFinalDefinitivo().format(Main.formatterConHoras) + ".";
        HashMap<Integer, Alarma> alarmas = evento.obtenerAlarmas();
        String stringAlarmas = "";
        if (alarmas.size() == 0) {
            resultado += "\nEste evento no tiene alarmas configuradas.";
        } else {
            for (Alarma alarma : alarmas.values()) {
                stringAlarmas += alarma.obtenerFechaActivacion().format(Main.formatterConHoras) + ", ";
            }
            resultado += "\nFechas alarmas: " + stringAlarmas;
        }
        if (evento.obtenerFrecuencia() != null) { // si no tiene frecuencia es que no se repite nunca
            resultado += "\n" + evento.obtenerFrecuencia().obtenerTipoFrecuencia(this.visitanteFrecuencia);
        }
        return resultado;
    }


    public String obtenerInfoResumida(Tarea tarea) {
        String estado;
        estado = tarea.estaCompletada() ? "Completada." : "No completada.";
        if (tarea.obtenerTodoElDia()) {
            return "Nombre: " + tarea.obtenerNombre() + ". Fecha de inicio: " + tarea.obtenerFechaInicio().toLocalDate().format(Main.formatterSinHoras) + ". Es de dia completo" +
                    ". Estado: " + estado;
        }
        return "Nombre: " + tarea.obtenerNombre() + ". Fecha de inicio: " + tarea.obtenerFechaInicio().format(Main.formatterConHoras) + ". Estado: " +
                estado;
    }

    public String obtenerInfoCompleta(Tarea tarea) {
        String resultado;
        String fechaInicio = tarea.obtenerTodoElDia() ? tarea.obtenerFechaInicio().toLocalDate().format(Main.formatterSinHoras) : tarea.obtenerFechaInicio().format(Main.formatterConHoras);
        String esDeDiaCompleto = tarea.obtenerTodoElDia() ? " Es de dia completo." : "";
        resultado = "Nombre: " + tarea.obtenerNombre() + "." + "\nDescripción: " + tarea.obtenerDescripcion() + "." + "\nFecha de inicio: "
                + fechaInicio + "." + esDeDiaCompleto;
        HashMap<Integer, Alarma> alarmas = tarea.obtenerAlarmas();
        String stringAlarmas = "";
        if (alarmas.size() == 0) {
            resultado += "\nEsta tarea no tiene alarmas configuradas.";
        } else {
            for (Alarma alarma : alarmas.values()) {
                stringAlarmas += alarma.obtenerFechaActivacion().format(Main.formatterConHoras);
            }
            resultado += "\nFechas alarmas: " + stringAlarmas;
        }
        return resultado;
    }

    public colorFondo obtenerColor(Evento evento) {
        return colorFondo.AZUL;
    }

    public colorFondo obtenerColor(Tarea tarea) {
        return colorFondo.VERDE;
    }

}
