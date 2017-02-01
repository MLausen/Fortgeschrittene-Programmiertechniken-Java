package ChatComponents;

import Controller.ControllerChatClientView;
import View.ViewChatClient;
import javafx.application.Application;
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
public class ClientInitializer extends Application {
    public static String url;

    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        url = "//localhost:1099/" + ServerDriver.NAME; // registry
        ChatService server = (ChatService) Naming.lookup(url);

        ClientService client = new ChatClient(server);
        ViewChatClient view = new ViewChatClient(client.getName());
        ControllerChatClientView ctrl = new ControllerChatClientView(view, client);

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();

        ThreadChatClient clientThread = new ThreadChatClient(client);
        Naming.rebind(client.getName(), client);

        Thread thread = new Thread(clientThread);
        thread.start();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                stage.close();
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
