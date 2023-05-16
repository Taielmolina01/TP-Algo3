package org.example;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ManejadorGuardadoTest {

    @Test
    public void testArchivoRecuperadoNoExiste() {
        new File("MiCalendario.txt").delete();
        Calendario calendario = new Calendario();
        assertNull(calendario.recuperarEstado());
        assertEquals("El archivo de recuperado no existe.", calendario.obtenerSalidaManejador());
    }

    @Test
    public void testArchivoGuardadoNoExiste() {
        Calendario calendario = new Calendario();

        ManejadorGuardado manejador = new ManejadorGuardado();

        manejador.guardarEstado("carpetaInexistente/MICALENDARIO.txt", calendario);
        // Si envio un directorio valido, FileOutputStream crea el archivo con el nombre enviado y nunca caería en el FileNotFoundException.

        assertEquals("El archivo de guardado no existe.", manejador.salida.obtenerLoQueSeImprimio());
    }

    @Test
    public void testGuardarYRecuperarCalendarioVacio() {
        Calendario calendario1 = new Calendario();
        calendario1.guardarEstado();
        Calendario calendario2 = (new Calendario()).recuperarEstado();
        assertThrows(NullPointerException.class, () -> calendario2.obtenerNombre(0));
    }
}