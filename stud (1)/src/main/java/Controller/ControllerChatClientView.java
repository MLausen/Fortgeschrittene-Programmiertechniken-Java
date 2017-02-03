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

        setClientEventHandler();
    }

    public void sendToServer(String message) throws RemoteException {
        model.send(model.getName() + ": " + message);
    }

    private void setClientEventHandler(){
        view.setOnSendHandeler(e->{
            String message = view.getMessage();
            if(message != null && !message.equals("")){
                view.resetInputField("");
                try {
                    this.sendToServer(message);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });

        view.setKeyPressedHandler(view);
    }
}
