package org.example;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class formateador {

    public static DateTimeFormatter formatterConHoras = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static DateTimeFormatter formatterSinHoras = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatearDuracion(Duration duracion) {
        var s = duracion.getSeconds();
        String duracionFormateada = String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
        if (duracionFormateada.length() == 7) {
            duracionFormateada = "0" + duracionFormateada;
        }
        return duracionFormateada;
    }

    public static Duration formatearDuracion(String intervalo) {
        String[] formateado = intervalo.split(":");
        int horas;
        int minutos;
        int segundos;
        try {
            horas = Integer.parseInt(formateado[0]);
            minutos = Integer.parseInt(formateado[1]);
            segundos = Integer.parseInt(formateado[2]);
        } catch (NumberFormatException e1) {
            return null;
        }
        if (formateado.length != 3) {
            return null;
        } else {
            return Duration.ofHours(horas).plusMinutes(minutos).plusSeconds(segundos);
        }
    }
}
