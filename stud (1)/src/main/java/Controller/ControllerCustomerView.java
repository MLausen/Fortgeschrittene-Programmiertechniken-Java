package Controller;

import ChatComponents.StartChatWindow;
import Helper.ErrorDialog;
import Model.ModelShop;
import Model.Order;
import Model.Product;
import TCPConnection.StartLoginWindow;
import View.ViewCustomer;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * Created Team 10
 */

public class ControllerCustomerView {
    private ModelShop modelShop;
    private ViewCustomer viewCustomer;
    private Order order;

    // thread
    private Thread timeRequestThread;

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
             StartChatWindow.getInstance();
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
            StartLoginWindow.getInstance(order);
    }

    // method to buyRequest a request to a server by a client via udp-package
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
}
