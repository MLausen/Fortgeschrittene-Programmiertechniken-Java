package Controller;

import Model.ModelShop;
import View.ViewCustomer;
import View.ViewShop;
import javafx.scene.control.Button;

/**
 * Created Team 10
 */
public class ControllerCostumerView {
    private ModelShop modelShop;

    public void link(ModelShop model, ViewCustomer viewCustomer) {
        this.modelShop = model;
        viewCustomer.setProducts(modelShop);
    }
}
