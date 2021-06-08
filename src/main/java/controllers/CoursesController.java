package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;
import java.io.IOException;


public class CoursesController extends SceneController {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnStudents;
    @FXML
    private void handleButtonClick(ActionEvent mouseEvent) throws IOException {
        Button clickSource= (Button) mouseEvent.getSource();
        Window clickedWindow = clickSource.getScene().getWindow();
        if(clickSource==btnHome){
            switchToScene(MyScene.HOME,clickedWindow);
        }
        else if (clickSource==btnStudents){
            switchToScene(MyScene.STUDENTS,clickedWindow);
        }
    }


}
