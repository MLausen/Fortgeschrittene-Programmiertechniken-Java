package Controller;

import Model.ModelShop;
import View.ViewCustomer;

/**
 * Created Team 10
 */

public class ControllerCustomerView {
    private ModelShop modelShop;

    // defines controller for customer view
    public void link(ModelShop model, ViewCustomer viewCustomer) {
        this.modelShop = model;

        // link ProductList from ModelShop to Customer View
        viewCustomer.setProducts(modelShop);
    }
}
