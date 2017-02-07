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
    private Socket serverCon;
    private static Client instance = null;

    public boolean login;
    public Order order;


    public void setOrder(Order order) {
        this.order = order;
    }

    private Client() {
        try {
            serverCon = new Socket("localhost", 6666);
            new IncomingClientThread(serverCon, order).start();
            new OutcomingClientThread(serverCon, order).start();
        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void sendCloseSignal() {
        if (!serverCon.isClosed()) try {
            serverCon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
