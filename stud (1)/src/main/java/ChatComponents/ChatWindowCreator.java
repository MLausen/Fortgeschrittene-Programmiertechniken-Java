package ChatComponents;

import Controller.ControllerChatClientView;
import Helper.ErrorDialog;
import TCPConnection.Warehouse;
import View.ViewChatClient;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public class ChatWindowCreator extends Stage {
    private static ChatWindowCreator instance;
    public static String url;

    public static ChatWindowCreator getInstance() throws RemoteException, NotBoundException, MalformedURLException {
        if (ChatWindowCreator.instance == null || !ChatWindowCreator.instance.isShowing()) {
            ChatWindowCreator.instance = new ChatWindowCreator();
        }
        ChatWindowCreator.instance.requestFocus();
        return ChatWindowCreator.instance;
    }

    public ChatWindowCreator() throws RemoteException, MalformedURLException, NotBoundException {
        url = "//localhost:1099/" + Warehouse.SERVER_NAME; // registry
        try {
            ChatService server = (ChatService) Naming.lookup(url);
            ClientService client = new ChatClient(server);
            ViewChatClient view = new ViewChatClient(client.getName());
            ControllerChatClientView ctrl = new ControllerChatClientView(view, client);

            Scene scene = new Scene(view);
            this.setScene(scene);
            this.show();

            ThreadChatClient clientThread = new ThreadChatClient(client);
            Naming.rebind(client.getName(), client);

            Thread thread = new Thread(clientThread);
            thread.start();

            this.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    try {
                        server.logout(client.getName());
                        Naming.unbind(client.getName());
                        ((ChatClient) client).login = false;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry .. Chat server is down..");
        }
    }
}
