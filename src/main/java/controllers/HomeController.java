package controllers;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SceneController {

    public Label label_homeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_homeText.setText("Wilkommen zum DHBW Studenten Management System. Das Tool dient zur leichten Verwaltung der DHBW Datenbank. Um Einträge zu löschen können sie die Zeile auswählen und den Löschen Button drücken. Zum Bearbeiten über Doppelclick das Feld auswählen, den Inhalt anpassen und den Speichern Button clicken.");
        super.initialize(location, resources);
    }
}
