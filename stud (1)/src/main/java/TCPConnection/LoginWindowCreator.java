package TCPConnection;

import Controller.ControllerLoginView;
import View.ViewLogin;
import fpt.com.Order;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Team 10
 */
public class LoginWindowCreator extends Stage {
    private static LoginWindowCreator instance;


    public static LoginWindowCreator getInstance(Order order) {
        if (LoginWindowCreator.instance == null || !LoginWindowCreator.instance.isShowing()) {
            LoginWindowCreator.instance = new LoginWindowCreator(order);
        }
        LoginWindowCreator.instance.requestFocus();
        return LoginWindowCreator.instance;
    }

    public LoginWindowCreator(Order order) {
        Client.getInstance().setOrder((Model.Order) order);
        ViewLogin view = new ViewLogin();
        ControllerLoginView ctrl = new ControllerLoginView();
        ctrl.link(view);

        Scene scene = new Scene(view);
        this.setScene(scene);
        this.show();
        view.addEventHandler(e -> {
            this.close();
        });
    }
}
