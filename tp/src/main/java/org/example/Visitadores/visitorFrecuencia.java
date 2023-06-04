package org.example.Visitadores;

import org.example.Frecuencia.FrecuenciaAnual;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.Frecuencia.FrecuenciaMensual;
import org.example.Frecuencia.FrecuenciaSemanal;

public interface visitorFrecuencia {

    String obtenerFrecuencia(FrecuenciaDiaria frecuencia);

    String obtenerFrecuencia(FrecuenciaSemanal frecuencia);

    String obtenerFrecuencia(FrecuenciaMensual frecuencia);

    String obtenerFrecuencia(FrecuenciaAnual frecuencia);
}
