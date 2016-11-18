package Controller;


import Model.ModelShop;
import Strategy.BinaryStrategy;
import Strategy.XMLStrategy;
import View.ViewCustomer;
import View.ViewShop;
import fpt.com.Product;
import fpt.com.SerializableStrategy;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by Team 1/2Hobbyte
 * controller element of View.ViewShop
 */
public class ControllerShop {
    String path;
    private ModelShop modelShop;

    public ControllerShop() throws FileNotFoundException {
    }

    // added event handler to view elements
    public void link(ModelShop model, ViewShop viewShop) {
        this.modelShop = model;
        viewShop.getTable().setItems(modelShop);
        viewShop.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewShop.ADD_BUTTON_ID:
                    addElement(viewShop.getName(), Double.parseDouble(viewShop.getPrice()), Integer.parseInt(viewShop.getQuantity()));
                    break;
                case ViewShop.DEL_BUTTON_ID:
                    deleteElement(viewShop.selectedProduct());
                    break;
                case ViewShop.SAVE_BUTTON_ID:
                    // todo without param?
                    save(viewShop);
                    break;
                case ViewShop.LOAD_BUTTON_ID:
                    // todo
                    load(viewShop);
                    break;
            }
        });
    }

    // add new product to model
    private void addElement(String name, double price, int quantity) {
        try {
            modelShop.add(new Model.Product(name, price, quantity));
        } catch (NumberFormatException e2) {
            error("Please enter Numeric Value");
        }
    }

    // delete product from model
    private void deleteElement(Product product) {
        System.out.println("delete: " + product.getName());
        modelShop.remove(product);
        for (fpt.com.Product p : Services.ProductList.getInstance().getProductlist()) {
            System.out.println("remaining: " + p.getName());
        }
    }

    private void save(ViewShop v) {
        try {
            modelShop.save((SerializableStrategy) getStratagy(v), path);
        } catch (Exception e1) {
            // todo dont catch all exceptions
        }
    }

    private void load(ViewShop v) {
        try {
            modelShop.load((SerializableStrategy) getStratagy(v), path);
        } catch (Exception e1) {
            // todo dont catch all exceptions
        }
    }

    private void error(String s) {
        JOptionPane.showMessageDialog(null,
                "Error : " + s);
    }

    public SerializableStrategy getStratagy(ViewShop v) throws FileNotFoundException {
        String choice = ("" + v.getCboiseBox());
        switch (choice) {
            case "XML-Serialisierung":
                path = "sss.xml";
                return new XMLStrategy();
            case "Binary-Serialisierung":
                path = "sss.ser";
                return new BinaryStrategy();
            default:
                error("Please select SerializableStrategy");
        }
        return null;
    }
}
