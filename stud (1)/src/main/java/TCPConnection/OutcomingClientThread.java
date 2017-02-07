package TCPConnection;

import fpt.com.Order;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Melli on 07.02.2017.
 */
public class OutcomingClientThread extends Thread {
    private Socket socket;
    Order order;

    OutcomingClientThread(Socket socket, Order order) {
        this.socket = socket;
        this.order = order;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {
            while (!socket.isClosed()) {
                if (order != null) {
                    if (order.size() > 0 && ((Model.Order) order).getFinished()) {
                        //send what the client wrote in the Dialog then flush
                        out.writeObject("admin");
                        out.writeObject("admin");
                        out.writeObject(order);
                        out.flush();
                    }
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
