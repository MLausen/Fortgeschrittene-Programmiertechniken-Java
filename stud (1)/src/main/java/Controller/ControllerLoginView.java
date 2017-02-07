package Controller;

import TCPConnection.Client;
import View.ViewLogin;
import javafx.scene.control.Button;
import javafx.util.Pair;

/**
 * Created by Team 10
 */
public class ControllerLoginView {
    private Client client;
    private ViewLogin viewLogin;

    public ControllerLoginView(){}

    // defines controller for shop view
    public void link(ViewLogin view) {
        this.client = Client.getInstance();
        this.viewLogin = view;

        viewLogin.addEventHandler(e -> {
            //get id we defined in viewlogin
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewLogin.CANCEL_BUTTON:
                        this.cancelLogin();
                    break;
                case ViewLogin.LOGIN_BUTTON:
                        this.login();
                    break;
            }
        });
    }

    public void login(){
        client.buyRequest(getUserData().getKey(), getUserData().getValue());

    }

    public void cancelLogin(){
    }

    public Pair<String, String> getUserData(){
       return new Pair(this.viewLogin.getName(), this.viewLogin.getPass());
    }
}
