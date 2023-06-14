package org.example.Visitadores;

import org.example.Frecuencia.FrecuenciaAnual;
import org.example.Frecuencia.FrecuenciaDiaria;
import org.example.Frecuencia.FrecuenciaMensual;
import org.example.Frecuencia.FrecuenciaSemanal;

public interface VisitorFrecuencia {

    void obtenerTipoFrecuencia(FrecuenciaDiaria frecuencia);

    void obtenerTipoFrecuencia(FrecuenciaSemanal frecuencia);

    void obtenerTipoFrecuencia(FrecuenciaMensual frecuencia);

    void obtenerTipoFrecuencia(FrecuenciaAnual frecuencia);
}
