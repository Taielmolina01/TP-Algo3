package org.example.Visitadores;

import org.example.Alarma.Alarma;
import org.example.ElementosCalendario.ElementoCalendario;
import org.example.ElementosCalendario.Evento;
import org.example.ElementosCalendario.Tarea;
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

    public ArrayList<ArrayList<String>> visitarElementos(List<ElementoCalendario> elementos) { // ver bien como imprimir la info
        ArrayList<ArrayList<String>> infoElementos = new ArrayList<>();
        ArrayList<String> infoResumida = new ArrayList<>();
        ArrayList<String> infoCompleta = new ArrayList<>();
        ArrayList<String> colores = new ArrayList<>();
        for (ElementoCalendario elemento : elementos) {
            String resumida = elemento.obtenerInfoResumida(this);
            String completa = elemento.obtenerInfoCompleta(this);
            String color = elemento.obtenerColor(this).toString();
            infoResumida.add(resumida);
            infoCompleta.add(completa);
            colores.add(color);
        }
        infoElementos.add(infoResumida);
        infoElementos.add(infoCompleta);
        infoElementos.add(colores);
        return infoElementos;
    }

    public String obtenerInfoResumida(Evento evento) {
        if (evento.obtenerTodoElDia()) {
            return "Nombre: " + evento.obtenerNombre() + ". Fecha de inicio: " + evento.obtenerFechaInicio().toLocalDate().format(Main.formatter2)
                    + ". Es de dia completo.";
        }
        return "Nombre: " + evento.obtenerNombre() + ". Fecha de inicio: " + evento.obtenerFechaInicio().format(Main.formatter) + ". Fecha fin: " +
                evento.obtenerFechaFinalDefinitivo().format(Main.formatter) + ".";
    }

    public String obtenerInfoCompleta(Evento evento) {
        String resultado;
        resultado = obtenerInfoResumida(evento) + "\nDescripción: " + evento.obtenerDescripcion();
        HashMap<Integer, Alarma> alarmas = evento.obtenerAlarmas();
        String stringAlarmas = "";
        for (Alarma alarma : alarmas.values()) {
            stringAlarmas += alarma.obtenerFechaActivacion().format(Main.formatter);
        }
        resultado += "\nFechas alarmas: " + stringAlarmas;
        if (evento.obtenerFrecuencia() != null) { // si no tiene frecuencia es que no se repite nunca
            resultado += "\n" + evento.obtenerFrecuencia().obtenerTipoFrecuencia(this.visitanteFrecuencia);
        }
        return resultado;
    }


    public String obtenerInfoResumida(Tarea tarea) {
        String estado;
        estado = tarea.estaCompletada() ? "Completada" : "No completada";
        if (tarea.obtenerTodoElDia()) {
            return "Nombre: " + tarea.obtenerNombre() + ". Fecha de inicio: " + tarea.obtenerFechaInicio().toLocalDate().format(Main.formatter2) + ". Es de dia completo" +
                    ". Estado: " + estado + ".";
        }
        return "Nombre: " + tarea.obtenerNombre() + ". Fecha de inicio: " + tarea.obtenerFechaInicio().format(Main.formatter) + ". Estado: " +
                estado + ".";
    }

    public String obtenerInfoCompleta(Tarea tarea) {
        String resultado;
        resultado = obtenerInfoResumida(tarea) + "\n Descripción: " + tarea.obtenerDescripcion();
        HashMap<Integer, Alarma> alarmas = tarea.obtenerAlarmas();
        String stringAlarmas = "";
        for (Alarma alarma : alarmas.values()) {
            stringAlarmas += alarma.obtenerFechaActivacion().format(Main.formatter);
        }
        resultado += "\n Fechas alarmas: " + stringAlarmas;
        return resultado;
    }

    public colorFondo obtenerColor(Evento evento) {
        return colorFondo.AZUL;
    }

    public colorFondo obtenerColor(Tarea tarea) {
        return colorFondo.VERDE;
    }

}
