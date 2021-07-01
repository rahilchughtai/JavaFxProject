package controllers;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SceneController {

    public Label label_homeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_homeText.setText("Wilkommen zum DHBW Studenten Management System. Das Tool dient zur leichten Verwaltung der DHBW Datenbank. \r\n\r\n Um Einträge zu löschen können Sie die Zeile auswählen und den Löschen Button drücken. \r\n\r\n Zum Bearbeiten über Doppelclick das Feld auswählen, den Inhalt anpassen und ENTER drücken. Damit die Änderungen übernommen werden, anschließend den 'Speichern' Button drücken.");
        super.initialize(location, resources);
    }

}
