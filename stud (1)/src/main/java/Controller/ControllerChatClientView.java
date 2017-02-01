package Controller;

import ChatComponents.ChatClient;
import ChatComponents.ClientService;
import View.ViewChatClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Melli on 01.02.2017.
 */
public class ControllerChatClientView {
    ViewChatClient view;
    ChatClient model;

    public ControllerChatClientView(ViewChatClient view, ClientService model){
        this.view = view;
        this.model = (ChatClient) model;

        //view.setOnSendHandeler();
    }

    private boolean onSendText(){
        throw new NotImplementedException();
    }
}
