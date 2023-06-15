package org.example;

import javafx.scene.Parent;

public class ModoApp {

    public static void setModo(modo modoActual, Parent parent) {
        parent.getStylesheets().setAll(modoActual.obtenerRutaArchivo());
    }

    public enum modo {

        CLARO("lightMode.css", "modeLogo.png"),

        OSCURO("darkMode.css", "modeLogo.png");

        private final String rutaArchivo;
        private final String rutaImagen;

        modo(String rutaArchivo, String rutaImagen) {
            this.rutaArchivo = rutaArchivo;
            this.rutaImagen = rutaImagen;
        }

        public String obtenerRutaArchivo() {
            return this.rutaArchivo;
        }

        public String obtenerRutaImagen() {
            return this.rutaImagen;
        }
    }
}
