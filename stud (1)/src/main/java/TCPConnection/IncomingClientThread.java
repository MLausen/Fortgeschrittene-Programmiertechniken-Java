package TCPConnection;

import fpt.com.Order;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Melli on 07.02.2017.
 */
public class IncomingClientThread extends Thread {
    private Socket socket;
    Order order;

    IncomingClientThread(Socket socket, fpt.com.Order order){
        this.socket = socket;
        this.order = order;
    }

    @Override
    public void run(){
        // closes afterwards
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            while (!socket.isClosed()) {
                //wait till the server responses
                String loginFeedback = null;
                try {
                    loginFeedback = (String) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (loginFeedback.substring(0, 10).equals("Your order is on the way to the Warehouse".substring(0, 10))) {
                    Client.getInstance().login = true;
                    ((Model.Order)order).setFinished(false);
                    ((Model.Order)order).clear();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}