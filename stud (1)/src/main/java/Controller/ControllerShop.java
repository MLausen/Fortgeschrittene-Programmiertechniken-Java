package Controller;


import Helper.ErrorDialog;
import Model.ModelShop;
import Strategy.BinaryStrategy;
import Strategy.XMLStrategy;
import Strategy.XStreamStrategy;
import View.ViewCustomer;
import View.ViewShop;
import fpt.com.Product;
import fpt.com.SerializableStrategy;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by Team 10
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

    // TODO check if quantity > 0
    // add new product to model
    private void addElement() {
        try {
            modelShop.add(new Model.Product(viewShop.getName(), Double.parseDouble(viewShop.getPrice()), Integer.parseInt(viewShop.getQuantity())));
            System.out.println(modelShop.getList().size());
        } catch (NumberFormatException e2) {
            ErrorDialog.error("Please enter Numeric Value");
        }
    }

    // delete product from model
    private void deleteElement() {
        if (viewShop.selectedProduct() == null) {
            ErrorDialog.error("Please add product first.");
            return;
        }
        System.out.println("delete: " + viewShop.selectedProduct().getName());
        modelShop.remove(viewShop.selectedProduct());
        for (fpt.com.Product p : Services.ProductList.getInstance().getProductlist()) {
            System.out.println("remaining: " + p.getName());
        }
    }

    private void save() {
        if (modelShop.getList().size() > 4) {
            try {
                modelShop.save(getStratagy(viewShop), path);
            } catch (Exception e1) {
                // todo dont catch all exceptions
            }
        } else {
            ErrorDialog.error("Min 5 elements to save in file.");
        }
    }

    private void load() {
        try {
            modelShop.load(getStratagy(viewShop), path);
        } catch (Exception e1) {
            // todo dont catch all exceptions
        }
    }

    public SerializableStrategy getStratagy(ViewShop v) {
        String choice = ("" + v.getCboiseBox());
        switch (choice) {
            case ViewShop.XML_SER:
                path = "products.xml";
                return new XMLStrategy();
            case ViewShop.BINARY_SER:
                path = "products.ser";
                return new BinaryStrategy();
            case ViewShop.XSTREAM_SER:
                path = "xproducts.xml";
                return new XStreamStrategy();
            default:
                ErrorDialog.error("something went wrong");
        }
        return null;
    }
}
