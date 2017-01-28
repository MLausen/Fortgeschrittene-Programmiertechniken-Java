package TCPConnection;

import Model.Order;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Team 10
 */
public class Warehouse {
    static Order order = new Order();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(6666)) {
            int connections = 0;

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
}
