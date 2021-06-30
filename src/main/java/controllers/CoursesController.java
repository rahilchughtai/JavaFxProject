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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class CoursesController extends SceneController {

    private ModelService<database.models.Course> courseService;
    private ModelService<database.models.Room> roomService;

    private Collection<database.models.Room> possibleRooms;

    @FXML
    private ComboBox<String> combo_room;

    @FXML
    private TableView<Course> table_info;

    @FXML
    private TextField text_newCourseName;

    @FXML
    private TableColumn<Course, String> col_room;

    @FXML
    private void addNewCourse(ActionEvent actionEvent) {
        final var selectedRoomName = combo_room.getSelectionModel().getSelectedItem();
        final var newCourseName = text_newCourseName.getText();

        if (selectedRoomName == null || newCourseName.equals(""))
            return;

        final var selectedRoom = possibleRooms.stream().filter(x -> x.getName().equals(selectedRoomName)).findFirst().get();
        final var newCourse = new Course(newCourseName, selectedRoom.getId(), selectedRoom.getName());

        try {
            courseService.save(new ArrayList<>() {{ add(new database.models.Course(null, newCourse.getName(), new Room(newCourse.getRoomId(), newCourse.getRoomName()))); }});

            data_courses.add(newCourse);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ObservableList<Course> data_courses;
    public static ObservableList<String> data_roomNames;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseService = CourseService.getService();
        roomService = RoomService.getService();
        loadData();
        initializeColumns();
    }

    private void initializeColumns() {
        col_room.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), data_roomNames));
    }


    private void loadData() {
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
                    .map(x -> new Course(x.getName(), x.getRoom().getId(), x.getRoom().getName()))
                    .toList());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        table_info.setItems(data_courses);
    }

    @FXML
    private void saveCourses(ActionEvent actionEvent){

    }
}
