package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLOutput;
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
        String Output= String.format(
                """
                Student Info:
                Mtr-Nr: %s
                Name: %s %s
                Java-Skills: %s
                Firma: %s
                """,
                matrikelNumberField.getText(),
                firstNameField.getText(),
                lastNameField.getText(),
                javaSkillsComboBox.getValue(),
                companyField.getText()
        );
        System.out.println(Output);
        System.out.println("Adding a new student!!");

    }
}
