package ChatComponents;

import Helper.ErrorDialog;

import java.rmi.RemoteException;

/**
 * Created by Melli on 31.01.2017.
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("Client with ID " + client.getId() + " joins the chat.");
        String message = "Hello, my name is " + client.getName();

        int i = 0; // del
        //while(true){
        do{
            try {
                client.getServer().send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
                ErrorDialog.error("RemoteException");
            }
            i++;
        }while(i < 20);

        try {
            client.getServer().logout(client.getName());
        } catch (RemoteException e) {
            e.printStackTrace();
            ErrorDialog.error("RemoteException");
        }
    }
}
