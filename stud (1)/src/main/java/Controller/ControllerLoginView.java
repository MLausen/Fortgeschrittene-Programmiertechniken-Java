package Controller;

import TCPConnection.Client;
import View.ViewLogin;
import fpt.com.Order;
import javafx.scene.control.Button;
import javafx.util.Pair;

/**
 * Created by Team 10
 */
public class ControllerLoginView {
    private ViewLogin viewLogin;
    Order order;

    public ControllerLoginView(){}

    // defines controller for shop view
    public void link(ViewLogin view, Order order) {
        this.viewLogin = view;
        this.order = order;

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
        ((Model.Order)order).setFinished(true);
    }

    public void cancelLogin(){
    }

    public Pair<String, String> getUserData(){
       return new Pair(this.viewLogin.getName(), this.viewLogin.getPass());
    }
}
