package ChatComponents;

import Controller.ControllerChatClientView;
import View.ViewChatClient;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public class ClientInitializer extends Stage {
    public static String url;

    private static ClientInitializer instance;


    public static ClientInitializer getInstance() throws RemoteException, NotBoundException, MalformedURLException {
        if (ClientInitializer.instance == null) {
            ClientInitializer.instance = new ClientInitializer();
        }

        return ClientInitializer.instance;
    }
        public ClientInitializer() throws RemoteException, MalformedURLException, NotBoundException {
        this.setAlwaysOnTop(true);
        url = "//localhost:1099/" + ServerDriver.NAME; // registry
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
                    ((ChatClient)client).login = false;
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch(MalformedURLException e){
                    e.printStackTrace();
                }
                catch(NotBoundException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
