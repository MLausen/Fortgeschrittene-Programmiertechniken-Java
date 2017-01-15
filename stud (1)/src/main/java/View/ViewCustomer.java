package View;

import Model.ModelShop;
import fpt.com.Product;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Team 10
 */
public class ViewCustomer extends BorderPane {
    TableView<fpt.com.Product> tableProducts = new TableView<>();
    TableView<fpt.com.Product> tableOrders = new TableView<>();

    Button b ;
    Button buy ;
    HBox buybox;

    private Thread timeRequestThread;
    private Thread timeResponseThread;

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

        timeRequest();
        timeRequestThread.start();
        timeResponse();
        timeResponseThread.start();
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

    // method to send a request to a server by a client via udp-package
    public void timeRequest(){
        this.timeRequestThread = new Thread("Time Request") {
            public void run() {
                // own address
                InetAddress ia = null;
                try {
                    ia = InetAddress.getByName("localhost");
                } catch (UnknownHostException e2) {
                    e2.printStackTrace();
                }

                // socket for client
                try (DatagramSocket dSocket = new DatagramSocket(5555);) {

                    try {
                        int i = 0;
                        while (i < 10) {
                            String command = "TIME:";

                            byte buffer[] = null;
                            buffer = command.getBytes();

                            // package for request
                            DatagramPacket packet = new DatagramPacket(buffer,
                                    buffer.length, ia, 6667);
                            // sending package
                            dSocket.send(packet);

                            byte answer[] = new byte[1024];
                            // preparing package for response
                            packet = new DatagramPacket(answer, answer.length);
                            // waiting for response
                            dSocket.receive(packet);

                            System.out.println(new String(packet.getData(), 0, packet
                                    .getLength()));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            i++;
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
    public void timeResponse(){
        this.timeResponseThread = new Thread("Time Response") {
            public void run() {
                // server socket with port 6667
                try (DatagramSocket socket = new DatagramSocket(6667);) {
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
                        int len = packet.getLength();
                        byte[] data = packet.getData();

                        System.out.printf(
                                "Anfrage von %s vom Port %d mit der Länge %d:%n%s%n",
                                address, port, len, new String(data, 0, len));

                        // data to string
                        String da = new String(packet.getData());
                        // dividing datas by :
                        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
                            // filtering first command
                            String keyword = sc.next();

                            if (keyword.equals("TIME")) {

                                Date dt = new Date();
                                byte[] myDate = dt.toString().getBytes();

                                // package with new date
                                packet = new DatagramPacket(myDate, myDate.length,
                                        address, port);
                                // sending package
                                socket.send(packet);

                            } else {
                                byte[] myDate = null;
                                myDate = new String("Command unknown").getBytes();

                                // package for invalid keyword
                                // preparing for response
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
}
