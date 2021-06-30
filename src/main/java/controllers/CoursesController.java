package controllers;

import database.models.JavaSkillRating;
import database.models.Student;
import database.services.DummyDataService;
import database.services.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import model.Course;


import java.net.URL;
import java.util.ResourceBundle;



import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController extends SceneController {


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
        initCols();
        loadData();
    }

    private void initCols() {
        col_cid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        col_courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update"));
        editableCols();
    }

    private void editableCols() {
        col_cid.setCellFactory(TextFieldTableCell.forTableColumn());
        col_cid.setOnEditCommit(e->{
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setCid(e.getNewValue());
        });
        col_courseName.setCellFactory(TextFieldTableCell.forTableColumn());

        col_courseName.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCourse(e.getNewValue());
        });
        col_room.setCellFactory(TextFieldTableCell.forTableColumn());

        col_room.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setRoom(e.getNewValue());
        });
        table_info.setEditable(true);
    }

    private void loadData() {
        data_table = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            data_table.add(new Course(String.valueOf(i),
                    "Kursname" + i, "Nr." + i, new Button("Update")));
        }
        table_info.setItems(data_table);
    }


}
