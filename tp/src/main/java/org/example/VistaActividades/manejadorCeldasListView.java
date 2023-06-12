package org.example.VistaActividades;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.example.interfazCambioEstado;

public class manejadorCeldasListView extends ListCell<vistaActividad> {

    private CheckBox checkBox;
    private interfazCambioEstado i;

    public manejadorCeldasListView(interfazCambioEstado i) {
        this.i = i;
    }

    @Override
    protected void updateItem(vistaActividad item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setText(item.obtenerInfoResumida());
            int i = getIndex();
            setBackground(new Background(new BackgroundFill(Color.web(item.obtenerCodigoColorFondo()),
                    null, null)));
            if (item.obtenerCodigoColorFondo().equals(vistaActividad.colorActividad.TAREA.obtenerCodigoColor())) {
                var checkbox = getCheckBox(i);
                checkbox.setSelected(((vistaTarea) item).obtenerEstaCompletada());
                checkbox.setPrefSize(10, 10);
                setGraphic(checkbox);
            }
        } else {
            setText(null);
            setGraphic(null);
            setBackground(null);
        }
    }

    private CheckBox getCheckBox(int i){
        if(checkBox==null){
            checkBox = new CheckBox();
            checkBox.selectedProperty().addListener((obs,old,val)->{
                if(getItem() != null){
                    ((vistaTarea) getItem()).cambiarEstadoTarea();
                    this.i.huboCambioEstadoTarea(i);
                }
            });
        }
        return checkBox;
    }
}