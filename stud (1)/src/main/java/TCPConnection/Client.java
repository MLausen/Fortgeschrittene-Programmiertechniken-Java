package TCPConnection;

import Helper.ErrorDialog;
import Model.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Team 10
 */
public class Client {
    public boolean login;
    ObjectOutputStream out;
    ObjectInputStream in;
    public Order order;
    private static Client instance = null;

    public void setOrder(Order order) {
        this.order = order;
    }

    private Client() {
        try {
            Socket serverCon = Connect.getInstance().getSocket();
            out = new ObjectOutputStream(serverCon.getOutputStream());
            in = new ObjectInputStream(serverCon.getInputStream());
        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Client getInstance() {
        if (instance == null) instance = new Client();
        return instance;
    }
    public void sendCloseSignal(){
        try {
            out.writeObject("close");
            out.flush();
            if (in != null) in.close();
            if (out != null) out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buyRequest(String username, String password) {
        try {
            //send what the client wrote in the Dialog then flush
            out.writeObject("open");
            out.writeObject(username);
            out.writeObject(password);
            out.writeObject(order);
            out.flush();
            out.reset(); // otherwise the stream would give the same order every time

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


        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.error("IO Exc");
        }
        return login;
    }
}
