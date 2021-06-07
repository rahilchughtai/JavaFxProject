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

public class HomeController implements Initializable {
    @FXML
    private Button btnStudents;
    @FXML
    private Button btnCourses;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void switchToScene(String fxml, Window clickedWindow) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxml)));
        Scene scene = new Scene(root);
        Stage appStage = (Stage) clickedWindow;
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        Button clickSource= (Button) mouseEvent.getSource();
        Window clickedWindow = clickSource.getScene().getWindow();

        if(clickSource==btnCourses){
            System.out.println("Courses!");
            switchToScene("views/Courses.fxml", clickedWindow);
        }
        else if (clickSource==btnStudents){
            System.out.println("Students!!!");
            switchToScene("views/Students.fxml",clickedWindow);
        }

    }


}
