package org.example;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class PrintStreamMock extends PrintStream {

    private final OutputStream out;
    private String loQueSeImprimio = "";

    public PrintStreamMock(OutputStream out) {
        super(out);
        this.out = out;
    }
    
    public void println(String mensaje) {
        this.loQueSeImprimio = mensaje;
        super.println(mensaje);
    }

    public String obtenerLoQueSeImprimio() {
        return this.loQueSeImprimio;
    }
}
