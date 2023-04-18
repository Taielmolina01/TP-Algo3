package org.example;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime diaInicio = LocalDateTime.parse("2024-03-15T11:25");
        System.out.println(diaInicio.plusMonths(1));
    }
}