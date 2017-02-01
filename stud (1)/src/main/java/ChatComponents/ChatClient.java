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
    public boolean login = true;

    public ChatClient (ChatService server) throws RemoteException, MalformedURLException{
        this.id = server.getUserList().size() + 1;
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
        System.out.println("Client " + id + " receives: " + message);

        if(chatArea.getText() != null){
            this.chatArea.setText(this.chatArea.getText()+ "\n" + message);
        }else{
            this.chatArea.setText(message);
        }
    }

    @Override
    public String getName() {
        return ("Person" + this.id);
    }

    public Integer getId() {
        return id;
    }

    public ChatService getServer() {
        return server;
    }

    public void setGUIComponent(TextArea chat){
        this.chatArea = chat;
        String clients = "";
        try {
            for(int i = 0; i < server.getUserList().size(); i++){
                clients += server.getUserList().get(i) + "\n";
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(clients.equals("")){
            clients = "keine";
        }
        this.chatArea.setText(this.chatArea.getText() + "\nBisher anwesende Personen:\n" + clients);
    }
}
