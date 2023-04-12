package org.example;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        Duration duracion = Duration.ofDays(2);
        System.out.println(duracion.getSeconds());

    }
}