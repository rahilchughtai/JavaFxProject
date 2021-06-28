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
import model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentsController extends SceneController implements Initializable {

    @FXML
    private TableView<Student> tableUser;

    @FXML
    private TableColumn<Student, String> columnId;

    @FXML
    private TableColumn<Student, String> columnVorname;

    @FXML
    private TableColumn<Student, String> columNachname;

    @FXML
    private TableColumn<Student, String> columnFirma;

    @FXML
    private TableColumn<Student, String> columnJavaSkills;

    /*@FXML
    private TableColumn<Student, Button> col_update;*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();

    }

    private void initTable() {
        initCols();
    }

    private void initCols() {

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnVorname.setCellValueFactory(new PropertyValueFactory<>("name"));
        columNachname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnFirma.setCellValueFactory(new PropertyValueFactory<>("company"));
        columnJavaSkills.setCellValueFactory(new PropertyValueFactory<>("skill"));
        //col_update.setCellValueFactory(new PropertyValueFactory<>("update"));

        editableCols();
    }
    private void editableCols() {
        columnId.setCellFactory(TextFieldTableCell.forTableColumn());

        columnId.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });


        columnVorname.setCellFactory(TextFieldTableCell.forTableColumn());

        columnVorname.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });


        columNachname.setCellFactory(TextFieldTableCell.forTableColumn());

        columNachname.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setLastname(e.getNewValue());
        });

        columnFirma.setCellFactory(TextFieldTableCell.forTableColumn());

        columnFirma.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCompany(e.getNewValue());
        });

        columnJavaSkills.setCellFactory(TextFieldTableCell.forTableColumn());

        columnJavaSkills.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSkill(e.getNewValue());
        });

        tableUser.setEditable(true);
    }

    private void loadData() {
        ObservableList<Student> data_table = FXCollections.observableArrayList();

        for (int i = 0; i < 10; i++) {
            data_table.add(new Student(String.valueOf(i),
                    "name " + i, "lastname " + i,"company " + i, "skill " + i, new Button("update")));
        }

        tableUser.setItems(data_table);

    }
}
