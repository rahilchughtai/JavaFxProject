package dhbw;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class application extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello World! :)");
        primaryStage.setScene(new Scene(label,100,100));
        primaryStage.setTitle("Student Manager");
        primaryStage.show();

    }
}
