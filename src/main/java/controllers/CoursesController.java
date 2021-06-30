package controllers;

import database.services.CourseService;
import database.services.ModelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Course;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursesController extends SceneController {

    private ModelService<database.models.Course> courseService;

    @FXML
    private TableView<Course> table_info;

    @FXML
    private TextField text_newCourseName;

    @FXML
    private TextField text_newRoomName;

    @FXML
    private void addNewCourse(ActionEvent actionEvent) {
        final var newCourse = new Course(text_newCourseName.getText(), text_newRoomName.getText());

        System.out.println(newCourse);
    }

    public static ObservableList<Course> data_table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        courseService = CourseService.getService();

        loadData();
    }

    private void loadData() {
        data_table = FXCollections.observableArrayList();

        try {

            data_table.addAll(courseService
                    .get()
                    .stream()
                    .map(x -> new Course(x.getName(), x.getRoom().getName()))
                    .toList());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        table_info.setItems(data_table);
    }
}
