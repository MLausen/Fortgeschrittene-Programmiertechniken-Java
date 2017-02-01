package ChatComponents;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team 10
 */
public class ChatServer extends UnicastRemoteObject implements ChatService{
    private static final long serialVersionUID = 1L;
    private List<String> clients = new ArrayList<>();

    public ChatServer() throws RemoteException{
        System.out.println("ChatServer just started.");
    }

    public boolean logout(String client) throws RemoteException{
        System.out.println("Client with ID " + client + " has left.");
        this.send("Client " + client + " hat den Chat verlassen.");
        return this.clients.remove(client);
    }

    public List<String> getUserList(){
        return this.clients;
    }

    @Override
    public boolean login(String client) throws RemoteException {
        System.out.println("Client " + client + " logged in.");
        this.send("Client " + client + " hat den Chat betreten.");
        return this.clients.add(client);

    }

    @Override
    public synchronized void send(String message) throws RemoteException {
        System.out.println("Send \"" + message + "\" to each client.");

        for(int i = 0; i < clients.size(); i++){
            try {
                ClientService client = (ClientService) Naming.lookup(clients.get(i));
                client.receive(message);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
