package ChatComponents;

import Helper.ErrorDialog;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public class ClientDriver {
    public static String url;
    public static Integer clientCounter = 1;

    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        url = "rmi://localhost:1099/" + ServerDriver.NAME; // registry
        ChatServer server = (ChatServer) Naming.lookup(url);

        ChatClient client = new ChatClient(clientCounter++, server);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
