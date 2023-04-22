package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class BuilderEvento {

    private Evento evento;

    public BuilderEvento() {

    }

    public void definirNombre(String nombre) {
        evento.modificarNombre(nombre);
    }

    public void definirDescripcion(String descripcion) {
        evento.modificarDescripcion(descripcion);
    }

    public  void definirTodoElDia(boolean todoElDia) {
        evento.modificarTodoElDia(todoElDia);
    }

    public void definirFechaInicial(LocalDateTime fechaInicial) {
        evento.modificarFechaInicio(fechaInicial);
    }

    public void definirDuracion(Duration duracion, boolean todoElDia) {
        evento.modificarDuracion(duracion);
    }

    public void definirFrecuencia(Frecuencia frecuencia) {
        evento.modificarFrecuencia(frecuencia);
    }

    public void definirRepeticion(int repeticion) {
        evento.modificarOcurrencias(repeticion);
    }

    public Evento obtenerResultado() {
        return this.evento;
    }
}
