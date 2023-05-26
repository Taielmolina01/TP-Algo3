package org.example.Visitadores;

import org.example.Frecuencia.FrecuenciaAnual;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.Frecuencia.FrecuenciaMensual;
import org.example.Frecuencia.FrecuenciaSemanal;

import java.time.DayOfWeek;

public class visitadorEventosFrecuencia implements visitorFrecuencia {

    public String obtenerFrecuencia(FrecuenciaDiaria frecuencia) {
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " días.";
    }

    public String obtenerFrecuencia(FrecuenciaSemanal frecuencia) { // ver hacerlo por cada semana seria sin s
        if (frecuencia.obtenerDiasSemana().size() == 7) {
            return "Se repite todos los días cada " + frecuencia.obtenerValorRepeticion() + " semanas.";
        }

        String resultado = "Se repite los ";
        for (DayOfWeek dia : frecuencia.obtenerDiasSemana()) {
            resultado += dia.name() + ", ";
        }
        return resultado + "cada " + frecuencia.obtenerValorRepeticion() + " semanas.";
    }

    public String obtenerFrecuencia(FrecuenciaMensual frecuencia) {
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " meses.";
    }

    public String obtenerFrecuencia(FrecuenciaAnual frecuencia) {
        return "Se repite cada " + frecuencia.obtenerValorRepeticion() + " años.";
    }
}
