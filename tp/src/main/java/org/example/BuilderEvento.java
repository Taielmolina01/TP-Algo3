package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class BuilderEvento {

    private Evento evento;

    public BuilderEvento() {

    }

    public void setNombre(String nombre) {
        evento.modificarNombre(nombre);
    }

    public void setDescripcion(String descripcion) {
        evento.modificarDescripcion(descripcion);
    }

    public void setFechaInicial(LocalDateTime fechaInicial) {
        evento.modificarFechaInicio(fechaInicial);
    }

    public void setDuracion(Duration duracion, boolean todoElDia) {
        evento.modificarDuracion(duracion);
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        evento.modificarFrecuencia(frecuencia);
    }

    public void setRepeticion(int repeticion) {
        evento.modificarOcurrencias(repeticion);
    }

    public Evento getResultado() {
        return this.evento;
    }
}
