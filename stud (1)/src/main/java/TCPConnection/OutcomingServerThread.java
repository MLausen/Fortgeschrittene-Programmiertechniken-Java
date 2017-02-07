package TCPConnection;

import Model.Order;
import Model.Product;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.Lock;

/**
 * Created by Team 10
 */
public class OutcomingServerThread extends Thread {
    private boolean login;

    private ObjectOutputStream out;
    private Order newOrder;

    protected  Lock lock;

    public OutcomingServerThread(Lock lock, ObjectOutputStream output, boolean login, Order order) {
        this.lock = lock;
        this.login = login;
        this.out = output;
        this.newOrder = order;
    }

    public void run() {
        String answer = "";
        if (login) {
            if (newOrder.size() > 0) {
                this.lock.lock();
                System.out.println("------------Order------------");
                for (int i = 0; i < newOrder.size(); i++) {
                    Warehouse.order.add(new Product(newOrder.get(i).getName(), newOrder.get(i).getPrice(), newOrder.get(i).getQuantity()));
                    answer += String.format("%-15s  %-5s %-1s%n", newOrder.get(i).getName(), newOrder.get(i).getQuantity(), newOrder.get(i).getPrice() + " €");
                }
                System.out.println(answer);
                //print the updated order list in warehouse with total income and quantity
                System.out.println("==========all orders=========");
                for (int i = 0; i < Warehouse.order.size(); i++) {
                    System.out.printf("%-15s  %-5s %-1s%n", Warehouse.order.get(i).getName(), Warehouse.order.get(i).getQuantity(), Warehouse.order.get(i).getPrice() + " €");
                }
                System.out.println("============================");
                System.out.println("total sell  : " + Warehouse.order.getSum() + " €");
                System.out.println("total count : " + Warehouse.order.getQuantity());
                //send positive feedback
                this.lock.unlock();

                try {
                    out.writeObject("Your order is on the way to the Warehouse" + '\n' + answer);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                //send negative feedback if the list were empty
                    out.writeObject("sorry .. your order List is empty");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                out.writeObject("Invalid Username and/or password.");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
