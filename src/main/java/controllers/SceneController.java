package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    private enum MyScene {
        HOME("Home.fxml"),
        COURSES("Courses.fxml"),
        STUDENTS("Students.fxml"),
        ROOMS("Rooms.fxml");
        public final String path;
        //This converts the enum to the file path
        private MyScene(String path){
            this.path="views/"+path;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * This method makes a textField into a numberfield
     * might need refactoring in the future !!!
     * @param textField
     */
    void textFieldToNumberField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    /**
     * Takes Button Click event and returns the window
     */
    public Window eventToWindow(ActionEvent mouseEvent){
        return ((Button) mouseEvent.getSource()).getScene().getWindow();
    }
    /**
     * This method switches the scene
     * based on given fxml path string
     *
     * @param fxml Path to the fxml file
     * @param clickedWindow The window that was clicked
     */
    public void  FxmlPathToScene(String fxml, Window clickedWindow) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxml)));

        Scene scene = new Scene(root);
        Stage appStage = (Stage) clickedWindow;
        Image icon = new Image(getClass().getResourceAsStream("/images/dhbwicon.png"));
        appStage.getIcons().add(icon);
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    private void btnSwitchToCourse(javafx.event.ActionEvent mouseEvent) throws IOException {
        FxmlPathToScene(MyScene.COURSES.path,eventToWindow(mouseEvent));
    }

    @FXML
    private void btnSwitchToRooms(javafx.event.ActionEvent mouseEvent) throws IOException {
        FxmlPathToScene(MyScene.ROOMS.path,eventToWindow(mouseEvent));
    }

    @FXML
    private void btnSwitchToHome(javafx.event.ActionEvent mouseEvent) throws IOException {
        FxmlPathToScene(MyScene.HOME.path,eventToWindow(mouseEvent));
    }
    @FXML
    private void btnSwitchToStudents(javafx.event.ActionEvent mouseEvent) throws IOException {
        FxmlPathToScene(MyScene.STUDENTS.path,eventToWindow(mouseEvent));
    }
}
