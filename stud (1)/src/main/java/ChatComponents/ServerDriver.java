package ChatComponents;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by Team 10
 */
public class ServerDriver {
    public static final String NAME = "Team10_RMIChatServer";

    public static void main(String args[]) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1099); // registered
        Naming.rebind("//localhost:1099/" + ServerDriver.NAME, new ChatServer());
    }
}
