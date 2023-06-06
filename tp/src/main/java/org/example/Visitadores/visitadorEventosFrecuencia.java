package org.example.Visitadores;

import org.example.Frecuencia.*;

import java.time.DayOfWeek;

public class visitadorEventosFrecuencia implements visitorFrecuencia {

    private boolean noHayFrecuencia(Frecuencia frecuencia) {
        return frecuencia == null;
    }

    public String obtenerTipoFrecuencia(FrecuenciaDiaria frecuencia) {
        if (noHayFrecuencia(frecuencia)) {
            return "";
        }
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " día" + esPlural(frecuencia) + ".";
    }

    public String obtenerTipoFrecuencia(FrecuenciaSemanal frecuencia) { // ver hacerlo por cada semana seria sin s
        if (noHayFrecuencia(frecuencia)) {
            return "";
        }
        if (frecuencia.obtenerDiasSemana().size() == 7) {
            return "Se repite todos los días cada " + frecuencia.obtenerValorRepeticion() + " semana" + esPlural(frecuencia) + ".";
        }
        String resultado = "Se repite los ";
        for (DayOfWeek dia : frecuencia.obtenerDiasSemana()) {
            resultado += dia.name() + ", ";
        }
        return resultado + "cada " + frecuencia.obtenerValorRepeticion() + " semana" + esPlural(frecuencia) + ".";
    }

    public String obtenerTipoFrecuencia(FrecuenciaMensual frecuencia) {
        if (noHayFrecuencia(frecuencia)) {
            return "";
        }
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " mes" + esPluralMeses(frecuencia) + ".";
    }

    public String obtenerTipoFrecuencia(FrecuenciaAnual frecuencia) {
        if (noHayFrecuencia(frecuencia)) {
            return "";
        }
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " año" + esPlural(frecuencia) + ".";
    }

    private String esPlural(Frecuencia frecuencia) {
        return frecuencia.obtenerValorRepeticion() == 1 ? "s" : "";
    }

    private String esPluralMeses(FrecuenciaMensual frecuencia) {
        return frecuencia.obtenerValorRepeticion() == 1 ? "es" : "";
    }
}
