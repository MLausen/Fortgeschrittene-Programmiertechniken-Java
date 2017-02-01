package View;

import Helper.ErrorDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.event.ActionListener;

/**
 * Created by Team 10
 */
public class ViewChatClient extends GridPane {
    private TextArea chatField;
    private Label nameLabel;
    private TextField inputField;
    private Button sendButton;
    public String name;

    public ViewChatClient(String name){
        this.name = name;
        this.inputField = new TextField();
        this.chatField = new TextArea("Du hast den Chat betreten.");
        this.nameLabel = new Label(name + ":");
        this.sendButton = new Button("senden");

        this.createGrid();
    }

    private void createGrid(){
        this.setMinSize(200, 200);
        // col, row, colspan, rowspan
        this.add(chatField, 0, 0, 8, 4);
        this.add(nameLabel, 0, 4, 1, 1);
        this.add(inputField, 1, 4, 3, 1);
        this.add(sendButton, 5, 4, 1, 1);

        chatField.setEditable(false);
        inputField.setPromptText("Gib eine Nachricht ein");
    }

    // auslagern in controller mit reflection?

    public String getMessage(){
        return this.inputField.getText();
    }

    public void resetInputField(){
        this.inputField.setText("");
    }

    public TextArea getChatArea(){
        return this.chatField;
    }

    public void setOnSendHandeler(EventHandler<ActionEvent> eventHandler){
        sendButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void setKeyPressedHandler(Node node) {
        node.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                sendButton.fire();
                ev.consume();
            }
        });
    }
}
