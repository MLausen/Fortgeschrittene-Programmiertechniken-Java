package ChatComponents;

import View.ViewChatClient;
import javafx.scene.control.TextArea;
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
    private TextArea chatArea;
    private Integer id = -1;

    public ChatClient (Integer id, ChatService server) throws RemoteException, MalformedURLException{
        this.id = id;
        this.server = (ChatService) server;
    }

    @Override
    public void send(String message) {
        try {
            server.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void receive(String message) {
        System.out.println("Client " + id + " receives:");
        System.out.println(message);
        if(chatArea.getText() != null){
            this.chatArea.setText(this.chatArea.getText()+ "\n" + message);
        }else{
            this.chatArea.setText(message);
        }
    }

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

    public void setGUIComponent(TextArea chat){
        this.chatArea = chat;
    }
}
