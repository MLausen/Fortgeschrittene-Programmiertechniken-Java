package Controller;

import Model.ModelShop;
import View.ViewCustomer;
import View.ViewShop;
import javafx.scene.control.Button;

/**
 * Created by Melli on 18.11.2016.
 */
public class ControllerCostumerView {
    private ModelShop modelShop;

    public void link(ModelShop model, ViewCustomer viewCustomer) {
        this.modelShop = model;
        viewCustomer.setProducts(modelShop);
    }
}
