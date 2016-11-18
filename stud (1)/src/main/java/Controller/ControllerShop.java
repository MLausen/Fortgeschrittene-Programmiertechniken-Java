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
    private ViewShop viewShop;
    public ControllerShop() throws FileNotFoundException {
    }

    // added event handler to view elements
    public void link(ModelShop model, ViewShop view) {
        this.modelShop = model;
        this.viewShop = view;

        viewShop.getTable().setItems(modelShop);
        viewShop.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewShop.ADD_BUTTON_ID:
                    addElement();
                    break;
                case ViewShop.DEL_BUTTON_ID:
                    deleteElement();
                    break;
                case ViewShop.SAVE_BUTTON_ID:
                    save();
                    break;
                case ViewShop.LOAD_BUTTON_ID:
                    load();
                    break;
            }
        });
    }

    // add new product to model
    private void addElement() {
        try {
            modelShop.add(new Model.Product(viewShop.getName(), Double.parseDouble(viewShop.getPrice()), Integer.parseInt(viewShop.getQuantity())));
        } catch (NumberFormatException e2) {
            error("Please enter Numeric Value");
        }
    }

    // delete product from model
    private void deleteElement() {
        System.out.println("delete: " + viewShop.selectedProduct().getName());
        modelShop.remove(viewShop.selectedProduct());
        for (fpt.com.Product p : Services.ProductList.getInstance().getProductlist()) {
            System.out.println("remaining: " + p.getName());
        }
    }

    private void save() {
        try {
            modelShop.save((SerializableStrategy) getStratagy(viewShop), path);
        } catch (Exception e1) {
            // todo dont catch all exceptions
        }
    }

    private void load() {
        try {
            modelShop.load((SerializableStrategy) getStratagy(viewShop), path);
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
