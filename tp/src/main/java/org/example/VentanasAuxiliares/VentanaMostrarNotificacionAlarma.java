package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VentanaMostrarNotificacionAlarma {

    @FXML
    private Text textoNotificacion;

    public static void lanzarVentanaNotificacion(String nombreActividad) {
        try {
            new VentanaMostrarNotificacionAlarma().start(nombreActividad);
        } catch (Exception e5) {
            //
        }
    }

    public void start(String nombreActividad) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaNotificacion.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("ALARMA");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        textoNotificacion.setText("Alarma de la actividad " + nombreActividad);
    }


}
