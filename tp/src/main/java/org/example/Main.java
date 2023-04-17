package org.example;


import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime hoy = LocalDateTime.parse("2018-01-10T11:25");
        System.out.println(hoy.toLocalDate());
        System.out.println(hoy.toLocalDate().atStartOfDay());
    }
}