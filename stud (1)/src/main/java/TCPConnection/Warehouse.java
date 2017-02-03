package TCPConnection;

import ChatComponents.ChatServer;
import Model.Order;

import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Team 10
 */
public class Warehouse {
    static Order order = new Order();
    private static Thread timeResponseThread;
    public static final String NAME = "Team10_RMIChatServer";

    public static void main(String[] args) throws RemoteException, MalformedURLException{
        // Chat
        LocateRegistry.createRegistry(1099); // registered
        Naming.rebind("//localhost:1099/" + Warehouse.NAME, new ChatServer());

        // Order (tcp)
        try (ServerSocket server = new ServerSocket(6666)) {
            int connections = 0;

            Warehouse.timeResponse();
            timeResponseThread.start();

            //always running
            while (true) {
                try {

                    //wait until some client call the address and has the same port
                    Socket socket = server.accept();
                    connections++;
                    //create a thread which response to that client
                    new ClientThread(connections, socket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    // method to get a timeresponse by a server via udp-package
    public static void timeResponse() {
        Warehouse.timeResponseThread = new Thread("Time Response") {
            public void run() {
                // server socket with port 6667
                try (DatagramSocket socket = new DatagramSocket(6667)) {
                    System.out.println(socket.getLocalPort());
                    while (true) {

                        // new package
                        DatagramPacket packet = new DatagramPacket(new byte[5], 5);

                        // waiting for package
                        try {
                            socket.receive(packet);
                            Thread timeClientThread = new Thread(new TimeClientThread(packet, socket));
                            timeClientThread.start();
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
