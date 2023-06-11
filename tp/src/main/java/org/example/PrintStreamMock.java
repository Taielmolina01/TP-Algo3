package org.example;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamMock extends PrintStream {

    private String loQueSeImprimio = "";

    public PrintStreamMock(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String mensaje) {
        this.loQueSeImprimio = mensaje;
        super.println(mensaje);
    }

    public String obtenerLoQueSeImprimio() {
        return this.loQueSeImprimio;
    }
}
