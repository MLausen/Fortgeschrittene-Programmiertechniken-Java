package ChatComponents;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public interface ClientService extends Remote{
    void send(String message) throws RemoteException;
    String getName();
}
