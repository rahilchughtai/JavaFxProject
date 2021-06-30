package controllers;

import database.models.JavaSkillRating;
import database.models.Student;
import database.services.DummyDataService;
import database.services.ModelService;
import database.services.RoomService;
import database.services.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Room;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RoomsController extends SceneController {

    private ModelService<database.models.Room> roomService;

    @FXML
    private TableView<Room> table_rooms;

    //@FXML
    //private TableColumn<Room, String> columnRoomName;

    public static ObservableList<Room> data_table;

    @FXML
    private void addNewRoom(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        roomService = RoomService.getService();

        loadData();
    }

    private void loadData(){
        data_table = FXCollections.observableArrayList();

        try {
            final var rooms = roomService
                    .get()
                    .stream()
                    .map(room -> new Room(room.getId(), room.getName()))
                    .toList();
            data_table.addAll(rooms);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        table_rooms.setItems(data_table);
    }
}
