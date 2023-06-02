package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ManejadorGuardado {

    private final PrintStream salida;
    private final String rutaArchivoGuardado = "MiCalendario.txt";
    private final File archivoGuardado = new File(this.rutaArchivoGuardado);

    public ManejadorGuardado(PrintStream salida) {
        this.salida = salida;
        this.crearArchivoGuardado();
    }

    protected void guardarEstado(Calendario calendario) throws IOException {
        try {
            FileOutputStream archivoDestino = new FileOutputStream(this.rutaArchivoGuardado);
            calendario.serializar(this.salida, archivoDestino);
        } catch (FileNotFoundException e) {
            this.salida.println("El archivo de guardado no existe.");
        }
        throw new FileNotFoundException("El archivo de guardado no existe.");
    }

    protected Calendario recuperarEstado() throws FileNotFoundException, IOException, ClassNotFoundException {
        try {
            FileInputStream archivo = new FileInputStream(this.rutaArchivoGuardado);
            return (new Calendario()).deserializar(this.salida, archivo);
        } catch (FileNotFoundException e) {
            this.salida.println("El archivo de recuperado no existe.");
            throw new FileNotFoundException("El archivo de recuperado no existe.");
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