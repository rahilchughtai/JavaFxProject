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
    private TextField text_newMatrikelNumber;

    @FXML
    private TextField text_newFirstName;

    @FXML
    private TextField text_newLastName;

    @FXML
    private TextField text_newCorporation;

    @FXML
    private ComboBox<String> combo_javaSkills;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentService = StudentService.getService();

        textFieldToNumberField(text_newMatrikelNumber);

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

                                setMatriculationNumber(text_newMatrikelNumber.getText());
                                setFirstName(text_newFirstName.getText());
                                setLastName(text_newLastName.getText());
                                setJavaSkillRating(javaSkillStringToEnum(combo_javaSkills.getValue()));
                                setCorporationName(text_newCorporation.getText());
                            }
                        });

            }

        });
    }
}
