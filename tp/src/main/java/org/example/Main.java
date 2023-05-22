package org.example;

import javafx.application.Application;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var label = new Label("Hola mundo!");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){

    }
}