package org.example;

import org.example.Frecuencia.FrecuenciaDiaria;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface interfazGuardarActividadNueva {

    void guardarEventoSinRepeticion(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                            ArrayList<Duration> duracionesAlarmas) throws IOException;

    void guardarEventoRepeticionDiaria(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion, boolean diaCompleto,
                            LocalDateTime fechaFinal, FrecuenciaDiaria frecuencia, ArrayList<Duration> duracionesAlarmas) throws IOException;

    void guardarTarea(String nombre, String descripcion, LocalDateTime fechaInicio, boolean diaCompleto, ArrayList<Duration> duracionesAlarmas)
            throws IOException;

}
