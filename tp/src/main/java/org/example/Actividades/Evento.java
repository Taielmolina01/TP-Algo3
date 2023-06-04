package org.example.Actividades;

import org.example.Frecuencia.Frecuencia;
import org.example.Visitadores.visitadorElementosCalendario;
import org.example.Visitadores.visitorElementos;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Evento extends Actividad implements Serializable {

    private LocalDateTime fechaFin; // Fin del evento sin contar sus repeticiones, NO es la fecha en donde terminan las repeticiones.
    private LocalDateTime fechaFinalRepeticion; // Fecha en la que terminan las repeticiones del evento.
    private Duration duracion;
    private Integer ocurrencias;
    private Frecuencia frecuencia;

    // Constructores.

    // Constructor si no se repite el evento nunca.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalRepeticion = this.fechaFin;
    }

    // Constructor si se repite el evento dada la fecha de fin.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, LocalDateTime fechaFinalRepeticion, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.fechaFinalRepeticion = fechaFinalRepeticion;
        this.frecuencia = frecuencia;
    }

    // Constructor si se repite el evento dada las veces que se va a repetir el evento.
    public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
                  boolean todoElDia, Integer ocurrencias, Frecuencia frecuencia) {
        super(nombre, descripcion, fechaInicio, todoElDia);
        this.definirDuracion(duracion);
        this.frecuencia = frecuencia;
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo();
    }

    public void modificarDuracion(Duration duracion) {
        this.duracion = duracion;
        this.calcularFechaFin();
    }

    public void modificarOcurrencias(Integer ocurrencias) {
        this.ocurrencias = ocurrencias;
        this.calcularFechaFinDefinitivo();
    }

    public void modificarFechaFinal(LocalDateTime fechaFinalRepeticion) {
        this.fechaFinalRepeticion = fechaFinalRepeticion;
    }

    public void modificarFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        if (this.ocurrencias != null) {
            this.calcularFechaFinDefinitivo();
        }
    }

    private void calcularFechaFinDefinitivo() {
        LocalDateTime fecha = this.fechaInicio;
        for (int i = 1; i < this.ocurrencias; i++) {
            fecha = this.frecuencia.obtenerProximaFecha(fecha);
        }
        this.fechaFinalRepeticion = fecha;
    }

    private void calcularFechaFin() {
        this.fechaFin = this.fechaInicio.plus(this.duracion);
    }

    private void definirDuracion(Duration duracion) {
        if (this.todoElDia) {
            this.duracion = Duration.ofHours(23).plusMinutes(59).plusSeconds(59);
        } else {
            this.duracion = duracion;
        }
        this.calcularFechaFin();
    }

    public Duration obtenerDuracion() {
        return this.duracion;
    }

    public LocalDateTime obtenerFechaFinalDefinitivo() {
        return this.fechaFinalRepeticion;
    }

    public Frecuencia obtenerFrecuencia() {
        return this.frecuencia;
    }


    @Override
    public ArrayList<LocalDateTime> elementosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        LocalDateTime dia = fechaInicial.isBefore(this.fechaInicio) ? this.fechaInicio : fechaInicial;
        ArrayList<LocalDateTime> eventos = new ArrayList<>();
        if (fechaInicial.isAfter(this.fechaFinalRepeticion)) {
            return eventos;
        }
        while (estaEntreFechas(dia, fechaInicial, this.fechaFinalRepeticion) && estaEntreFechas(dia, fechaInicial, fechaFinal)) {
            eventos.add(dia);
            dia = this.frecuencia.obtenerProximaFecha(dia);
        }
        return eventos;
    }

    public boolean hayEvento(LocalDateTime diaAAnalizar) {
        ArrayList<LocalDateTime> eventos = elementosEntreFechas(this.fechaInicio, diaAAnalizar);
        LocalDateTime ultimoDiaInicio = eventos.get(eventos.size() - 1);
        LocalDateTime ultimoDiaFin = ultimoDiaInicio.plus(this.duracion);
        return estaEntreFechas(diaAAnalizar, ultimoDiaInicio, ultimoDiaFin);
    }

    @Override
    public String obtenerInfoResumida(visitorElementos visitante) {
        return visitante.obtenerInfoResumida(this);
    }

    @Override
    public String obtenerInfoCompleta(visitorElementos visitante) {
        return visitante.obtenerInfoCompleta(this);
    }

    @Override
    public visitadorElementosCalendario.colorFondo obtenerColor(visitorElementos visitante) {
        return visitante.obtenerColor(this);
    }

}