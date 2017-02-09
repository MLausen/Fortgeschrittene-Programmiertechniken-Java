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
    public static final String SERVER_NAME = "Team10_RMIChatServer";

    public static void main(String[] args) throws RemoteException, MalformedURLException{
        // Chat
        LocateRegistry.createRegistry(1099); // registered
        Naming.rebind("//localhost:1099/" + Warehouse.SERVER_NAME, new ChatServer());

        // Order (tcp)
        try (ServerSocket server = new ServerSocket(6666)) {
            int connections = 0;

            // date time request
            Warehouse.timeResponse();
            timeResponseThread.start();

            while (true) {
                try {
                    //wait until some client call the address and has the same port
                    Socket socket = server.accept();
                    connections++;
                    System.out.println("connections: " + connections);
                    //create a thread which response to that client
                    new Connection(connections, socket).start();
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
