package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController extends SceneController {
    @FXML
    private Button btnStudents;
    @FXML
    private Button btnCourses;

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        Button clickSource= (Button) mouseEvent.getSource();
        Window clickedWindow = clickSource.getScene().getWindow();

        if(clickSource==btnCourses){
            switchToScene(MyScene.COURSES,clickedWindow);
        }
        else if (clickSource==btnStudents){
            switchToScene(MyScene.STUDENTS,clickedWindow);
        }
    }


}
