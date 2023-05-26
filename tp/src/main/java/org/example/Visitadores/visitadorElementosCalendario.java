package org.example.Visitadores;

import org.example.Alarma.Alarma;
import org.example.ElementosCalendario.ElementoCalendario;
import org.example.ElementosCalendario.Evento;
import org.example.ElementosCalendario.Tarea;
import org.example.Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class visitadorElementosCalendario implements visitorElementos {

    private visitorFrecuencia visitanteFrecuencia = new visitadorEventosFrecuencia();

    public ArrayList<ArrayList<String>> visitarElementos(List<ElementoCalendario> elementos) {
        ArrayList<ArrayList<String>> infoElementos = new ArrayList<>();
        ArrayList<String> infoResumida = new ArrayList<>();
        ArrayList<String> infoCompleta = new ArrayList<>();
        for (ElementoCalendario elemento : elementos) {
            String resumida = elemento.obtenerInfoResumida(this);
            String completa = elemento.obtenerInfoCompleta(this);
            infoResumida.add(resumida);
            infoCompleta.add(completa);
        }
        infoElementos.add(infoResumida);
        infoElementos.add(infoCompleta);
        return infoElementos;
    }

    public String obtenerInfoResumida(Evento evento) {
        if (evento.obtenerTodoElDia()) {
            return "Nombre: " + evento.obtenerNombre() + "Fecha de inicio: " + evento.obtenerFechaInicio() + ". Es de dia completo.";
        }
        return "Nombre: " + evento.obtenerNombre() + "Fecha de inicio: " + evento.obtenerFechaInicio() + ". Fecha fin: " +
                evento.obtenerFechaFinalDefinitivo() + ".";
    }

    public String obtenerInfoCompleta(Evento evento) {
        String resultado;
        resultado = obtenerInfoResumida(evento) + "\n Descripción: " + evento.obtenerDescripcion();
        HashMap<Integer, Alarma> alarmas = evento.obtenerAlarmas();
        String stringAlarmas = "";
        for (Alarma alarma : alarmas.values()) {
            stringAlarmas += alarma.obtenerFechaActivacion();
        }
        resultado += "\n Fechas alarmas: " + stringAlarmas;
        if (evento.obtenerFrecuencia() != null) { // si no tiene frecuencia es que no se repite nunca
            resultado += "\n" + evento.obtenerFrecuencia().obtenerTipoFrecuencia(this.visitanteFrecuencia);
        }
        return resultado;
    }


    public String obtenerInfoResumida(Tarea tarea) {
        if (tarea.obtenerTodoElDia()) {
            return "Nombre: " + tarea.obtenerNombre() + "Fecha de inicio: " + tarea.obtenerFechaInicio() + ". Es de dia completo" +
                    ". Estado: " + tarea.estaCompletada();
        }
        return "Nombre: " + tarea.obtenerNombre() + "Fecha de inicio: " + tarea.obtenerFechaInicio() + ". Estado: " +
                tarea.estaCompletada() + ".";
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
}
