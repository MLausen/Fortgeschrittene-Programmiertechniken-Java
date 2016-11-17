import Helper.IdGenerator;
import Helper.IdOverFlowException;
import Model.ModelShop;
import Model.Product;
import Strategy.BinaryStrategy;
import Strategy.XMLStrategy;
import View.ViewCustomer;
import View.ViewShop;
import fpt.com.*;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by Team 1/2Hobbyte
 */
public class ControllerShop {
    IdGenerator idGen = new IdGenerator();
    String path;

    public ControllerShop() throws FileNotFoundException {
    }

    // TODO comment
    public void link(ModelShop modelShop, ViewShop viewShop, ViewCustomer viewCustomer) {
        viewCustomer.setProducts(modelShop);
        viewShop.getTable().setItems(modelShop);
        viewShop.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case "addButton":
                    try {
                        modelShop.add(new Product(viewShop.getName(), Integer.parseInt(viewShop.getQuantity()), Double.parseDouble(viewShop.getPrice())));
                    } catch (NumberFormatException e2) {
                        error("Please enter Numeric Value");
                    }
                    break;
                case "deleteButton":
                    modelShop.remove(viewShop.selectedProduct());
                    break;
                case "saveButton":
                    try {
                        modelShop.save((SerializableStrategy) getStratagy(viewShop), path);
                    } catch (Exception e1) {
                    }
                    break;
                case "loadButton":
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
