package org.example.VentanasAuxiliares;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class notificacionVentana {

    @FXML
    private Text textoNotificacion;

    public void start(String nombreActividad) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/escenaNotificacion.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle("ALARMA");
        s.setScene(scene);
        s.show();
        textoNotificacion.setText("Alarma de la actividad " + nombreActividad);
    }

    public static void lanzarVentanaNotificacion(String nombreActividad) {
        try {
            new notificacionVentana().start(nombreActividad);
        } catch (Exception e5) {
            //
        }
    }


}
