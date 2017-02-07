package TCPConnection;

import Model.Order;

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


    private int name;
    private Socket socket;


    private IncomingThread incoming;
    private OutcomingThread outcoming;

    public Connection(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void run() {
        //print the new connection information
        printConnection();

        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            while (socket.isConnected()) {
                Lock lock = new ReentrantLock();

                incoming = new IncomingThread(in);
                incoming.start();

                boolean login = false;
                Order order = null;

                while (incoming.isAlive()) {
                    login = incoming.login;
                    if (login) order = incoming.newOrder;
                }

                outcoming = new OutcomingThread(lock, out, login, order);
                outcoming.start();
            }
            closeSockets();
        } catch (IOException e) {
        e.printStackTrace();
        }

    }


    private void printConnection() {
        System.out.println("TCP Connection to " + name + " " + socket.getInetAddress() + "  " + socket.getLocalPort());
    }

    private void closeSockets() throws IOException {
        //close & print
        System.out.println("Connecting with " + name + " timed out");
        socket.close();
    }
}
