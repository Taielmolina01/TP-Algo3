package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ManejadorGuardado {

    protected final PrintStream salida;
    private final String rutaArchivoGuardado = "MiCalendario.txt";
    private final File archivoGuardado = new File(rutaArchivoGuardado);


    public ManejadorGuardado(PrintStream salida) {
        this.salida = salida;
        this.crearArchivoGuardado();
    }

    protected void guardarEstado(Calendario calendario) {
        try {
            FileOutputStream archivoDestino = new FileOutputStream(this.rutaArchivoGuardado);
            calendario.serializar(salida, archivoDestino);
        } catch (FileNotFoundException e) {
            this.salida.println("El archivo de guardado no existe.");
        }
    }

    protected Calendario recuperarEstado() {
        try {
            FileInputStream archivo = new FileInputStream(this.rutaArchivoGuardado);
            return (new Calendario()).deserializar(salida, archivo);
        } catch (FileNotFoundException e ) {
            this.salida.println("El archivo de recuperado no existe.");
            return null;
        }
    }

    protected void borrarEstadoGuardado() {
        try (BufferedWriter bf = Files.newBufferedWriter(Path.of(this.rutaArchivoGuardado),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearArchivoGuardado() {
        try {
            this.archivoGuardado.createNewFile();
        } catch (IOException e) {
            //
        }
    }
}