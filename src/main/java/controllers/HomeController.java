package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    //Declaration of buttons

    @FXML
    private Button btnStudents;
    @FXML
    private Button btnCourses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Add event listeners for the buttons
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Something went wrong with loading the fxml!");
        }
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent mouseEvent) {
        Object clickSource=mouseEvent.getSource();

        if(clickSource==btnCourses){
            System.out.println("Courses!");
        }
        else if (clickSource==btnStudents){
            System.out.println("Students!!!");
        }
        /*
        if (mouseEvent.getSource() == btnDashboard) {
            loadStage("/home/fxml/Dashboard.fxml");
        } else if (mouseEvent.getSource() == btnStudents) {
            loadStage("/home/fxml/Students.fxml");
        } else if (mouseEvent.getSource() == btn_Timetable) {
            loadStage("/home/fxml/Timetable.fxml");
        }*/
    }


}
