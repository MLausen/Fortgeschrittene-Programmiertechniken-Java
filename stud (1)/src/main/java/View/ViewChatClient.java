package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Team 10
 */
public class ViewChatClient extends GridPane {
    private TextArea inputField;
    private Label nameLabel;
    private TextField chatField;
    private Button sendButton;
    public String name;

    public ViewChatClient(String name){
        this.name = name;
        this.inputField = new TextArea("Gib eine Nachricht ein.");
        this.chatField = new TextField("Du hast den Chat betreten.");
        this.nameLabel = new Label(name + ":");
        this.sendButton = new Button("senden");

        this.createGrid();
    }

    public void setOnSendHandeler(){
        //sendButton.addEventHandler();
        throw new NotImplementedException();
    }

    private void createGrid(){
        this.setMinSize(200, 200);
        // col, row, colspan, rowspan
        this.add(chatField, 0, 0, 3, 3);
        this.add(inputField, 1, 3, 2, 1);
        this.add(sendButton, 4, 3, 1, 1);
        this.add(nameLabel, 0, 3, 1, 1);
    }

    public void addText(String message, String clientName){
        this.chatField.setText(this.chatField.getText() + "\n" + clientName + ": " + message);
    }
}
