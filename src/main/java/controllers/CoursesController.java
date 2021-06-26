package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Course;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursesController extends SceneController implements Initializable  {

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

    public static ObservableList<Course> data_table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_info_2 = table_info;
        initTable();
        loadData();
    }

    private void initTable() {
        initCols();
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

        for (int i = 0; i < 10; i++) {
            data_table.add(new Course(String.valueOf(i),
                    "courseName " + i, "room " + i, new Button("update")));
        }

        table_info.setItems(data_table);

    }
}