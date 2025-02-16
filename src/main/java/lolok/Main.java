package lolok;
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

    private final Lolok lolok = new Lolok("./data/lolok_data.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            //set up the title and icon
            stage.setTitle("Chatbot Lolok");
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/icon.png")));
            fxmlLoader.<MainWindow>getController().setLolok(lolok);
            stage.show();
            fxmlLoader.<MainWindow>getController().greetToUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

