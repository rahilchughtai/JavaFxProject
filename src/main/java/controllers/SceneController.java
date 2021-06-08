package controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    enum MyScene {
        HOME,
        COURSES,
        STUDENTS
    }
}
