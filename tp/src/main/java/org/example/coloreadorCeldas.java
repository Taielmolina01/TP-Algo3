package org.example;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.example.Visitadores.visitadorElementosCalendario;

import java.util.ArrayList;

public class coloreadorCeldas extends ListCell<String> {

    ArrayList<ArrayList<String>> info;

    private final String colorAzul = "#85C1E9";
    private final String colorVerde = "#58D68D";

    public coloreadorCeldas(ArrayList<ArrayList<String>> info) {
        this.info = info;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setText(item);
            int i = getIndex();
            if (this.info.get(2).get(i).equals(visitadorElementosCalendario.colorFondo.AZUL.toString())) {
                setBackground(new Background(new BackgroundFill(Color.web(this.colorAzul), null, null)));
            } else {
                setBackground(new Background(new BackgroundFill(Color.web(this.colorVerde), null, null)));
            }
        } else {
            setText(null);
        }
    }

    protected void actualizarInfo(ArrayList<ArrayList<String>> info) {
        this.info = info;
    }
}