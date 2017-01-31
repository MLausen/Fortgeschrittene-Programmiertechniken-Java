package ChatComponents;

import Helper.ErrorDialog;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Team 10
 */
public class ChatClient extends UnicastRemoteObject implements ClientService{
    private static final long serialVersionUID = 1L;
    private ChatService server;
    private Integer id = -1;

    public ChatClient (Integer id, ChatService server) throws RemoteException, MalformedURLException{
        this.id = id;
        this.server = (ChatService) server;
    }


    // this is equal to receiving?!! <--check
    @Override
    public void send(String message) {
        System.out.println("Client " + id + " receives:");
        System.out.println(message);
    }

    /*
    public void receive(String message) {
        System.out.println("Client " + id + " receives:");
        System.out.println(message);
    }
    */

    @Override
    public String getName() {
        return ("Client" + this.id);
    }

    public Integer getId() {
        return id;
    }
    public ChatService getServer() {
        return server;
    }
}

class ClientView extends GridPane {
    private TextArea inputField;
    private Label nameLabel;
    private TextField chatField;
    private Button sendButton;
    public String name;

    ClientView(String name){
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
}

class ClientViewController{
    ClientView view;
    ChatClient model;

    ClientViewController(ClientView view, ClientService model){
        this.view = view;
        this.model = (ChatClient) model;

        //view.setOnSendHandeler();
    }

    private boolean onSendText(){
        throw new NotImplementedException();
    }
}