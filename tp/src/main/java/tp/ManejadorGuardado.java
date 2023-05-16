package tp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ManejadorGuardado implements Serializable {

    private final String nombreArchivoGuardado = "MiCalendario.txt";
    protected final Pantalla printStreamMock;
    private final OutputStream salida = System.out;

    public ManejadorGuardado() {
        this.printStreamMock = new Pantalla(new PrintStream(this.salida));
    }

    protected void guardarEstado(Calendario calendario) {
        this.crearArchivoGuardado();
        try {
            FileOutputStream archivoDestino = new FileOutputStream(this.nombreArchivoGuardado);
            calendario.serializar(archivoDestino);
        } catch (IOException e) {
            this.printStreamMock.println("No existe el archivo de guardado.");
        }
    }

    protected static Calendario recuperarEstado(InputStream is) {
        return Calendario.deserializar(is);
    }

    protected void borrarEstadoGuardado() {
        try (BufferedWriter bf = Files.newBufferedWriter(Path.of(this.nombreArchivoGuardado),
                StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearArchivoGuardado() {
        try {
            File archivo = new File(this.nombreArchivoGuardado);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            this.printStreamMock.println("No se pudo crear el archivo de guardado.");
        }
    }
}