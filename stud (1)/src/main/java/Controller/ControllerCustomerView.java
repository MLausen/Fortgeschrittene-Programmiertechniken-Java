package Controller;

import Helper.ErrorDialog;
import Model.ModelShop;
import Model.Order;
import Model.Product;
import TCPConnection.Client;
import View.ViewCustomer;
import javafx.scene.control.Button;


/**
 * Created Team 10
 */

public class ControllerCustomerView {
    private ModelShop modelShop;
    private ViewCustomer viewCustomer;
    private Order order;
    Client client;

    // defines controller for customer view
    public void link(ModelShop model, ViewCustomer view, Order o) {
        this.modelShop = model;
        this.viewCustomer = view;
        this.order = o;

        // link ProductList from ModelShop to Customer View
        viewCustomer.setProducts(modelShop);
        viewCustomer.setOrders(order);

        viewCustomer.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case "add":
                    addElement();
                    break;
                case "buy":
                    try {
                        client = new Client(order);
                        buyOperation();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        });
    }

    private void addElement() {
        if (viewCustomer.selectedProduct() != null) {
            if (viewCustomer.selectedProduct().getQuantity() > 0) {
                Product d = new Product(viewCustomer.selectedProduct().getName(), viewCustomer.selectedProduct().getPrice(), 1);
                order.add(d);
                modelShop.decreaseQuantity(viewCustomer.selectedProduct());
                viewCustomer.totalSum("total  " + order.getSum() + " €");
            } else {
                ErrorDialog.error("not available now");
            }
        }
        else{
            ErrorDialog.error("select product first");
        }
    }

    private void buyOperation() throws ClassNotFoundException {
        if (client.buyRequest()) {
            viewCustomer.totalSum("Sent Successfully  ✓");
            order.clear();
        }
    }
}
