package controllers;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SceneController {

    public Label label_homeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_homeText.setText("Willkommen zum DHBW Studenten Management System. Das Tool dient zur leichten Verwaltung der DHBW Datenbank.\r\n\r\nDas Hinzufügen von Einträgen erfolgt durch das Ausfüllen der benötigten Eingaben und einem Klick auf 'Hinzufügen'. \r\n\r\nMit einem Doppelklick auf einen Eintrag wird die Bearbeitung möglich. Möchte man die Änderungen dann sichern, so drückt man ENTER und klickt anschließend 'Speichern'. \r\n\r\nEin Eintrag wird gelöscht, indem man ihn auswählt und anschließend den Button 'Löschen' anklickt.");
        super.initialize(location, resources);
    }

}
