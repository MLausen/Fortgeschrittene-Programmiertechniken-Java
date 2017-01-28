package Controller;

import Helper.ErrorDialog;
import Model.ModelShop;
import TCPConnection.Client;
import View.ViewLogin;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.util.Pair;
import sun.swing.plaf.windows.ClassicSortArrowIcon;

/**
 * Created by Team 10
 */
public class ControllerLoginView {
    private Client client;
    private ViewLogin viewLogin;

    public ControllerLoginView(){}

    // defines controller for shop view
    public void link(Client client, ViewLogin view) {
        this.client = client;
        this.viewLogin = view;

        viewLogin.addEventHandler(e -> {
            //get id we defined in viewlogin
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewLogin.CANCEL_BUTTON:
                    try{
                        this.cancelLogin();
                    }catch(ClassNotFoundException cnfe){
                        ErrorDialog.error("Probleeeem");
                    }
                    break;
                case ViewLogin.LOGIN_BUTTON:
                    try{
                        this.login();
                    }catch(ClassNotFoundException cnfe){
                        ErrorDialog.error("Probleeeem");
                    }
                    break;
            }
        });
    }

    public void login() throws ClassNotFoundException{
        client.buyRequest(getUserData().getKey(), getUserData().getValue());
        viewLogin.close();
    }

    public void cancelLogin() throws ClassNotFoundException{
        viewLogin.close();
    }

    public Pair<String, String> getUserData(){
       return new Pair(this.viewLogin.getName(), this.viewLogin.getPass());
    }
}
