package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ManejadorGuardado {

    protected final PrintStreamMock salida;
    private final PrintStream salidaReal;
    private final String rutaArchivoGuardado = "MiCalendario.txt";
    private File archivoGuardado;


    public ManejadorGuardado() {
        this.salidaReal = System.out;
        this.salida = new PrintStreamMock(this.salidaReal);
        this.crearArchivoGuardado();
    }

    protected void guardarEstado(Calendario calendario) {
        try {
            FileOutputStream archivoDestino = new FileOutputStream(this.rutaArchivoGuardado);
            calendario.serializar(archivoDestino);
        } catch (FileNotFoundException e) {
            this.salida.println("El archivo de guardado no existe.");
        }
    }

    protected Calendario recuperarEstado() {
        try {
            FileInputStream archivo = new FileInputStream(this.rutaArchivoGuardado);
            return (new Calendario()).deserializar(archivo);
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
        this.archivoGuardado = new File(rutaArchivoGuardado);
        try {
            this.archivoGuardado.createNewFile();
        } catch (IOException e) {
            //
        }
    }
}