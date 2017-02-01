package Controller;

import ChatComponents.ChatClient;
import ChatComponents.ClientService;
import View.ViewChatClient;

import java.rmi.RemoteException;

/**
 * Created by Melli on 01.02.2017.
 */
public class ControllerChatClientView {
    ViewChatClient view;
    ChatClient model;

    public ControllerChatClientView(ViewChatClient view, ClientService model){
        this.view = view;
        this.model = (ChatClient) model;

        view.setOnSendHandeler(e->{
            try {
                view.addText(view.getMessage(), model.getName());
                view.resetInputField();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
    }



}
