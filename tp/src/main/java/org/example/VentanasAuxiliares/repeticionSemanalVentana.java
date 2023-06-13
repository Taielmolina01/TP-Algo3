package org.example.VentanasAuxiliares;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class repeticionSemanalVentana implements Initializable {

    @FXML
    private Button botonOk;
    @FXML
    private CheckBox lunes;
    @FXML
    private CheckBox martes;
    @FXML
    private CheckBox miercoles;
    @FXML
    private CheckBox jueves;
    @FXML
    private CheckBox viernes;
    @FXML
    private CheckBox sabado;
    @FXML
    private CheckBox domingo;
    @FXML
    private TextField semanas;
    @FXML
    private Text mensaje;

    private Integer repeticiones;
    private HashMap<CheckBox, DayOfWeek> map;
    private CheckBox[] checkBoxes;


    public void start() throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaRepeticionSemanal.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Definir frecuencia semanal");
        s.setResizable(false);
        s.setScene(scene);
        s.show();
    }

    @FXML
    public void clickOk(ActionEvent event) {
        String repeticion = this.semanas.getText();
        try {
            this.repeticiones = Integer.parseInt(repeticion);
            this.mensaje.setText("✓ Repeticion agregada");
        } catch (NumberFormatException e1) {
            this.mensaje.setText("✕ No se ha ingresado un número");
        }
        if (this.obtenerDiasSemana().size() == 0) {
            this.mensaje.setText("✕ Seleccione algún día");
        }

    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }

    public TreeSet<DayOfWeek> obtenerDiasSemana() {
        ArrayList<DayOfWeek> diasSeleccionados = new ArrayList<>();
        for (CheckBox c : this.checkBoxes) {
            if (c.isSelected()) {
                diasSeleccionados.add(this.map.get(c));
            }

        }
        return new TreeSet<>(diasSeleccionados);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.botonOk.setOnAction(this::clickOk);
        this.crearMapeo();
        this.crearArrayCheckBoxes();
    }

    private void crearMapeo() {
        this.map = new HashMap<>();
        this.map.put(lunes, DayOfWeek.MONDAY);
        this.map.put(martes, DayOfWeek.TUESDAY);
        this.map.put(miercoles, DayOfWeek.WEDNESDAY);
        this.map.put(jueves, DayOfWeek.THURSDAY);
        this.map.put(viernes, DayOfWeek.FRIDAY);
        this.map.put(sabado, DayOfWeek.SATURDAY);
        this.map.put(domingo, DayOfWeek.SUNDAY);
    }

    private void crearArrayCheckBoxes() {
        this.checkBoxes = new CheckBox[]{this.lunes, this.martes, this.miercoles, this.jueves, this.viernes, this.sabado, this.domingo};
    }
}
