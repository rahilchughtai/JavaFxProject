package controllers;

import com.sun.javafx.scene.control.IntegerField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class StudentsController extends SceneController{

    @FXML private TextField matrikelNumberField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField companyField;
    @FXML private TextField javaSkillsField;

    private int matrikelNumber;
    private String firstName;
    private String lastName;
    private String companyName;


    @FXML
    private void addNewStudent(ActionEvent actionEvent){

    }
}
