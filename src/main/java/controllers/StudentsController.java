package controllers;

import database.models.Course;
import database.models.JavaSkillRating;
import database.services.CourseService;
import database.services.ModelService;
import database.services.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.DefaultStringConverter;
import models.Student;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;


public class StudentsController extends SceneController {

    public static ObservableList<Student> data_table;
    private ModelService<database.models.Course> courseService;
    private ModelService<database.models.Student> studentService;
    private Collection<database.models.Course> possibleCourses;

    public TableColumn<Student,String> col_javaSkills;

    @FXML
    private TableView<Student> table_Students;

    @FXML
    private ComboBox<String> combo_course;

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
    public void initialize(URL url, ResourceBundle resources) {
        courseService = CourseService.getService();
        studentService = StudentService.getService();
        textFieldToNumberField(text_newMatrikelNumber);
        super.initialize(url, resources);
        initializeColumns();

    }

    private void initializeColumns() {
     //  col_javaSkills.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), "NONE"));
      // col_room.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), data_roomNames));
      //  col_room.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, true));
       // col_courseName.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, false));
    }

    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating == null)
            return JavaSkillRating.NONE;
        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }


    private void insertCourses() throws SQLException {
        ObservableList<String> data_courses = FXCollections.observableArrayList();

        data_courses.addAll(courseService.get()
                .stream()
                .map(Course::getName)
                .toList());
        combo_course.setItems(data_courses);
    }

    private void insertStudents() throws SQLException {
        data_table.addAll(
                studentService.get().stream().map(
                        s -> new Student(
                                s.getId(),
                                s.getMatriculationNumber(),
                                s.getFirstName(),
                                s.getLastName(),
                                s.getCorporationName(),
                                s.getJavaSkillRating(),
                                s.getCourse()
                        )
                ).toList()
        );
        table_Students.setItems(data_table);
    }


    @Override
    protected void loadData() {
        data_table = FXCollections.observableArrayList();
        try {
            insertCourses();
            insertStudents();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    Course findCourse(String courseName) throws SQLException {
        possibleCourses = courseService.get();
        Optional<Course> foundCourse = possibleCourses.stream().filter(course -> course.getName().
                equals(courseName)).findFirst();
        return foundCourse.orElse(null);
    }

    @FXML
    private void deleteSelectedStudent(final ActionEvent actionEvent) {
        final var selectedStudent = table_Students.getSelectionModel().getSelectedItem();
        if (selectedStudent == null)
            return;
        try {
            studentService.delete(selectedStudent.getId());
            data_table.remove(selectedStudent);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Der Student konnte nicht gelöscht werden");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    void editRow(TableColumn.CellEditEvent<models.Student, String > cellEditEvent, boolean roomChanged) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_table.indexOf(rowValue);
        if (indexOfRowValue == -1)
            return;
        models.Student updatedStudent;
    }


    @FXML
    private void saveStudents(ActionEvent actionEvent) {
        try {
            final var changeStudents = data_table
                    .stream()
                    .map(x -> new database.models.Student(x.getId(), x.getMatri_Id(), x.getFirstName(), x.getLastName(), x.getJavaSkill(), x.getCorporation(), x.getCourse())).toList();

            studentService.save(changeStudents);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Einträge können wegen Duplikaten nicht gespeichert werden!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @FXML
    private void addNewStudent(ActionEvent actionEvent) throws SQLException {
        Collection<database.models.Student> students;
        Course course = findCourse(combo_course.getValue());
        studentService.save(new ArrayList<database.models.Student>() {
            {
                add(
                        new database.models.Student() {
                            {
                                setCourse(course);
                                setMatriculationNumber(text_newMatrikelNumber.getText());
                                setFirstName(text_newFirstName.getText());
                                setLastName(text_newLastName.getText());
                                setJavaSkillRating(javaSkillStringToEnum(combo_javaSkills.getValue()));
                                setCorporationName(text_newCorporation.getText());
                            }
                        });

            }

        });
        loadData();
    }

}
