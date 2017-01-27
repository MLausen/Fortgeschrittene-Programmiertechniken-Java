package Controller;

import Model.ModelShop;
import View.ViewLogin;
import View.ViewShop;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Melli on 27.01.2017.
 */
public class ControllerLoginView {
    private ModelShop modelShop;
    private ViewLogin viewShop;

    public ControllerLoginView(){}

    // defines controller for shop view
    public void link(ModelShop model, ViewLogin view) {
        this.modelShop = model;
        this.viewShop = view;

        viewShop.addEventHandler(e -> {
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

    public void login(){}

    public void cancelLogin(){}

    public Pair<String, String> getUserData(){
       return new Pair(this.viewShop.getName(), this.viewShop.getPass());
    }
}
