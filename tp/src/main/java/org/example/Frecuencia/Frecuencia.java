package org.example.Frecuencia;


import org.example.Visitadores.VisitorFrecuencia;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Frecuencia implements Serializable {

    private int valorRepeticion;

    public Frecuencia(int frecuenciaRepeticiones) {
        this.valorRepeticion = frecuenciaRepeticiones;
    }

    public int obtenerValorRepeticion() {
        return this.valorRepeticion;
    }

    public void modificarValorRepeticion(int valorRepeticion) {
        this.valorRepeticion = valorRepeticion;
    }

    public abstract LocalDateTime obtenerProximaFecha(LocalDateTime fechaInicial);

    public abstract void obtenerTipoFrecuencia(VisitorFrecuencia visitante);

    public abstract LocalDateTime definirFechaInicio(LocalDateTime fechaInicial);

}
