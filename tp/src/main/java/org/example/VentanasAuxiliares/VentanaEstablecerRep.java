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
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class VentanaEstablecerRep implements Initializable {

    @FXML
    private Button botonOk;
    @FXML
    private TextField repeticion;
    @FXML
    private Text mensaje;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField textFechaFinal;
    @FXML
    private CheckBox repsInfinitas;

    private LocalDateTime fechaFinal;
    private Integer repeticiones;
    private ModoApp.modo modoActual;

    public void start(String titulo, String promptText, ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaRepeticion.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(titulo);
        this.repeticion.setPromptText(promptText);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.modoActual = modoActual;
        this.parent.requestFocus();
        ModoApp.setModo(this.modoActual, this.parent);
    }

    @FXML
    public void clickOk(ActionEvent event) {
        try {
            this.repeticiones = Integer.parseInt(this.repeticion.getText());
            if (this.repsInfinitas.isSelected()) {
                this.fechaFinal = LocalDateTime.MAX;
                this.mensaje.setText("✓ Repeticion agregada");
                return;
            }
            this.fechaFinal = LocalDateTime.parse(this.textFechaFinal.getText(), Formateador.formatterConHoras);
        } catch (NumberFormatException e) {
            this.mensaje.setText("✕ No se ha ingresado un número");
            return;
        } catch (DateTimeParseException e2) {
            this.mensaje.setText("✕ No se ha ingresado una fecha final válida");
            return;
        }
        this.mensaje.setText("✓ Repeticion agregada");
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }

    public LocalDateTime obtenerFechaFinal() {
        return this.fechaFinal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.botonOk.setOnAction(this::clickOk);
    }

}
