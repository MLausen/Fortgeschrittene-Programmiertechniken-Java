package View;

import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by Team 10
 */

public class ViewShop extends BorderPane {
    TableView<fpt.com.Product> table = new TableView<>();

    // TODO create String file
    public static final String ADD_BUTTON_ID ="addButton";
    public static final String DEL_BUTTON_ID = "deleteButton";
    public static final String SAVE_BUTTON_ID = "saveButton";
    public static final String LOAD_BUTTON_ID = "loadButton";

    public static final String BINARY_SER = "Binary-Serialisierung";
    public static final String XML_SER = "XML-Serialisierung";
    public static final String XSTREAM_SER = "XStream XML-Serialisierung";
    public static final String JDBC_SER = "JDBC-Database";
    public static final String JPA_SER = "OpenJPA-Database";

    Button addbtn;
    Button delbtn;
    Button savebtn;
    Button loadbtn;

    TextField priceArea, quantatiyArea, nameArea;
    Label priceLabel, quantatiyLabel, nameLabel;
    HBox box;

    HBox toolbox;
    HBox bigbox;
    ChoiceBox cb;

    public ViewShop() {
        addbtn = new Button("add");
        delbtn = new Button("delete");
        savebtn = new Button("Save");
        loadbtn = new Button("Load");

        addbtn.setId(ADD_BUTTON_ID);
        delbtn.setId(DEL_BUTTON_ID);
        savebtn.setId(SAVE_BUTTON_ID);
        loadbtn.setId(LOAD_BUTTON_ID);

        cb = new ChoiceBox(FXCollections.observableArrayList(BINARY_SER, XML_SER, XSTREAM_SER, JPA_SER));
        toolbox = new HBox(savebtn, loadbtn, cb);
        toolbox.setSpacing(20);

        priceArea = new TextField();
        quantatiyArea = new TextField();
        nameArea = new TextField();

        priceLabel = new Label("\t\tPrice");
        quantatiyLabel = new Label("Quantity");
        nameLabel = new Label("Name");

        box = new HBox(addbtn, delbtn, priceLabel, priceArea, quantatiyLabel, quantatiyArea, nameLabel, nameArea);
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
        TableColumn<fpt.com.Product, Integer> quantityColumn = new TableColumn<>("Quantity");
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

    public String getChoiceBox() {
        return (String) cb.getValue();
    }

    public TableView<fpt.com.Product> getTable() {
        return table;
    }

    public Product selectedProduct() {
        return table.getSelectionModel().getSelectedItem();
    }

    public String getName() {
        return nameArea.getText();
    }

    public String getQuantity() {
        return quantatiyArea.getText();
    }

    public String getPrice() {
        return priceArea.getText();
    }
}
