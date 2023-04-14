package org.example;

public class Tarea {

    private String titulo;
    private String descripcion;

    private boolean completada;

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public void toggleTarea() {
        completada = !completada;
    }
}
