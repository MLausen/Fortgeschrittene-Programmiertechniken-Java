package Controller;

import Model.ModelShop;
import View.ViewCustomer;
import TCPConnection.Client;

/**
 * Created Team 10
 */

public class ControllerCustomerView {
    private ModelShop modelShop;
    private Client client = new Client();

    // defines controller for customer view
    public void link(ModelShop model, ViewCustomer viewCustomer) {
        this.modelShop = model;
        // link ProductList from ModelShop to Customer View
        viewCustomer.setProducts(modelShop);
        viewCustomer.addEventHandler(event -> {
            buyOperation();
        });
    }

    private void buyOperation() {
        if(client.loginRequest()){
            System.out.println("Hallo i can send orders");
        }
    }
}
