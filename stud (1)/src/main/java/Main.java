import Controller.ControllerCostumerView;
import Controller.ControllerShop;
import Model.ModelShop;
import Services.ProductFactory;
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
        // prepare
        ProductFactory.createProducts();
        // launch application
        Application.launch(args);
    }

    // TODO question Melli
    // What if content of main view changes?
    // still need primary stage!? --> give as parameter to view???

    @Override
    public void start(Stage CostumerStage) throws Exception {
        // defining model for application
        // should be singleton pattern??
        ModelShop model = new ModelShop();

        // defining start views
        ViewShop viewShop = new ViewShop();
        ViewCustomer viewCustomer = new ViewCustomer();

        // instanciate controller for views
        ControllerShop controllerShop = new ControllerShop();
        ControllerCostumerView controllerCostumerView = new ControllerCostumerView();

        //customer stage
        CostumerStage.setTitle("Customer");
        Scene scene = new Scene(viewCustomer);
        CostumerStage.setScene(scene);
        CostumerStage.show();

        //shop stage
        Stage shopStage = new Stage();
        shopStage.setTitle("Shop");
        Scene shopScene = new Scene(viewShop);
        shopStage.setScene(shopScene);
        shopStage.show();

        //link
        controllerShop.link(model, viewShop);
        controllerCostumerView.link(model, viewCustomer);
    }
}
