package org.example;

import java.io.*;

public class ManejadorGuardadoModo {
    private String rutaArchivoGuardado = "modoActual.txt";

    public ManejadorGuardadoModo() throws IOException {
        this.crearArchivoGuardado();
    }

    public void guardarModo(ModoApp.modo modoActual) throws IOException {
        FileOutputStream archivoDestino = new FileOutputStream(this.rutaArchivoGuardado);
        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(archivoDestino);
            o.writeObject(modoActual);
            o.flush();
        } catch (IOException e) {
            throw new IOException("El flujo de salida no existe");
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }

    public ModoApp.modo recuperarModo() throws IOException, ClassNotFoundException {
        FileInputStream archivoDestino = new FileInputStream(this.rutaArchivoGuardado);
        try {
            ObjectInputStream objectInStream = new ObjectInputStream(archivoDestino);
            return (ModoApp.modo) objectInStream.readObject();
        } catch (IOException e) {
            throw new IOException("Error cargando el modo");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("La clase modoActual no se encuentra en este paquete.");
        }
    }


    private void crearArchivoGuardado() throws IOException {
        try {
            File file = new File(this.rutaArchivoGuardado);
            if (file.createNewFile()) {
                guardarModo(ModoApp.modo.CLARO);
            }
        } catch (IOException e) {
            throw new IOException("No se ha podido crear el archivo de guardado del modo");
        }
    }

}
