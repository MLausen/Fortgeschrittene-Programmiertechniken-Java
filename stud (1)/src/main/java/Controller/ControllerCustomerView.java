package Controller;

import ChatComponents.ClientInitializer;
import Helper.ErrorDialog;
import Model.ModelShop;
import Model.Order;
import Model.Product;
import TCPConnection.LoginInitializer;
import View.ViewCustomer;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * Created Team 10
 */

public class ControllerCustomerView {
    private ModelShop modelShop;
    private ViewCustomer viewCustomer;
    private Order order;


    // threads
    private Thread timeRequestThread;
    private Thread timeResponseThread;

    // defines controller for customer view
    public void link(ModelShop model, ViewCustomer view, Order o) {
        this.modelShop = model;
        this.viewCustomer = view;
        this.order = o;

        // link ProductList from ModelShop to Customer View
        viewCustomer.setProducts(modelShop);
        viewCustomer.setOrders(order);

        timeRequest();
        timeRequestThread.start();
        timeResponse();
        timeResponseThread.start();

        viewCustomer.addEventHandler(e -> {
            String buttonID = ((Button) e.getSource()).getId();
            switch (buttonID) {
                case "add":
                    addElement();
                    break;
                case "buy":
                    buyOperation();
                    break;
                case "chat":
                    openChat();
                    break;
            }
        });
    }

    private void openChat() {
        try {
             ClientInitializer.getInstance();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void addElement() {
        if (viewCustomer.selectedProduct() != null) {
            if (viewCustomer.selectedProduct().getQuantity() > 0) {
                Product d = new Product(viewCustomer.selectedProduct().getName(), viewCustomer.selectedProduct().getPrice(), 1);
                order.add(d);
                modelShop.decreaseQuantity(viewCustomer.selectedProduct());
                viewCustomer.totalSum("total  " + order.getSum() + " €");
            } else {
                ErrorDialog.error("not available now");
            }
        }
        else{
            ErrorDialog.error("select product first");
        }
    }

    private void buyOperation() {
        LoginInitializer.getInstance(order);

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
                            byte buffer[] = ("TIME:").getBytes();

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
                                viewCustomer.updateTimeLabel(timeText);
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
}
