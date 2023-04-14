package org.example;

public class Tarea {

    private final String titulo;
    private final String descripcion;

    private boolean completada;

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String verTitulo() {
        return  this.titulo;
    }

    public String verDescripcion() {
        return  this.descripcion;
    }

    public void toggleTarea() {
        this.completada = !this.completada;
    }

    public boolean estaCompletada() {
        return this.completada;
    }
}
