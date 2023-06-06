package org.example.VistaActividades;

import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class manejadorCeldasListView extends ListCell<String> {

    ArrayList<vistaActividad> vistaActividades;

    public manejadorCeldasListView(ArrayList<vistaActividad> vistaActividades) {
        this.vistaActividades = vistaActividades;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setText(item);
            int i = getIndex();
            setBackground(new Background(new BackgroundFill(Color.web(this.vistaActividades.get(i).obtenerCodigoColorFondo()), null, null)));
            /*if (this.vistaActividades.get(i).obtenerCodigoColorFondo().equals("#58D68D")) {
                var check = new CheckBox();
                check.setLayoutX(572);
                check.setLayoutY(123);
                this.getChildren().add(check);
            }
             */
        } else {
            setText(null);
        }
    }

    public void setVistaActividades(ArrayList<vistaActividad> vistaActividades) {
        this.vistaActividades = vistaActividades;
    }
}