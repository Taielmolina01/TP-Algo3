package org.example;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        String cadena = "todos los martes, miercoles y jueves";
        String cadenaSinComas = cadena.replace(",", " ").replace("  ", " ");
        for (String palabra : cadenaSinComas.split(" ")) {
            System.out.println(palabra);
        }
    }
}