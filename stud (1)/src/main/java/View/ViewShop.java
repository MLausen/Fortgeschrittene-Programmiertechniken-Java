package View;

import com.sun.javafx.scene.control.skin.ChoiceBoxSkin;
import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by Team 1/2Hobbyte
 */

public class ViewShop extends BorderPane {
    TableView<fpt.com.Product> table = new TableView<>();

    Button addbtn;
    Button delbtn;
    Button savebtn;
    Button loadbtn;

    TextField price, quantatiy, name;
    Label priceL, quantatiyL, nameL;
    HBox box;

    HBox toolbox;
    HBox bigbox;
    ChoiceBox cb;

    public ViewShop() {
        addbtn = new Button("add");
        delbtn = new Button("delete");
        savebtn = new Button("Save");
        loadbtn = new Button("Load");

        cb = new ChoiceBox(FXCollections.observableArrayList("Binary-Serialisierung", "XML-Serialisierung", "XStream XML-Serialisierung:"));
        toolbox = new HBox(savebtn, loadbtn, cb);
        toolbox.setSpacing(20);

        addbtn.setId("addButton");
        delbtn.setId("deleteButton");
        savebtn.setId("saveButton");
        loadbtn.setId("loadButton");

        price = new TextField();
        quantatiy = new TextField();
        name = new TextField();

        priceL = new Label("          Price");
        quantatiyL = new Label("Quantity");
        nameL = new Label("Name");

        box = new HBox(addbtn, delbtn, priceL, price, quantatiyL, quantatiy, nameL, name);
        bigbox = new HBox(box);
        bigbox.setAlignment(Pos.CENTER);
        bigbox.setPrefHeight(50);
        //Name column
        TableColumn<fpt.com.Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price column
        TableColumn<fpt.com.Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity column
        TableColumn<fpt.com.Product, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        //ids column
        TableColumn<fpt.com.Product, Long> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        table.getColumns().addAll(nameColumn, priceColumn, idColumn, quantityColumn);
        setCenter(table);
        setBottom(bigbox);
        setTop(toolbox);
    }

    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        addbtn.addEventHandler(ActionEvent.ACTION, eventHandler);
        delbtn.addEventHandler(ActionEvent.ACTION, eventHandler);
        savebtn.addEventHandler(ActionEvent.ACTION, eventHandler);
        loadbtn.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public String getCboiseBox() {
        return (String) cb.getValue();
    }

    public TableView<fpt.com.Product> getTable() {
        return table;
    }

    public Product selectedProduct() {
        return (Product) table.getSelectionModel().getSelectedItem();
    }

    public String getName() {
        return name.getText();
    }

    public String getQuantity() {
        return quantatiy.getText();
    }

    public String getPrice() {
        return price.getText();
    }
}
