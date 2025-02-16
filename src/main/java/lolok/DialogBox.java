package lolok;
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class DialogBox extends HBox {
    @FXML private TextFlow dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set text and image
        Text textNode = new Text(text);
        dialog.getChildren().add(textNode);
        displayPicture.setImage(img);

        // Style configuration
        this.getStyleClass().add(isUser ? "user-message" : "bot-message");
        HBox.setHgrow(dialog, Priority.ALWAYS);

        // Circular avatar
        Circle clip = new Circle(20, 20, 20);
        displayPicture.setClip(clip);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.setTextAlignment(TextAlignment.LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    public static DialogBox getLolokDialog(String text, Image img, boolean isError) {
        var db = new DialogBox(text, img, false);
        db.flip();
        if (isError) {
            db.getStyleClass().add("error-message");
        }
        return db;
    }
}