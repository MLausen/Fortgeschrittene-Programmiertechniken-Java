package View;

import Model.ModelShop;
import Model.Order;
import fpt.com.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Team 10
 */
public class ViewCustomer extends BorderPane {
    TableView<fpt.com.Product> tableProducts = new TableView<>();
    TableView<fpt.com.Product> tableOrders = new TableView<>();

    // view components
    private Button buy;
    private Button add;
    private Label timeLable;
    private Label total;
    private BorderPane box;

    // threads
    private Thread timeRequestThread;
    private Thread timeResponseThread;

    public ViewCustomer() {
        buy = new Button("Buy");
        buy.setId("buy");

        add = new Button("Add");
        add.setId("add");

        timeLable = new Label();
        total = new Label("");

        box = new BorderPane(null, null, new HBox(10, total, add, buy), null, timeLable);

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

        timeRequest();
        timeRequestThread.start();
        timeResponse();
        timeResponseThread.start();
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

    // method to send a request to a server by a client via udp-package
    public void timeRequest() {
        this.timeRequestThread = new Thread("Time Request") {
            public void run() {
                // own address
                InetAddress adress = null;
                try {
                    adress = InetAddress.getByName("localhost");
                } catch (UnknownHostException e2) {
                    e2.printStackTrace();
                }

                // socket for client
                try (DatagramSocket dSocket = new DatagramSocket()) {
                    try {
                        while (true) {
                            String command = "TIME:";

                            byte buffer[] = null;
                            buffer = command.getBytes();

                            // package for request
                            DatagramPacket packet = new DatagramPacket(buffer,
                                    buffer.length, adress, 6667);
                            // sending package
                            dSocket.send(packet);

                            byte answer[] = new byte[1024];
                            // preparing package for response
                            packet = new DatagramPacket(answer, answer.length);
                            // waiting for response
                            dSocket.receive(packet);
                            String timeText = new String(packet.getData(), 0, packet
                                    .getLength());
                            Platform.runLater(() -> {
                                // code that updates GUI
                                updateTimeLabel(timeText);
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } catch (SocketException e1) {
                    e1.printStackTrace();
                }
            }
        };
    }


    // method to get a timeresponse by a server via udp-package
    public void timeResponse() {
        // could happen?
        if (this.timeResponseThread != null) {
            return;
        }

        this.timeResponseThread = new Thread("Time Response") {
            public void run() {
                // server socket with port 6667
                try (DatagramSocket socket = new DatagramSocket(6667)) {
                    while (true) {

                        // new package
                        DatagramPacket packet = new DatagramPacket(new byte[5], 5);
                        // waiting for package
                        try {
                            socket.receive(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // read data
                        InetAddress address = packet.getAddress();
                        int port = packet.getPort();

                        // data to string
                        String da = new String(packet.getData());

                        // dividing commands by :
                        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
                            // filtering first command
                            String keyword = sc.next();

                            if (keyword.equals("TIME")) {
                                System.out.printf(
                                        "Time-Request from %s  Port %d%n",
                                        address, port);
                                DateFormat df = new SimpleDateFormat("dd.MM.yy   HH:mm:ss");
                                Date dateobj = new Date();
                                byte[] myDate = df.format(dateobj).getBytes();

                                // package with new date to the same Port and address
                                packet = new DatagramPacket(myDate, myDate.length,
                                        address, port);
                                // sending package
                                socket.send(packet);

                            } else {
                                byte[] myDate = null;
                                myDate = new String("Command unknown").getBytes();

                                // package for invalid keyword
                                // preparing for response to the same Port and address
                                packet = new DatagramPacket(myDate, myDate.length,
                                        address, port);
                                // sending package
                                socket.send(packet);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void addEventHandler(EventHandler<ActionEvent> eventHandler) {
        buy.addEventHandler(ActionEvent.ACTION, eventHandler);
        add.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    private void updateTimeLabel(String time) {
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
