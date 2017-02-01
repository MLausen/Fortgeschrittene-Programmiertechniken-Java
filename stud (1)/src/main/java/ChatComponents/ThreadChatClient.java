package ChatComponents;

import Helper.ErrorDialog;
import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * Created by Team 10
 */
public class ThreadChatClient implements Runnable {
    ChatClient client;

    public ThreadChatClient(ClientService client){
        this.client = (ChatClient) client;
    }

    @Override
    public void run(){
        try {
            client.getServer().login(client.getName());
            System.out.println("Client with ID " + client.getId() + " joins the chat.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        while(client.login == true){   }

        System.out.println("ended client thread");
        // TODO thread still active?
    }
}
