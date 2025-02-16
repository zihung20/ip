package lolok;

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

    private Lolok lolok;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image lolokImage = new Image(this.getClass().getResourceAsStream("/images/lolok.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Lolok instance */
    public void setLolok(Lolok l) {
        lolok = l;
    }
    public void greetToUser() {
        dialogContainer.getChildren().add(
                DialogBox.getLolokDialog(lolok.greet("Lolok"), lolokImage, false)
        );
    }
    /**
     * Creates two dialog boxes, one echoing user input and the other containing Lolok's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = lolok.getResponse(input).trim();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getLolokDialog(response, lolokImage, response.contains("Exception"))
        );
        userInput.clear();
    }
}
