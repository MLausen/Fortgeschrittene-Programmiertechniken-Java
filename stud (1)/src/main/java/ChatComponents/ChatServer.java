package ChatComponents;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
        return this.clients.remove(client);
    }

    public boolean send(){
        throw new NotImplementedException();
    }

    public List<String> getUserList(){
        return this.clients;
    }

    @Override
    public boolean login(String client) throws RemoteException {
        //Naming.rebind("chat_server", this); copied lydia
       // Naming.rebind("//localhost:1099/" + ServerDriver.NAME, client); --> old
        return this.clients.add(client);
    }

    @Override
    public synchronized boolean send(String message) throws RemoteException {
        // send to all other clients
        for(int i = 0; i < clients.size(); i++){
            try {
                ClientService client = (ClientService) Naming.lookup(clients.get(i));
                client.send(message);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return true; // del
    }
}
