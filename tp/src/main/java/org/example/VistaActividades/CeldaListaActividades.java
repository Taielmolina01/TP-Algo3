package org.example.VistaActividades;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.example.Actividades.Tarea;
import org.example.InterfazCambioEstado;

public class CeldaListaActividades extends ListCell<VistaActividad> {

    private final InterfazCambioEstado i;
    private CheckBox checkBox;

    public CeldaListaActividades(InterfazCambioEstado i) {
        this.i = i;
    }

    @Override
    protected void updateItem(VistaActividad item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setText(item.obtenerInfoResumida());
            int i = getIndex();
            setBackground(new Background(new BackgroundFill(Color.web(item.obtenerCodigoColorFondo()),
                    null, null)));
            if (item.llevaCheckbox()) {
                CheckBox checkbox = getCheckBox(i);
                checkbox.setSelected(item.checkBoxSeleccionado());
                checkbox.setPrefSize(10, 10);
                setGraphic(checkbox);
            }
        } else {
            setText(null);
            setGraphic(null);
            setBackground(null);
        }
    }

    private CheckBox getCheckBox(int i) {
        if (checkBox == null) {
            checkBox = new CheckBox();
            checkBox.setOnAction(e -> {
                getItem().cambiarSeleccionCheckBox();
                getItem().actualizarInfo();
                this.i.huboCambioEstadoTarea(i);
            });
        }
        return checkBox;
    }
}