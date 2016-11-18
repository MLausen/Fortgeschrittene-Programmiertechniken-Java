package Controller;


import Model.ModelShop;
import Model.Product;
import Strategy.BinaryStrategy;
import Strategy.XMLStrategy;
import View.ViewCustomer;
import View.ViewShop;
import fpt.com.SerializableStrategy;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by Team 1/2Hobbyte
 */
public class ControllerShop {
    String path;
    private ModelShop modelShop;

    public ControllerShop() throws FileNotFoundException {  }

    // TODO comment
    public void link(ModelShop model, ViewShop viewShop, ViewCustomer viewCustomer) {
        this.modelShop = model;
        viewCustomer.setProducts(modelShop);
        viewShop.getTable().setItems(modelShop);
        viewShop.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewShop.ADD_BUTTON_ID:
                    try {
                        modelShop.add(new Product(viewShop.getName(), Double.parseDouble(viewShop.getPrice()), Integer.parseInt(viewShop.getQuantity())));
                    } catch (NumberFormatException e2) {
                        error("Please enter Numeric Value");
                    }
                    break;
                case ViewShop.DEL_BUTTON_ID:
                    modelShop.remove(viewShop.selectedProduct());
                    break;
                case ViewShop.SAVE_BUTTON_ID:
                    try {
                        modelShop.save((SerializableStrategy) getStratagy(viewShop), path);
                    } catch (Exception e1) {
                    }
                    break;
                case ViewShop.LOAD_BUTTON_ID:
                    try {
                        modelShop.load((SerializableStrategy) getStratagy(viewShop), path);
                    } catch (Exception e1) {

                    }
                    break;
            }
        });
    }

    public void error(String s) {
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
