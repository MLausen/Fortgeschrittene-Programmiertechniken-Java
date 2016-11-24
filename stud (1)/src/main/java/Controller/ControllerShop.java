package Controller;


import Helper.ErrorDialog;
import Model.ModelShop;
import Strategy.BinaryStrategy;
import Strategy.XMLStrategy;
import Strategy.XStreamStrategy;
import View.ViewShop;
import fpt.com.SerializableStrategy;
import javafx.scene.control.Button;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Team 10
 * controller element of View.ViewShop
 */
public class ControllerShop {
    // path where the file will be saved or gotten for deserialization
    String path;
    private ModelShop modelShop;
    private ViewShop viewShop;

    public ControllerShop() throws FileNotFoundException {   }

    // defines controller for shop view
    public void link(ModelShop model, ViewShop view) {
        this.modelShop = model;
        this.viewShop = view;

        // productList from modelShop to shop view
        viewShop.getTable().setItems(modelShop);

        //TODO single event handler for each button
        // added event handler to view elements
        // to add or delete products
        // or to save or load product list form file
        viewShop.addEventHandler(e -> {
            //get id we defined in viewShop
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case ViewShop.ADD_BUTTON_ID:
                    addElement();
                    break;
                case ViewShop.DEL_BUTTON_ID:
                    deleteElement();
                    break;
                case ViewShop.SAVE_BUTTON_ID:
                    try {
                        save();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case ViewShop.LOAD_BUTTON_ID:
                    try {
                        load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }
        });
    }

    // add new product to model
    private void addElement() {
        try {
            modelShop.add(new Model.Product(viewShop.getName(), Double.parseDouble(viewShop.getPrice()), Integer.parseInt(viewShop.getQuantity())));
        } catch (NumberFormatException e2) {
            ErrorDialog.error("Please enter Numeric Value");
        }
    }

    // delete product from model
    private void deleteElement() {
        //check if a product is selected in view
        if (viewShop.selectedProduct() == null) {
            ErrorDialog.error("Please select a product to delete.");
            return;
        }
        System.out.println("delete: " + viewShop.selectedProduct().getName());
        //call remove method in modelShop
        modelShop.remove(viewShop.selectedProduct());

    }

    private void save() throws IOException {
        //minimum size to serialize is 5 products
        if (modelShop.getList().size() > 4) {

            //serialization after getting strategy and file path
            modelShop.serialization(getStrategy(viewShop), path);
        } else {
            ErrorDialog.error("Min 5 elements to serialization in file.");
        }
    }

    private void load() throws IOException {
        //deserialization after getting strategy and file path
        modelShop.deserialization(getStrategy(viewShop), path);
    }

    //get the selected strategy from choice box and return it as a new object with its file path
    public SerializableStrategy getStrategy(ViewShop v) {
        String choice = ("" + v.getChoiceBox());
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
                ErrorDialog.error("Please select one of the saving methods");
        }
        return null;
    }
}
