package View;

import Model.ModelShop;
import Model.Order;
import fpt.com.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by Team 10
 */
public class ViewCustomer extends BorderPane {
    TableView<fpt.com.Product> tableProducts = new TableView<>();
    TableView<fpt.com.Product> tableOrders = new TableView<>();

    // view components
    private Button buy;
    private Button enterChat;
    private Button add;
    private Label timeLable;
    private Label total;
    private BorderPane box;

    public ViewCustomer() {
        buy = new Button("Buy");
        buy.setId("buy");

        add = new Button("Add");
        add.setId("add");

        enterChat = new Button("Chat");
        enterChat.setId("chat");

        timeLable = new Label();
        total = new Label("");

        box = new BorderPane(null, null, new HBox(10, total, add, buy), null,  new HBox(10,timeLable,enterChat));

        setBottom(box);

        // products table columns
        TableColumn<Product, String> nameColumn = (TableColumn<Product, String>) creatClolumn("Name", "name");
        TableColumn<Product, Double> priceColumn = (TableColumn<Product, Double>) creatClolumn("Price", "price");
        TableColumn<Product, Integer> quantityColumn = (TableColumn<Product, Integer>) creatClolumn("Quantity", "quantity");
        TableColumn<Product, Long> idColumn = (TableColumn<Product, Long>) creatClolumn("Id", "id");

        // order table columns
        TableColumn<Product, String> nameColumnOrder = (TableColumn<Product, String>) creatClolumn("Name", "name");
        TableColumn<Product, Double> priceColumnOrder = (TableColumn<Product, Double>) creatClolumn("Price", "price");
        TableColumn<Product, Integer> quantityColumnOrder = (TableColumn<Product, Integer>) creatClolumn("Count", "quantity");

        //addcloumns to tables
        tableProducts.getColumns().addAll(nameColumn, priceColumn, idColumn, quantityColumn);
        tableOrders.getColumns().addAll(nameColumnOrder, priceColumnOrder, quantityColumnOrder);

        // positioning
        HBox box = new HBox(tableProducts, tableOrders);
        setCenter(box);
    }

    // method used in Controller to add Products from Model to the table in View
    public void setProducts(ModelShop items) {
        tableProducts.setItems(items);
    }

    // to create cloumns
    private TableColumn<Product, ?> creatClolumn(String name, String propertyName) {
        TableColumn<Product, ?> xColumn = new TableColumn<>("" + name);
        xColumn.setCellValueFactory(new PropertyValueFactory<>("" + propertyName));
        return xColumn;
    }

    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        buy.addEventHandler(ActionEvent.ACTION, eventHandler);
        add.addEventHandler(ActionEvent.ACTION, eventHandler);
        enterChat.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void updateTimeLabel(String time) {
        timeLable.setText(time);
    }

    public void totalSum(String text) {
        total.setText(text);
    }

    public void setOrders(Order orders) {
        tableOrders.setItems(orders);
    }

    public Product selectedProduct() {
        return tableProducts.getSelectionModel().getSelectedItem();
    }
}
