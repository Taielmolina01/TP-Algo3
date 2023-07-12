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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Formateador;
import org.example.ModoApp;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class VentanaEstablecerRepSemanal implements Initializable {
    @FXML
    private AnchorPane parent;

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
    @FXML
    private CheckBox repsInfinitas;
    @FXML
    private TextField textFechaFinal;
    private Integer repeticiones;
    private LocalDateTime fechaFinal;
    private HashMap<CheckBox, DayOfWeek> mapDias;
    private CheckBox[] checkBoxes;
    private ModoApp.modo modoActual;


    public void start(ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaRepeticionSemanal.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("Definir frecuencia semanal");
        s.setResizable(false);
        s.setScene(scene);
        s.show();
        this.modoActual = modoActual;
        ModoApp.setStyleSheet(this.modoActual, this.parent);
    }

    @FXML
    public void clickOk(ActionEvent event) {
        String repeticion = this.semanas.getText();
        try {
            this.repeticiones = Integer.parseInt(repeticion);
            if (this.repsInfinitas.isSelected()) {
                if (this.obtenerDiasSemana().size() != 0) {
                    this.fechaFinal = LocalDateTime.MAX;
                    this.mensaje.setText("✓ Repeticion agregada");
                } else {
                    this.mensaje.setText("✕ Seleccione algún día");
                }
                return;
            }
            this.fechaFinal = LocalDateTime.parse(this.textFechaFinal.getText(), Formateador.formatterConHoras);
        } catch (NumberFormatException e1) {
            this.mensaje.setText("✕ No se ha ingresado un número");
            return;
        } catch (DateTimeParseException e2) {
            this.mensaje.setText("✕ No se ha ingresado una fecha final válida");
            return;
        }
        if (this.obtenerDiasSemana().size() == 0) {
            this.mensaje.setText("✕ Seleccione algún día");
            return;
        }
        this.mensaje.setText("✓ Repeticion agregada");
        this.textFechaFinal.clear();
        this.semanas.clear();
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }

    public LocalDateTime obtenerFechaFinal() {
        return this.fechaFinal;
    }

    public TreeSet<DayOfWeek> obtenerDiasSemana() {
        ArrayList<DayOfWeek> diasSeleccionados = new ArrayList<>();
        for (CheckBox c : this.checkBoxes) {
            if (c.isSelected()) {
                diasSeleccionados.add(this.mapDias.get(c));
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
        this.mapDias = new HashMap<>();
        this.mapDias.put(this.lunes, DayOfWeek.MONDAY);
        this.mapDias.put(this.martes, DayOfWeek.TUESDAY);
        this.mapDias.put(this.miercoles, DayOfWeek.WEDNESDAY);
        this.mapDias.put(this.jueves, DayOfWeek.THURSDAY);
        this.mapDias.put(this.viernes, DayOfWeek.FRIDAY);
        this.mapDias.put(this.sabado, DayOfWeek.SATURDAY);
        this.mapDias.put(this.domingo, DayOfWeek.SUNDAY);
    }

    private void crearArrayCheckBoxes() {
        this.checkBoxes = new CheckBox[]{this.lunes, this.martes, this.miercoles, this.jueves, this.viernes, this.sabado, this.domingo};
    }
}
