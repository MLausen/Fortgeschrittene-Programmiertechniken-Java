import Controller.ControllerShop;
import Model.ModelShop;
import Services.ProductFactory;
import View.ViewCustomer;
import View.ViewShop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Team 1/2Hobbyte
 */

public class Main extends Application {

    public static void main(String[] args) {
        ProductFactory.createProducts();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ModelShop modelShop = new ModelShop();
        ViewShop viewShop = new ViewShop();
        ViewCustomer viewCustomer = new ViewCustomer();
        ControllerShop controllerShop = new ControllerShop();

        //link
        controllerShop.link(modelShop, viewShop, viewCustomer);

        //customer stage
        primaryStage.setTitle("Customer");
        Scene scene = new Scene(viewCustomer);
        primaryStage.setScene(scene);
        primaryStage.show();

        //shop stage
        Stage anotherStage = new Stage();
        anotherStage.setTitle("Shop");
        Scene shopScene = new Scene(viewShop);
        anotherStage.setScene(shopScene);
        anotherStage.show();
    }
}
