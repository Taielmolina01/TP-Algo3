package org.example;


import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime hoy = LocalDateTime.parse("2018-10-10T11:25");
        System.out.println(hoy.isAfter(LocalDateTime.parse("2018-10-10T11:26")));
    }
}