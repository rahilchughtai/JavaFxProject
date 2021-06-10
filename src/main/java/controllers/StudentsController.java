package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentsController extends SceneController{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private Button btnCourses;
    @FXML
    private Button btnHome;

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        Button clickSource= (Button) mouseEvent.getSource();
        Window clickedWindow = clickSource.getScene().getWindow();
        if(clickSource==btnCourses){
            switchToScene(MyScene.COURSES,clickedWindow);
        }
        else if (clickSource==btnHome){
            switchToScene(MyScene.HOME,clickedWindow);
        }

    }
}
