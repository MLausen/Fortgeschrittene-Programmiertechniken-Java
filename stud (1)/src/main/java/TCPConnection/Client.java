package TCPConnection;

import Helper.ErrorDialog;
import Model.Order;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Team 10
 */
public class Client extends Thread {
    public boolean login;
    String username;
    String password;
    public Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public Client() {

    }

   /* public void buyRequest(String username, String password) {
        //start to send by creating a new socket with the server address and port
        this.password = password;
        this.username = username;
    }*/

    @Override
    public void run() {
        try (Socket serverCon = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(serverCon.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(serverCon.getInputStream())) {

            IncomingClientThread inThread = new IncomingClientThread(serverCon, order);
            OutcomingClientThread outThread = new OutcomingClientThread(serverCon, order);

            inThread.start();
            outThread.start();

        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (SocketException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..");
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.error("IO Exc");
        }
    }
}
