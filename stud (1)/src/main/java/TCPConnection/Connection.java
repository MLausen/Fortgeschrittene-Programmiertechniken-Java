package TCPConnection;

import Model.Order;
import Model.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Team 10
 */
public class Connection extends Thread {
    public static final String USERNAME = "admin";
    public static final String PASSWORT = "admin";

    Order newOrder;
    private int name;
    private Socket socket;
    private boolean login;

    private IncomingThread incoming;
    private OutcomingThread outcoming;

    public Connection(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void run() {
        //print the new connection information
        printConnection();
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Lock lock = new ReentrantLock();

            incoming = new IncomingThread(in);
            incoming.start();

            boolean login = false;
            Order order = null;

            while(incoming.isAlive()){
                System.out.println("is alive");
                login = incoming.login;
                if(login == true){
                    order = incoming.newOrder;
                }
            }

            outcoming = new OutcomingThread(lock, out, login, order);
            outcoming.start();

            closeSockets();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (!this.socket.isClosed()) {
                try {
                    closeSockets();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
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

    private void closeSockets() throws IOException {
        //close & print
        System.out.println("Connecting with " + name + " timed out");
        socket.close();
    }
}
