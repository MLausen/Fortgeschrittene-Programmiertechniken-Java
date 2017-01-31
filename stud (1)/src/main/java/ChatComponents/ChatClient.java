package ChatComponents;

import Helper.ErrorDialog;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Team 10
 */
public class ChatClient extends UnicastRemoteObject implements ClientService, Runnable{
    private static final long serialVersionUID = 1L;
    private ChatService server;
    private Integer id = -1;

    public ChatClient (Integer id, ChatService server) throws RemoteException, MalformedURLException{
        this.id = id;
        this.server = (ChatServer) server;// TODO

        this.server.login(this.getName());

        // TODO
        try {
            ClientViewController ctrl = new ClientViewController(new ClientView(this.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.println("Client with ID " + id + " joins the chat.");

        String message = "Hello, my name is " + getName();

        int i = 0;
        //while(true){
        do{
            try {
                server.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
                ErrorDialog.error("RemoteException");
            }
            i++;
        }while(i < 20);

        try {
            this.server.logout(this.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
            ErrorDialog.error("RemoteException");
        }
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
}

class ClientView extends GridPane {
    private Stage stage;
    private Scene scene;
    private TextArea inputField;
    private Label nameLabel;
    private TextField chatField;
    private Button sendButton;
    public String name;

    ClientView(String name){
        this.name = name;
        this.stage = new Stage();
        this.inputField = new TextArea("Gib eine Nachricht ein.");
        this.chatField = new TextField("Du hast den Chat betreten.");
        this.nameLabel = new Label(name + ":");
        this.sendButton = new Button("senden");

        this.createGrid();

        this.scene = new Scene(this);
        this.stage.setScene(scene);
    }

    public void setOnSendHandeler(){
        //sendButton.addEventHandler();
        throw new NotImplementedException();
    }

    private void createGrid(){
        this.setMinSize(200, 200);
        // col, row, colspan, rowspan
        this.add(chatField, 0, 0, 3, 3);
        this.add(inputField, 1, 3, 2, 0);
        this.add(sendButton, 4, 3, 0, 0);
        this.add(nameLabel, 0, 3, 0, 0);
    }
}

class ClientViewController{
    ClientView view;

    ClientViewController(ClientView view){
        this.view = view;

        view.setOnSendHandeler();
    }

    private boolean onSendText(){
        throw new NotImplementedException();
    }
}