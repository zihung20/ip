package loklok;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private LokLok lokLok;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image lokLokImage = new Image(this.getClass().getResourceAsStream("/images/loklok.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the LokLok instance */
    public void setLolok(LokLok l) {
        lokLok = l;
    }

    /**
     * Prints an initializing message when starting the app, such as loading app data.
     *
     * @param message The message to be printed.
     */
    public void printInitializeMessage(String message) {
        dialogContainer.getChildren().add(
                DialogBox.getLolokDialog(message, lokLokImage, false)
        );
    }
    /**
     * Creates a greeting message for the user when they start the app.
     */
    public void greetToUser() {
        dialogContainer.getChildren().add(
                DialogBox.getLolokDialog(lokLok.greet("LokLok"), lokLokImage, false)
        );
    }
    /**
     * Creates two dialog boxes, one echoing user input and the other containing LokLok's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = lokLok.getResponse(input).trim();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLolokDialog(response, lokLokImage, response.contains("Exception"))
        );
        userInput.clear();
    }
}
