package controllers;

import database.models.Course;
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
import models.JavaSkillRating;
import models.Student;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentsController extends SceneController {

    private ObservableList<Student> data_students;
    private ObservableList<JavaSkillRating> data_javaSkills;

    @FXML
    private TableColumn<Student,String> col_corporationName;
    @FXML
    private TableColumn<Student,String> col_lastName;
    @FXML
    private TableColumn<Student,String> col_firstName;
    @FXML
    private TableColumn<Student,String> col_course;
    @FXML
    private TableColumn<Student,String> col_matriculationNumber;

    private ModelService<database.models.Course> courseService;
    private ModelService<database.models.Student> studentService;
    private Collection<database.models.Course> possibleCourses;

    @FXML
    public TableColumn<Student,JavaSkillRating> col_javaSkills;

    @FXML
    private TableView<Student> table_Students;

    @FXML
    private ComboBox<String> combo_course;

    @FXML
    private TextField text_newMatriculationNumber;
    @FXML
    private TextField text_firstName;
    @FXML
    private TextField text_lastName;
    @FXML
    private TextField text_corporationName;
    @FXML
    private ComboBox<String> combo_javaSkills;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        courseService = CourseService.getService();
        studentService = StudentService.getService();

        textFieldToNumberField(text_newMatriculationNumber);

        super.initialize(url, resources);

        initializeJavaSkills();
        initializeColumns();
    }

    private void initializeJavaSkills() {
        data_javaSkills = FXCollections.observableArrayList();

        data_javaSkills.add(JavaSkillRating.NONE);
        data_javaSkills.add(JavaSkillRating.BEGINNER);
        data_javaSkills.add(JavaSkillRating.INTERMEDIATE);
        data_javaSkills.add(JavaSkillRating.ADVANCED);
        data_javaSkills.add(JavaSkillRating.SEBASTIAN);
    }

    private void initializeColumns() {

        col_javaSkills.setCellFactory(ComboBoxTableCell.forTableColumn(new JavaSkillRatingConverter(), data_javaSkills));

          // col_room.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), data_roomNames));
          //  col_room.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, true));
           // col_courseName.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, false));
    }

    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating == null)
            return JavaSkillRating.NONE;

        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }


    private void loadCourses() throws SQLException {
        ObservableList<String> data_courses = FXCollections.observableArrayList();

        data_courses.addAll(courseService.get()
                .stream()
                .map(Course::getName)
                .toList());
        combo_course.setItems(data_courses);
    }

    private void loadStudents() throws SQLException {
        data_students.addAll(
                studentService.get()
                        .stream()
                        .map(x -> new Student(
                                x.getId(),
                                x.getMatriculationNumber(),
                                x.getFirstName(),
                                x.getLastName(),
                                x.getCorporationName(),
                                x.getCourse().getId(),
                                x.getCourse().getName(),
                                JavaSkillRating.valueOf(x.getJavaSkillRating().getValue()))
                        )
                        .toList()
        );

        table_Students.setItems(data_students);
    }


    @Override
    protected void loadData() {
        data_students = FXCollections.observableArrayList();
        try {
            loadCourses();
            loadStudents();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private Course findCourse(String courseName) throws SQLException {
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
            data_students.remove(selectedStudent);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Der Student konnte nicht gelöscht werden");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    void editRow(TableColumn.CellEditEvent<models.Student, String > cellEditEvent, boolean roomChanged) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);
        if (indexOfRowValue == -1)
            return;
        models.Student updatedStudent;
    }

    @FXML
    private void saveStudents(ActionEvent actionEvent) {
        try {
            final var changedStudents = data_students
                    .stream()
                    .map(x -> new database.models.Student(
                            x.getId(),
                            x.getMatriculationNumber(),
                            x.getFirstName(),
                            x.getLastName(),
                            database.models.JavaSkillRating.valueOf(x.getJavaSkill().getValue()),
                            x.getCorporationName(),
                            new Course(x.getCourseId(), x.getCourseName(), null)))
                    .toList();

            studentService.save(changedStudents);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Einträge können wegen Duplikaten nicht gespeichert werden!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @FXML
    private void addNewStudent(ActionEvent actionEvent) throws SQLException {

        final var course = findCourse(combo_course.getValue());
        final var matriculationNumber = text_newMatriculationNumber.getText();
        final var firstName = text_firstName.getText();
        final var lastName = text_lastName.getText();
        final var javaSkill = javaSkillStringToEnum(combo_javaSkills.getValue());
        final var corporationName = text_corporationName.getText();

        if (course == null
                || matriculationNumber.isEmpty()
                || firstName.isEmpty()
                || lastName.isEmpty()
                || corporationName.isEmpty()) {

            showError("Eingabewerte fehlen!");

            return;
        }

        final var newStudent = new Student(
                null,
                matriculationNumber,
                firstName,
                lastName,
                corporationName,
                course.getId(),
                course.getName(),
                javaSkill);

        try {
            final var newDatabaseStudent = new database.models.Student()
            {{
                setCourse(course);
                setMatriculationNumber(matriculationNumber);
                setFirstName(firstName);
                setLastName(lastName);
                setJavaSkillRating(database.models.JavaSkillRating.valueOf(javaSkill.getValue()));
                setCorporationName(corporationName);
            }};

            studentService.save(newDatabaseStudent);

            newStudent.setId(newDatabaseStudent.getId());

            data_students.add(newStudent);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Dieser Eintrag kann wegen Duplikaten nicht eingefügt werden!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
