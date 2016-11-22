package Controller;

import Model.ModelShop;
import View.ViewCustomer;

/**
 * Created Team 10
 */

public class ControllerCostumerView {
    private ModelShop modelShop;

    //link ProductList from ModelShop to Customer View
    public void link(ModelShop model, ViewCustomer viewCustomer) {
        this.modelShop = model;
        viewCustomer.setProducts(modelShop);
    }
}
