package controllers;

import database.models.JavaSkillRating;
import database.models.Student;
import database.services.ModelService;
import database.services.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;


public class StudentsController extends SceneController {

    private ModelService<Student> studentService;

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
        studentService = StudentService.getService();
        textFieldToNumberField(matrikelNumberField);

    }


    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating == null)
            return JavaSkillRating.NONE;
        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }

    @FXML
    private void addNewStudent(ActionEvent actionEvent) throws SQLException {
        Collection<Student> students;
        studentService.save(new ArrayList<Student>() {
            {
                add(
                        new Student() {
                            {
                                //setCorporation(new Corporation(null,"Test"));
                                setMatriculationNumber(matrikelNumberField.getText());
                                setFirstName(firstNameField.getText());
                                setLastName(lastNameField.getText());
                                setJavaSkillRating(javaSkillStringToEnum(javaSkillsComboBox.getValue()));
                            }
                        });

            }

        });
    }
}
