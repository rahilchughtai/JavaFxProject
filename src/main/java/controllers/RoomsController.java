package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.h2.mode.FunctionsMySQL;

import java.io.IOException;

public class RoomsController extends SceneController {

    @FXML
    private ChoiceBox<String> choiceBoxView;

    @FXML
    void btnSwitchView(ActionEvent actionEvent) throws IOException{
        if(choiceBoxView.getValue()==null){
            //Error handling? Alert?
            return;
        }
        FxmlPathToScene("views/"+choiceBoxView.getValue()+".fxml", eventToWindow(actionEvent) );
    }

    //Make Header seperate and load options

}
