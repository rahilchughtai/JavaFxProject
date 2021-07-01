package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
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
        private MyScene(String path) {
            this.path = "views/" + path;
        }
    }

    private Alert errorAlert;

    private void initializeUIControls() {
        errorAlert = new Alert(Alert.AlertType.ERROR);
    }

    protected void loadData() {
    }

    protected void showError(String errorMessage) {
        errorAlert.setHeaderText(errorMessage);
        errorAlert.show();
    }

    /**
     * This method makes a textField into a numberfield
     * might need refactoring in the future !!!
     *
     * @param textField
     */
    protected void textFieldToNumberField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUIControls();
        loadData();
    }

    /**
     * Takes Button Click event and returns the window
     */
    public Window eventToWindow(ActionEvent mouseEvent) {
        return ((Button) mouseEvent.getSource()).getScene().getWindow();
    }

    /**
     * This method switches the scene
     * based on given fxml path string
     *
     * @param fxml          Path to the fxml file
     * @param clickedWindow The window that was clicked
     */
    public void FxmlPathToScene(String fxml, Window clickedWindow) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(fxml)));
        Scene scene = new Scene(root);
        Stage appStage = (Stage) clickedWindow;

        appStage.setScene(scene);
        appStage.show();
        appStage.setHeight(appStage.getHeight());
        appStage.setWidth(appStage.getWidth());
    }

    private String buttonTextToPath(String buttonText) {
        return MyScene.valueOf(buttonText.toUpperCase()).path;
    }

    @FXML
    private void btnSwitchSceneClick(ActionEvent mouseEvent) throws IOException {
        String ButtonText = ((Button) mouseEvent.getSource()).getText();
        FxmlPathToScene(buttonTextToPath(ButtonText), eventToWindow(mouseEvent));
    }

}
