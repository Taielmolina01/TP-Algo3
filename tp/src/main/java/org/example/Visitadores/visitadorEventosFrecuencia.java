package org.example.Visitadores;

import org.example.Frecuencia.*;

import java.time.DayOfWeek;

public class visitadorEventosFrecuencia implements visitorFrecuencia {

    private String frecuenciaMensaje;


    public String obtenerMensajeFrecuencia() {
        return this.frecuenciaMensaje;
    }

    public void obtenerTipoFrecuencia(FrecuenciaDiaria frecuencia) {
        this.frecuenciaMensaje = "Se repite cada " + frecuencia.obtenerValorRepeticion() + " día" + esPlural(frecuencia) + ".";
    }

    public void obtenerTipoFrecuencia(FrecuenciaSemanal frecuencia) { // ver hacerlo por cada semana seria sin s
        if (frecuencia.obtenerDiasSemana().size() == 7) {
            this.frecuenciaMensaje = "Se repite todos los días cada " + frecuencia.obtenerValorRepeticion() + " semana" + esPlural(frecuencia) + ".";
        }
        String resultado = "Se repite los ";
        for (DayOfWeek dia : frecuencia.obtenerDiasSemana()) {
            resultado += dia.name() + ", ";
        }
        this.frecuenciaMensaje = resultado + "cada " + frecuencia.obtenerValorRepeticion() + " semana" + esPlural(frecuencia) + ".";
    }

    public void obtenerTipoFrecuencia(FrecuenciaMensual frecuencia) {
        this.frecuenciaMensaje = "Se repite cada " + frecuencia.obtenerValorRepeticion() + " mes" + esPluralMeses(frecuencia) + ".";
    }

    public void obtenerTipoFrecuencia(FrecuenciaAnual frecuencia) {
        this.frecuenciaMensaje = "Se repite cada " + frecuencia.obtenerValorRepeticion() + " año" + esPlural(frecuencia) + ".";
    }

    private String esPlural(Frecuencia frecuencia) {
        return frecuencia.obtenerValorRepeticion() > 1 ? "s" : "";
    }

    private String esPluralMeses(FrecuenciaMensual frecuencia) {
        return frecuencia.obtenerValorRepeticion() > 1 ? "es" : "";
    }
}
