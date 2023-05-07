package org.example;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        HashSet<DayOfWeek> setPrueba = new HashSet<>();

        setPrueba.add(DayOfWeek.MONDAY);
        setPrueba.add(DayOfWeek.THURSDAY);
        setPrueba.add(DayOfWeek.WEDNESDAY);
        setPrueba.add(DayOfWeek.TUESDAY);

        ArrayList<DayOfWeek> prueba = new ArrayList<>();
        prueba.addAll(setPrueba);

        System.out.println(setPrueba);

        prueba.sort(new Comparator<DayOfWeek>() {
            @Override
            public int compare(DayOfWeek o1, DayOfWeek o2) {
                return o1.compareTo(o2);
            }
        });


    }
}