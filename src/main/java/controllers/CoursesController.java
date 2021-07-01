package controllers;

import database.models.Room;
import database.services.CourseService;
import database.services.ModelService;
import database.services.RoomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.DefaultStringConverter;
import models.Course;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

public class CoursesController extends SceneController {

    private ModelService<database.models.Course> courseService;
    private ModelService<database.models.Room> roomService;

    private Collection<database.models.Room> possibleRooms;

    private ObservableList<Course> data_courses;
    private ObservableList<String> data_roomNames;

    @FXML
    private ComboBox<String> combo_room;

    @FXML
    private TableView<Course> table_courses;

    @FXML
    private TextField text_newCourseName;

    @FXML
    private TableColumn<Course, String> col_room;

    @FXML
    private TableColumn<Course, String> col_courseName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseService = CourseService.getService();
        roomService = RoomService.getService();
        super.initialize(url, resourceBundle);
        initializeColumns();
    }


    @FXML
    private void addNewCourse(ActionEvent actionEvent) {
        final var selectedRoomName = combo_room.getSelectionModel().getSelectedItem();
        final var newCourseName = text_newCourseName.getText();

        if (selectedRoomName == null || newCourseName.isEmpty()) {
            showError("Raumname oder Kursename fehlt!");
            return;
        }

        final var selectedRoom = possibleRooms.stream().filter(x -> x.getName().equals(selectedRoomName)).findFirst().get();
        final var newCourse = new Course(null, newCourseName, selectedRoom.getId(), selectedRoom.getName());

        try {
            final var newDatabaseCourse = new database.models.Course(null, newCourse.getName(), new Room(newCourse.getRoomId(), newCourse.getRoomName()));

            courseService.save(newDatabaseCourse);

            newCourse.setId(newDatabaseCourse.getId());

            data_courses.add(newCourse);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Dieser Eintrag kann wegen Duplikaten nicht eingefügt werden!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedCourse(final ActionEvent actionEvent) {
        final var selectedCourse = table_courses.getSelectionModel().getSelectedItem();

        if (selectedCourse == null)
            return;

        try {
            courseService.delete(selectedCourse.getId());

            data_courses.remove(selectedCourse);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Dieser Kurs wird verwendet und kann daher nicht gelöscht werden!");
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @FXML
    private void saveCourses(ActionEvent actionEvent){
        try {
            final var changedCourses = data_courses
                    .stream()
                    .map(x -> new database.models.Course(x.getId(), x.getName(), new Room(x.getRoomId(), x.getRoomName())))
                    .toList();

            courseService.save(changedCourses);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Einträge können wegen Duplikaten nicht gespeichert werden!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void editRow(TableColumn.CellEditEvent<Course, String > cellEditEvent, boolean roomChanged) {
        final var rowValue = cellEditEvent.getRowValue();
        final var indexOfRowValue = data_courses.indexOf(rowValue);
        if (indexOfRowValue == -1)
            return;
        Course updatedCourse;
        if (roomChanged) {
            final var selectedRoom = possibleRooms.stream().filter(x -> x.getName().equals(cellEditEvent.getNewValue())).findFirst().get();
            updatedCourse = new Course(rowValue.getId(), rowValue.getName(), selectedRoom.getId(), selectedRoom.getName());
        } else {
            updatedCourse = new Course(rowValue.getId(), cellEditEvent.getNewValue(), rowValue.getRoomId(), rowValue.getRoomName());
        }
        data_courses.set(indexOfRowValue, updatedCourse);
    }

    private void initializeColumns() {
        col_room.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), data_roomNames));
        col_room.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, true));
        col_courseName.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent, false));
    }

    @Override
    protected void loadData() {
        data_courses = FXCollections.observableArrayList();
        data_roomNames = FXCollections.observableArrayList();
        try {
            possibleRooms = roomService.get();
            data_roomNames.addAll(possibleRooms
                .stream()
                .map(Room::getName)
                .toList());
            combo_room.setItems(data_roomNames);
            data_courses.addAll(courseService
                    .get()
                    .stream()
                    .map(x -> new Course(x.getId(), x.getName(), x.getRoom().getId(), x.getRoom().getName()))
                    .toList());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        table_courses.setItems(data_courses);
    }
}
