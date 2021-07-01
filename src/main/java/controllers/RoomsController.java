package controllers;

import database.services.ModelService;
import database.services.RoomService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.converter.DefaultStringConverter;
import models.Course;
import models.Room;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RoomsController extends SceneController {

    private ModelService<database.models.Room> roomService;

    private ObservableList<Room> data_rooms;

    @FXML
    private TableColumn<Room, String> col_roomName;

    @FXML
    private TextField text_newRoomName;

    @FXML
    private TableView<Room> table_rooms;

    @FXML
    private void addNewRoom(ActionEvent actionEvent) throws SQLException {
        final var newRoomName = text_newRoomName.getText();

        if (newRoomName.isEmpty()) {
            showError("Raumname fehlt!");
            return;
        }

        final var newRoom = new Room(null, newRoomName);

        try {
            final var newDatabaseRoom = new database.models.Room(null, newRoomName);

            roomService.save(newDatabaseRoom);

            newRoom.setId(newDatabaseRoom.getId());

            data_rooms.add(newRoom);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Dieser Eintrag kann wegen Duplikaten nicht eingefügt werden!");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomService = RoomService.getService();

        super.initialize(url, resourceBundle);

        initializeColumns();
    }

    private void editRow(TableColumn.CellEditEvent<Room, String > cellEditEvent) {
        final var rowValue = cellEditEvent.getRowValue();

        final var indexOfRowValue = data_rooms.indexOf(rowValue);

        if (indexOfRowValue == -1)
            return;

        final var updatedRoom = new Room(rowValue.getId(), cellEditEvent.getNewValue());

        data_rooms.set(indexOfRowValue, updatedRoom);
    }

    private void initializeColumns() {
        col_roomName.setOnEditCommit(cellEditEvent -> editRow(cellEditEvent));
    }

    @Override
    protected void loadData() {
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
        try {
            final var changedRooms = data_rooms
                    .stream()
                    .map(x -> new database.models.Room(x.getId(), x.getName()))
                    .toList();

            roomService.save(changedRooms);
        } catch (JdbcSQLIntegrityConstraintViolationException jdbcSQLIntegrityConstraintViolationException) {
            showError("Einträge können wegen Duplikaten nicht gespeichert werden!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
