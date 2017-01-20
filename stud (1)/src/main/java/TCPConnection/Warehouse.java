package TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 18.01.2017.
 */
public class Warehouse {
    public static void main(String[] args){
        try (ServerSocket server = new ServerSocket(6666);) {
            int connections = 0;

            while (true) {
                try {
                    Socket socket = server.accept();
                    connections++;
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

