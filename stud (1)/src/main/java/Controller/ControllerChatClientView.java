package Controller;

import ChatComponents.ChatClient;
import ChatComponents.ClientService;
import View.ViewChatClient;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Team10
 */
public class ControllerChatClientView implements Observer {
    ViewChatClient view;
    ChatClient model;

    public ControllerChatClientView(ViewChatClient view, ClientService model) {
        this.view = view;
        this.model = (ChatClient) model;
        view.getChatArea().setText(((ChatClient) model).observable.getText());

        ((ChatClient) model).observable.addObserver(this);
        setClientEventHandler();
    }

    public void sendToServer(String message) throws RemoteException {
        model.send(model.getName() + ": " + message);
    }

    private void setClientEventHandler() {
        view.setOnSendHandeler(e -> {
            String message = view.getMessage();
            if (message != null && !message.equals("")) {
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

    @Override
    public void update(Observable o, Object arg) {
        String text = (String) arg;
        this.view.getChatArea().setText(text);
    }
}
