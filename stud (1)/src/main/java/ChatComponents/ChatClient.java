package ChatComponents;

import View.ViewChatClient;
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
