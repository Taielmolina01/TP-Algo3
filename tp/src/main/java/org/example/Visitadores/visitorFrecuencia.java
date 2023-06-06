package org.example.Visitadores;

import org.example.Frecuencia.FrecuenciaAnual;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.Frecuencia.FrecuenciaMensual;
import org.example.Frecuencia.FrecuenciaSemanal;

public interface visitorFrecuencia {

    String obtenerTipoFrecuencia(FrecuenciaDiaria frecuencia);

    String obtenerTipoFrecuencia(FrecuenciaSemanal frecuencia);

    String obtenerTipoFrecuencia(FrecuenciaMensual frecuencia);

    String obtenerTipoFrecuencia(FrecuenciaAnual frecuencia);
}
