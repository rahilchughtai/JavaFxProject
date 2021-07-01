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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import models.JavaSkillRating;
import models.Student;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

public class StudentsController extends SceneController {

    private ObservableList<Student> data_students;
    private ObservableList<String> data_courses;
    private ObservableList<JavaSkillRating> data_javaSkills;

    @FXML
    private TableColumn<Student,String> col_corporationName;
    @FXML
    private TableColumn<Student,String> col_lastName;
    @FXML
    private TableColumn<Student,String> col_firstName;
    @FXML
    private TableColumn<Student,String> col_courseName;
    @FXML
    private TableColumn<Student,String> col_matriculationNumber;

    private ModelService<database.models.Course> courseService;
    private ModelService<database.models.Student> studentService;
    private Collection<database.models.Course> possibleCourses;

    @FXML
    public TableColumn<Student,JavaSkillRating> col_javaSkill;

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

    private void editRowForMatriculationNumber(TableColumn.CellEditEvent<models.Student, String> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedStudent = new Student(
                rowValue.getId(),
                cellEditEvent.getNewValue(),
                rowValue.getFirstName(),
                rowValue.getLastName(),
                rowValue.getCorporationName(),
                rowValue.getCourseId(),
                rowValue.getCourseName(),
                rowValue.getJavaSkill()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }
    private void editRowForFirstName(TableColumn.CellEditEvent<models.Student, String> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedStudent = new Student(
                rowValue.getId(),
                rowValue.getMatriculationNumber(),
                cellEditEvent.getNewValue(),
                rowValue.getLastName(),
                rowValue.getCorporationName(),
                rowValue.getCourseId(),
                rowValue.getCourseName(),
                rowValue.getJavaSkill()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }

    private void editRowForLastName(TableColumn.CellEditEvent<models.Student, String> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedStudent = new Student(
                rowValue.getId(),
                rowValue.getMatriculationNumber(),
                rowValue.getFirstName(),
                cellEditEvent.getNewValue(),
                rowValue.getCorporationName(),
                rowValue.getCourseId(),
                rowValue.getCourseName(),
                rowValue.getJavaSkill()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }

    private void editRowForCorporationName(TableColumn.CellEditEvent<models.Student, String> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedStudent = new Student(
                rowValue.getId(),
                rowValue.getMatriculationNumber(),
                rowValue.getFirstName(),
                rowValue.getLastName(),
                cellEditEvent.getNewValue(),
                rowValue.getCourseId(),
                rowValue.getCourseName(),
                rowValue.getJavaSkill()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }

    private void editRowForCourse(TableColumn.CellEditEvent<models.Student, String> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var selectedCourse = possibleCourses
                .stream()
                .filter(x -> x.getName().equals(cellEditEvent.getNewValue()))
                .findFirst()
                .get();

        final var updatedStudent = new Student(
                rowValue.getId(),
                rowValue.getMatriculationNumber(),
                rowValue.getFirstName(),
                rowValue.getLastName(),
                rowValue.getCorporationName(),
                selectedCourse.getId(),
                selectedCourse.getName(),
                rowValue.getJavaSkill()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }

    private void editRowForJavaSkill(TableColumn.CellEditEvent<models.Student, JavaSkillRating> cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_students.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedStudent = new Student(
                rowValue.getId(),
                rowValue.getMatriculationNumber(),
                rowValue.getFirstName(),
                rowValue.getLastName(),
                rowValue.getCorporationName(),
                rowValue.getCourseId(),
                rowValue.getCourseName(),
                cellEditEvent.getNewValue()
        );

        data_students.set(indexOfRowValue, updatedStudent);

    }

    private void initializeColumns() {

        col_javaSkill.setCellFactory(ComboBoxTableCell.forTableColumn(new JavaSkillRatingConverter(), data_javaSkills));
        col_courseName.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), data_courses));
        col_matriculationNumber.setCellFactory(TextFieldTableCell.forTableColumn(new RestrictToOnlyNumbersStringConverter()));

        col_matriculationNumber.setOnEditCommit(cellEditEvent -> editRowForMatriculationNumber(cellEditEvent));
        col_courseName.setOnEditCommit(cellEditEvent -> editRowForCourse(cellEditEvent));
        col_corporationName.setOnEditCommit(cellEditEvent -> editRowForCorporationName(cellEditEvent));
        col_firstName.setOnEditCommit(cellEditEvent -> editRowForFirstName(cellEditEvent));
        col_lastName.setOnEditCommit(cellEditEvent -> editRowForLastName(cellEditEvent));
        col_javaSkill.setOnEditCommit(cellEditEvent -> editRowForJavaSkill(cellEditEvent));

    }

    private JavaSkillRating javaSkillStringToEnum(String skillRating) {
        if (skillRating == null)
            return JavaSkillRating.NONE;

        return JavaSkillRating.valueOf(skillRating.toUpperCase());
    }


    private void loadCourses() throws SQLException {
        data_courses = FXCollections.observableArrayList();

        possibleCourses = courseService.get();

        data_courses.addAll(possibleCourses
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
        return possibleCourses
                .stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .orElse(null);
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

            showError("Unnvollständige Eingabewerte.");
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
