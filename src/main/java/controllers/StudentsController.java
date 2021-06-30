package controllers;

import database.models.JavaSkillRating;
import database.models.Student;
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

    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating==null)
            return JavaSkillRating.NONE;
        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }

    @FXML
    private void addNewStudent(ActionEvent actionEvent) {

        System.out.println(javaSkillStringToEnum(javaSkillsComboBox.getValue()));
        // Refactor this and implement addNewStudentMethod, use Student Model
        String Output = String.format(
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
        Student student = new Student() {
            {
                setMatriculationNumber("9000");
                setFirstName("Peter");
                setLastName("Parker");
                setJavaSkillRating(JavaSkillRating.ADVANCED);

            }
        };

    }
}
