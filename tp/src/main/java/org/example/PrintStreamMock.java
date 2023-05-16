package org.example;

import java.io.PrintStream;
import java.io.Serializable;

public class PrintStreamMock implements Serializable {

    private final PrintStream out;
    private String loQueSeImprimio = "";

    public PrintStreamMock(PrintStream out) {
        this.out = out;
    }

    public void println(String mensaje) {
        this.out.println(mensaje);
        this.loQueSeImprimio = mensaje;
    }

    public String obtenerLoQueSeImprimio() {
        return this.loQueSeImprimio;
    }
}
