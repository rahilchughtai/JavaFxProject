package controllers;

import javafx.event.ActionEvent;
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

public class SceneController implements Initializable {
    enum MyScene {
        HOME,
        COURSES,
        STUDENTS
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private Window eventToWindow(ActionEvent mouseEvent){
        return ((Button) mouseEvent.getSource()).getScene().getWindow();
    }

    void FxmlPathToScene(String fxml, Window clickedWindow) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxml)));
        Scene scene = new Scene(root);
        Stage appStage = (Stage) clickedWindow;
        appStage.setScene(scene);
        appStage.show();
    }

    void switchToScene(MyScene scene, Window clickedWindow) throws IOException {
        String path = "views/";
        if (scene == MyScene.HOME) {
            path += ("Home.fxml");
        } else if (scene == MyScene.COURSES) {
            path += ("Courses.fxml");
        } else if (scene == MyScene.STUDENTS) {
            path += "Students.fxml";
        } else {
           return;
        }
        FxmlPathToScene(path, clickedWindow);
    }

    @FXML
    private void btnSwitchToCourse(javafx.event.ActionEvent mouseEvent) throws IOException {
        switchToScene(MyScene.COURSES,this.eventToWindow(mouseEvent));
    }
    @FXML
    private void btnSwitchToHome(javafx.event.ActionEvent mouseEvent) throws IOException {
        switchToScene(MyScene.HOME,eventToWindow(mouseEvent));
    }
    @FXML
    private void btnSwitchToStudents(javafx.event.ActionEvent mouseEvent) throws IOException {
        switchToScene(MyScene.STUDENTS,eventToWindow(mouseEvent));
    }

}
