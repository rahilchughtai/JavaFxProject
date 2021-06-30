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
import javafx.scene.control.cell.PropertyValueFactory;
import models.Course;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursesController extends SceneController {

    private ModelService<database.models.Course> courseService;

    @FXML
    private TableView<Course> table_info;

    public static TableView<Course> table_info_2;

    @FXML
    private TableColumn<Course, String> col_cid;

    @FXML
    private TableColumn<Course, String> col_courseName;

    @FXML
    private TableColumn<Course, String> col_room;

    @FXML
    private TableColumn<Course, Button> col_update;

    @FXML
    private void addNewCourse(ActionEvent actionEvent) {

    }

    public static ObservableList<Course> data_table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_info_2 = table_info;

        courseService = CourseService.getService();

        initCols();
        loadData();
    }

    private void initCols() {
        col_courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_room.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        editableCols();
    }

    private void editableCols() {
//        col_cid.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_cid.setOnEditCommit(e->{
//                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setCid(e.getNewValue());
//        });
//        col_courseName.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        col_courseName.setOnEditCommit(e->{
//            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCourse(e.getNewValue());
//        });
//        col_room.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        col_room.setOnEditCommit(e->{
//            e.getTableView().getItems().get(e.getTablePosition().getRow()).setRoom(e.getNewValue());
//        });
//        table_info.setEditable(true);
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
