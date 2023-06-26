package org.example;

public class prueba {

    public static void main(String[] args) {
        try {
            aMethod();
            System.out.println("F");
        } catch (NumberFormatException e) {
            System.out.println("B");
        } finally {
            System.out.println("C");
        }
        System.out.println("D");
    }

    public static void aMethod() {
        System.out.println("A" + 1/0);
    }
}
