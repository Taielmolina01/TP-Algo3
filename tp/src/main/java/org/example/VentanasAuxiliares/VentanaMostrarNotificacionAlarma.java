package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.ModoApp;

public class VentanaMostrarNotificacionAlarma {

    @FXML
    private AnchorPane parent;
    private Text textoNotificacion;
    private ModoApp.modo modoActual;

    public static void lanzarVentanaNotificacion(String nombreActividad, ModoApp.modo modoActual) {
        try {
            new VentanaMostrarNotificacionAlarma().start(nombreActividad, modoActual);
        } catch (Exception e5) {
            //
        }
    }

    public void start(String nombreActividad, ModoApp.modo modoActual) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaNotificacion.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("ALARMA");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.modoActual = modoActual;
        ModoApp.setModo(this.modoActual, this.parent);
        this.textoNotificacion.setText("Alarma de la actividad " + nombreActividad);
    }


}
