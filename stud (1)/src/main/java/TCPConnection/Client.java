package TCPConnection;

import Helper.ErrorDialog;
import Model.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Team 10
 */
public class Client {
    public boolean login;
    Order order;

    public Client(Order order) {
        this.order = order;
    }

    public boolean buyRequest(String username, String password) {
        //start to send by creating a new socket with the server address and port
        try (Socket serverCon = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(serverCon.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(serverCon.getInputStream())) {

            //send what the client wrote in the Dialog then flush
            out.writeObject(password);
            out.writeObject(username);
            out.writeObject(order);
            out.flush();

            //wait till the server responses
            String loginFeedback = null;
            try {
                loginFeedback = (String) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(loginFeedback);
            if (loginFeedback.substring(0, 10).equals("Your order is on the way to the Warehouse".substring(0, 10))) {
                login = true;
                order.clear();
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (SocketException e) {
            //TODo
            e.printStackTrace();
            ErrorDialog.error("Sorry..");
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.error("Make sure you entered username and password correctly");
        }
        return login;
    }
}
