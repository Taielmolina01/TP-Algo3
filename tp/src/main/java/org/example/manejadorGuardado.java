package org.example;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class manejadorGuardado {

    protected static void guardarEstado(Calendario calendario) throws IOException {
        FileOutputStream archivoDestino = new FileOutputStream("MiCalendario.txt");
        ObjectOutputStream elementoAGuardar = new ObjectOutputStream(archivoDestino);
        elementoAGuardar.writeObject(calendario.elementosCalendario);
    }

    protected static void recuperarEstado(Calendario calendario) throws IOException {
        FileInputStream fis = new FileInputStream("MiCalendario.txt");
        calendario.elementosCalendario = new HashMap<>();
        while (true) {
            try (ObjectInputStream input = new ObjectInputStream(fis)) {
                HashMap<Integer, ElementoCalendario> obj = (HashMap<Integer, ElementoCalendario>) input.readObject();
                if (obj != null) {
                    calendario.elementosCalendario.putAll(obj);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Se terminó de leer la entrada.");
                break;
            }
        }
    }
}
