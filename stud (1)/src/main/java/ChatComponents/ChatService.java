package ChatComponents;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Team 10
 */
public interface ChatService extends Remote {
    boolean login(String client) throws RemoteException, MalformedURLException;

    boolean logout(String client) throws RemoteException;

    boolean send(String message) throws RemoteException;

    List<String> getUserList() throws RemoteException;
}
