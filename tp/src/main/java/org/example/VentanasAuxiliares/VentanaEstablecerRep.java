package org.example.VentanasAuxiliares;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaEstablecerRep implements Initializable {

    @FXML
    private Button botonOk;
    @FXML
    private TextField repeticion;
    @FXML
    private Text mensaje;
    @FXML
    private AnchorPane anchorPane;
    private Integer repeticiones;

    public void start(String titulo, String promptText) throws Exception {
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
        this.anchorPane.requestFocus();
    }

    @FXML
    public void clickOk(ActionEvent event) {
        String repeticion = this.repeticion.getText();
        try {
            this.repeticiones = Integer.parseInt(repeticion);
            this.mensaje.setText("✓ Repeticion agregada");
            this.repeticion.clear();
        } catch (NumberFormatException e1) {
            this.mensaje.setText("✕ No se ha ingresado un número");
        }
    }

    public Integer obtenerRepeticiones() {
        return this.repeticiones;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.botonOk.setOnAction(this::clickOk);
    }
}
