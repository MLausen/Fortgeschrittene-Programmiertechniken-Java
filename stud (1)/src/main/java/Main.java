import Controller.ControllerCostumerView;
import Controller.ControllerShop;
import Model.ModelShop;
import Services.ProductList;
import View.ViewCustomer;
import View.ViewShop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Team 10
 */
public class Main extends Application {

    public static void main(String[] args) {
        // launch application
        Application.launch(args);
    }

    @Override
    public void start(Stage CostumerStage) throws Exception {
        // define model for application
        ModelShop model = new ModelShop();

        // define start views
        ViewShop viewShop = new ViewShop();
        ViewCustomer viewCustomer = new ViewCustomer();

        // instanciate controller for views
        ControllerShop controllerShop = new ControllerShop();
        ControllerCostumerView controllerCostumerView = new ControllerCostumerView();

        // prepare customer stage
        CostumerStage.setTitle("Customer");
        Scene scene = new Scene(viewCustomer);
        CostumerStage.setScene(scene);
        CostumerStage.show();

        // prepare shop stage
        Stage shopStage = new Stage();
        shopStage.setTitle("Shop");
        Scene shopScene = new Scene(viewShop);
        shopStage.setScene(shopScene);
        shopStage.show();

        // link view and controller
        controllerShop.link(model, viewShop);
        controllerCostumerView.link(model, viewCustomer);
    }
}
