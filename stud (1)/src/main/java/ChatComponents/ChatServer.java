package ChatComponents;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team 10
 */
public class ChatServer extends UnicastRemoteObject implements ChatService{
    private static final long serialVersionUID = 1L;
    private List<ChatClient> clients;

    public ChatServer() throws RemoteException{
        LocateRegistry.createRegistry(1099); // registered
        System.out.println("ChatServer just started.");
    }

    public boolean logout(ChatClient client) throws RemoteException{
        System.out.println("Client with ID " + client.getId() + " has left.");
        return this.clients.remove(client);
    }

    public boolean send(){
        throw new NotImplementedException();
    }

    public List<String> getUserList(){
        List<String> nameList = new ArrayList<String>();

        for (ChatClient c : clients) {
            nameList.add("" + c.getId());
        }

        return nameList;
    }

    @Override
    public boolean login(ChatClient client) throws RemoteException, MalformedURLException {
        //Naming.rebind("chat_server", this); copied lydia
        Naming.rebind("//localhost:1099/" + ServerDriver.NAME, client);
        Naming.rebind("//localhost:1099/" + ServerDriver.NAME, client);
        return this.clients.add(client);
    }

    @Override
    public synchronized boolean send(String message) throws RemoteException {
        // send to all other clients
        for(int i = 0; i < clients.size(); i++){
            clients.get(i).send(message);
        }

        return true; // del
    }
}
