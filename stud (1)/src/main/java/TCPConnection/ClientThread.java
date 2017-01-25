package TCPConnection;

import Model.Order;
import Model.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class ClientThread extends Thread {
    private int name;
    private Socket socket;
    private boolean login;


    public ClientThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;

    }

    public void run() {

        //print the new connection information
        printConnection();
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            login(in, out);
            delay();
            if(login) receiveOrder(in, out);
            closeSockets();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void receiveOrder(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        //receive the order, copy all items to the warehouse order list then print order items
        Order newOrder = (Order) in.readObject();
            if(newOrder.size() > 0) {
                System.out.println("------------Order------------");
                for (int i = 0; i < newOrder.size(); i++) {
                    Warehouse.order.add(new Product(newOrder.get(i).getName(), newOrder.get(i).getPrice(), newOrder.get(i).getQuantity()));
                    System.out.printf("%-15s  %-5s %-1s%n", newOrder.get(i).getName(), newOrder.get(i).getQuantity(), newOrder.get(i).getPrice() + " €");
                }
                //print the updated order list in warehouse with total income and quantity
                System.out.println("==========all orders=========");
                for (int i = 0; i < Warehouse.order.size(); i++) {
                    System.out.printf("%-15s  %-5s %-1s%n", Warehouse.order.get(i).getName(), Warehouse.order.get(i).getQuantity(), Warehouse.order.get(i).getPrice() + " €");
                }
                System.out.println("============================");
                System.out.println("total sell  : " + Warehouse.order.getSum() + " €");
                System.out.println("total count : " + Warehouse.order.getQuantity());
                //send positive feedback
                out.writeObject("Your order is on the way to the Warehouse");
                out.flush();
            }else{
                //send negative feedback if the list were empty
                out.writeObject("sorry .. your order List is empty");
                out.flush();
            }

    }

    private void delay() {
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printConnection() {
        System.out.printf("Login-Request from %s  Port %d%n", socket.getInetAddress(), socket.getLocalPort());
        System.out.println("Connection to " + name);

    }


    private void login(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        //read what has the client sent
        String username = (String) in.readObject();
        String password = (String) in.readObject();

        //authorize admin admin and send feedback then flush
        if (username.equals("admin") && password.equals("admin")) {
            login = true;
            out.writeObject("Logged in successfully as " + username);
        } else {
            login = false;
            out.writeObject("Invalid Username and/or password.");
        }
        out.flush();
    }

    private void closeSockets() throws IOException {
        //close & print
        System.out.println("Connecting with " + name + " timed out");
        socket.close();
    }
}