package TCPConnection;

import Controller.ControllerLoginView;
import View.ViewLogin;
import fpt.com.Order;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Team 10
 */
public class StartLoginWindow extends Stage {
    private static StartLoginWindow instance;

    public static StartLoginWindow getInstance(Order order) {
        if (StartLoginWindow.instance == null || !StartLoginWindow.instance.isShowing()) {
            StartLoginWindow.instance = new StartLoginWindow(order);
        }
        StartLoginWindow.instance.requestFocus();
        return StartLoginWindow.instance;
    }

    public StartLoginWindow(Order order) {
        ViewLogin view = new ViewLogin();
        ControllerLoginView ctrl = new ControllerLoginView();
        ctrl.link(order, view);

        Scene scene = new Scene(view);
        this.setScene(scene);
        this.show();
        view.addEventHandler(e -> {
            this.close();
        });
    }
}
