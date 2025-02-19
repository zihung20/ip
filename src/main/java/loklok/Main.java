package loklok;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main logic for GUI and window
 */
public class Main extends Application {

    private final LokLok lokLok = new LokLok("./data/LokLok_data.txt");
    private final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

    @Override
    public void start(Stage stage) {
        initialize(stage);
        fxmlLoader.<MainWindow>getController().setLolok(lokLok);
        stage.show();
        fxmlLoader.<MainWindow>getController().greetToUser();
    }
    private void initialize(Stage stage) {
        try {
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Chatbot LokLok");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/icon.png")));
        } catch (IOException e) {
            fxmlLoader.<MainWindow>getController().printInitializeMessage(e.getMessage());
        }
    }
}

