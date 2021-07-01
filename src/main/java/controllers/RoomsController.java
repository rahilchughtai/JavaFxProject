package controllers;

import database.services.ModelService;
import database.services.RoomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Room;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomsController extends SceneController {

    public static ObservableList<Room> data_rooms;
    public TextField text_roomName;
    private ModelService<database.models.Room> roomService;

    @FXML
    private TableView<Room> table_rooms;

    @FXML
    private void addNewRoom(ActionEvent actionEvent) throws SQLException {
        roomService.save(new database.models.Room(null, text_roomName.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomService = RoomService.getService();
        loadData();
    }

    private void loadData() {
        data_rooms = FXCollections.observableArrayList();
        try {
            final var rooms = roomService
                    .get()
                    .stream()
                    .map(room -> new Room(room.getId(), room.getName()))
                    .toList();
            data_rooms.addAll(rooms);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        table_rooms.setItems(data_rooms);
    }

    @FXML
    private void saveRooms(ActionEvent actionEvent) {

    }
}
