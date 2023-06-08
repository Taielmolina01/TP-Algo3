package org.example.VistaActividades;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.example.Visitadores.visitadorActividades;

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
            // este get falla, dice que se accede a la posicion nueva (18 por ej) cuando vistaActividades es de largo 18 (cuando debería ser 19 porque justo
            // se agrego un elemento)
            setBackground(new Background(new BackgroundFill(Color.web(this.vistaActividades.get(i).obtenerCodigoColorFondo()), null, null)));
            var vistaActual = this.vistaActividades.get(i);
            if (vistaActual.obtenerCodigoColorFondo().equals(visitadorActividades.colorActividad.TAREA.getClaveColor())) {
                var check = new CheckBox();
                if (vistaActual.infoActividad.get(vistaActual.infoActividad.size()-1).equals("false")) {

                }
                setGraphic(new CheckBox());
            }
        } else {
            setText(null);
        }
    }

    public void modificarListaVistaActividades(ArrayList<vistaActividad> vistaActividades) {
        this.vistaActividades = vistaActividades;
    }
}