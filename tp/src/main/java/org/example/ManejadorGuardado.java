package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ManejadorGuardado {

    protected final PrintStreamMock salida;
    private final PrintStream salidaReal;

    public ManejadorGuardado() {
        this.salidaReal = System.out;
        this.salida = new PrintStreamMock(this.salidaReal);
    }

    protected void guardarEstado(String rutaArchivo, Calendario calendario) {
        try {
            FileOutputStream archivoDestino = new FileOutputStream(rutaArchivo);
            calendario.serializar(archivoDestino);
        } catch (FileNotFoundException e) {
            this.salida.println("El archivo de guardado no existe.");
        }
    }

    protected Calendario recuperarEstado(String rutaArchivo) {
        try {
            FileInputStream archivo = new FileInputStream(rutaArchivo);
            return (new Calendario()).deserializar(archivo);
        } catch (FileNotFoundException e ) {
            this.salida.println("El archivo de recuperado no existe.");
            return null;
        }
    }

    protected void borrarEstadoGuardado(String rutaArchivo) {
        try (BufferedWriter bf = Files.newBufferedWriter(Path.of(rutaArchivo),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}