package ChatComponents;

import Helper.ErrorDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public class ClientInitializer extends Application {
    public static String url;
    public static Integer clientCounter = 1;

    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        url = "//localhost:1099/" + ServerDriver.NAME; // registry
        ChatService server = (ChatService) Naming.lookup(url);

        ChatClient client = new ChatClient(clientCounter++, server);
        Naming.rebind(client.getName(), client);

        Thread clientThread = new Thread(client);
        clientThread.start();

        ClientView view = new ClientView(client.getName());
        ClientViewController ctrl = new ClientViewController(view, client);

        Scene scene = new Scene(view);
        stage.setScene(scene);
    }
}
