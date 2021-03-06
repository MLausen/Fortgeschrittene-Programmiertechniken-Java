package ChatComponents;

import javafx.scene.control.TextArea;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Team 10
 */
public class ChatClient extends UnicastRemoteObject implements ClientService {
    private static final long serialVersionUID = 1L;

    private ChatService server;
    public ChatContentObservable observable;

    private String prefix = "Person";
    public volatile boolean login = true;
    private Integer id = -1;

    public ChatClient(ChatService server) throws RemoteException, MalformedURLException {
        this.server = (ChatService) server;
        setId();
        observable = new ChatContentObservable();
        observable.setText(getInitiationMessage());
    }

    @Override
    public void send(String message) throws RemoteException {
        try {
            server.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void receive(String message) throws RemoteException {
        observable.setText(observable.getText() + "\n" + message);
    }

    @Override
    public String getName() throws RemoteException {
        return (prefix + this.id);
    }

    public Integer getId() {
        return id;
    }

    private synchronized void setId() throws RemoteException, MalformedURLException {
        int i = 1;
        while (true) {
            if (server.getUserList().indexOf(new String(prefix + (i))) == -1) {
                this.id = (i);
                break;
            }
            i++;
        }
    }

    public ChatService getServer() {
        return server;
    }

    private String getInitiationMessage() {
        String clients = "";
        try {
            for (int i = 0; i < server.getUserList().size(); i++) {
                clients += server.getUserList().get(i) + "\n";
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (clients.equals("")) {
            clients = "keine";
        }
        return (observable.getText() + "\n" + "\nBisher anwesende Personen:\n" + clients);
    }
}
