package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;


public class StudentsController extends SceneController {

    @FXML
    private TextField matrikelNumberField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField companyField;

    @FXML
    private ComboBox<String> javaSkillsComboBox;

    /*
    private int matrikelNumber;
    private String firstName;
    private String lastName;
    private String companyName;
*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldToNumberField(matrikelNumberField);
    }


    @FXML
    private void addNewStudent(ActionEvent actionEvent) {
        // Refactor this and implement addNewStudentMethod, use Student Model
        System.out.println(firstNameField.getText());
        System.out.println(lastNameField.getText());
        System.out.println(javaSkillsComboBox.getValue());

        System.out.println("Adding a new student!!");

    }
}
