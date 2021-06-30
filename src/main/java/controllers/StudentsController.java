package controllers;

import database.models.JavaSkillRating;
import database.services.ModelService;
import database.services.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Student;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;


public class StudentsController extends SceneController {

    public static ObservableList<Student> data_table;
    public TableView<Student> table_Students;
    private ModelService<database.models.Student> studentService;
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
        loadData();

    }

    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating == null)
            return JavaSkillRating.NONE;
        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }

    private void loadData() {
        data_table = FXCollections.observableArrayList();
        try {
            data_table.addAll(
                    studentService.get().stream().map(
                            s -> new Student(
                                    s.getMatriculationNumber(),
                                    s.getFirstName(),
                                    s.getLastName(),
                                    s.getCorporationName(),
                                    s.getJavaSkillRating()
                            )
                    ).toList()
            );

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        table_Students.setItems(data_table);
    }

    @FXML
    private void addNewStudent(ActionEvent actionEvent) throws SQLException {
        Collection<database.models.Student> students;
        studentService.save(new ArrayList<database.models.Student>() {
            {
                add(
                        new database.models.Student() {
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

    @FXML
    private void saveStudents(ActionEvent actionEvent){

    }
}
