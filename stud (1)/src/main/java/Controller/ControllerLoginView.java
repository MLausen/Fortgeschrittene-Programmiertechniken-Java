package Controller;

import fpt.com.Order;
import TCPConnection.Client;
import View.ViewLogin;
import javafx.scene.control.Button;
import javafx.util.Pair;

/**
 * Created by Team 10
 */
public class ControllerLoginView {
    private Order order;
    private ViewLogin viewLogin;

    public ControllerLoginView(){}

    // defines controller for shop view
    public void link(Order order, ViewLogin view) {
        this.order = order;
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
        ((Model.Order)this.order).setFinished(true);
    }

    public void cancelLogin(){
    }

    public Pair<String, String> getUserData(){
       return new Pair(this.viewLogin.getName(), this.viewLogin.getPass());
    }
}
