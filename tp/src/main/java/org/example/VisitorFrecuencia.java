package org.example;

import org.example.Frecuencia.FrecuenciaAnual;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.Frecuencia.FrecuenciaMensual;
import org.example.Frecuencia.FrecuenciaSemanal;

public interface VisitorFrecuencia {

    String obtenerFrecuencia(FrecuenciaDiaria frecuencia);
    String obtenerFrecuencia(FrecuenciaSemanal frecuencia);
    String obtenerFrecuencia(FrecuenciaMensual frecuencia);
    String obtenerFrecuencia(FrecuenciaAnual frecuencia);
}
