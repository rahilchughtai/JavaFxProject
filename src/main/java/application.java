import database.connection.DatabaseConnectionManager;
import database.models.Room;
import database.services.RoomService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class application extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, IllegalStateException, SQLException {
        initializeDatabaseConnectionManager();

        initializeUI(stage);
    }

    private void initializeDatabaseConnectionManager() throws ClassNotFoundException, IllegalStateException, SQLException {
        DatabaseConnectionManager.initializeDatabase();
    }

    private void initializeUI(final Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Home.fxml")));
        Image icon = new Image(getClass().getResourceAsStream("/images/dhbwicon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("DHBW Studenten Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
