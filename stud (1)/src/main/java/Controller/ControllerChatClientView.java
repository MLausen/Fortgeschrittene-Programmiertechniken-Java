package Controller;

import ChatComponents.ChatClient;
import ChatComponents.ClientService;
import View.ViewChatClient;

import java.rmi.RemoteException;

/**
 * Created by Team10
 */
public class ControllerChatClientView {
    ViewChatClient view;
    ChatClient model;

    public ControllerChatClientView(ViewChatClient view, ClientService model){
        this.view = view;
        this.model = (ChatClient) model;
        ((ChatClient) model).setGUIComponent(view.getChatArea());

        view.setOnSendHandeler(e->{
            String message = view.getMessage();
            if(message != null || message.equals("")){
                view.resetInputField();
                this.sendToServer(message);
            }
        });
    }

    public void sendToServer(String message){
        model.send(model.getName() + ": " + message);
    }
}
