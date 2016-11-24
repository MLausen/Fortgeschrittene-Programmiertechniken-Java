package View;

import Model.ModelShop;
import fpt.com.Product;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by Team 10
 */
public class ViewCustomer extends BorderPane {
    TableView<fpt.com.Product> tableProducts = new TableView<>();
    TableView<fpt.com.Product> tableOrders = new TableView<>();

    Button b ;
    Button buy ;
    HBox buybox;

    public ViewCustomer(){
        buy = new Button("Buy");
        buybox = new HBox(buy);
        setBottom(buybox);
        buybox.setAlignment(Pos.BOTTOM_RIGHT);

        // products table columns
        TableColumn<Product, String> nameColumn = (TableColumn<Product, String>) creatClolumn("Name");
        TableColumn<Product, Double> priceColumn = (TableColumn<Product, Double>) creatClolumn("Price");
        TableColumn<Product, Integer> quantityColumn = (TableColumn<Product, Integer>) creatClolumn("Quantity");
        TableColumn<Product, Long> idColumn = (TableColumn<Product, Long>) creatClolumn("Id");

        // order table columns
        TableColumn<Product, String> nameColumnOrder = (TableColumn<Product, String>) creatClolumn("Name");
        TableColumn<fpt.com.Product, Double> priceColumnOrder = (TableColumn<Product, Double>) creatClolumn("Price");
        TableColumn<fpt.com.Product, Long> quantityColumnOrder = (TableColumn<Product, Long>) creatClolumn("Buy Count");

        //addcloumns to tables
        tableProducts.getColumns().addAll(nameColumn, priceColumn,idColumn, quantityColumn);
        tableOrders.getColumns().addAll(nameColumnOrder , priceColumnOrder , quantityColumnOrder);

        // positioning
        HBox box = new HBox(tableProducts,tableOrders);
        setCenter(box);
    }

    // method used in Controller to add Products from Model to the table in View
    public void setProducts(ModelShop x ) {
        tableProducts.setItems(x);
    }
    // to create cloumns
    private  TableColumn<Product,?> creatClolumn(String name){
        TableColumn<Product,?> xColumn = new TableColumn<>(""+name);
        xColumn.setCellValueFactory(new PropertyValueFactory<>(""+name));
        return xColumn;
    }
}
