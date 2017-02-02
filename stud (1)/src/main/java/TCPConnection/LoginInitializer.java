package TCPConnection;

import Controller.ControllerLoginView;
import View.ViewLogin;
import fpt.com.Order;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Team 10
 */
public class LoginInitializer extends Stage {




    private static LoginInitializer instance;


    public static LoginInitializer getInstance(Order order)  {
        if (LoginInitializer.instance == null || !LoginInitializer.instance.isShowing()) {
            LoginInitializer.instance = new LoginInitializer(order);
        }
        LoginInitializer.instance.requestFocus();
        return LoginInitializer.instance;
    }
    public LoginInitializer(Order order)  {

        Client client = new Client((Model.Order) order);
        ViewLogin view = new ViewLogin();
        ControllerLoginView ctrl = new ControllerLoginView();
        ctrl.link(client,view);

        Scene scene = new Scene(view);
        this.setScene(scene);
        this.show();
        view.addEventHandler(e->{
            this.close();
        });
    }
}
