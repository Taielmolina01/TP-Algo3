package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime diaInicio = LocalDateTime.parse("2024-03-15T11:25");
        LocalDateTime diaFin = LocalDateTime.parse("2025-03-15T11:25");
        String nombreEvento = "Cumpleaños";
        String descripcionEvento = "De Bauti";
        Duration duracion = Duration.ofHours(24);
        //public Evento(String nombre, String descripcion, LocalDateTime fechaInicio, Duration duracion,
        //      Boolean todoElDia, LocalDateTime fechaFinalDefinitivo, frecuencia frecuencia) {
        Evento cumpleBauti = new Evento(nombreEvento, descripcionEvento, diaInicio,
                duracion, true, diaFin, frecuencia.SEMANAL, 1);
        System.out.println(diaInicio.plusMonths(1));
    }
}