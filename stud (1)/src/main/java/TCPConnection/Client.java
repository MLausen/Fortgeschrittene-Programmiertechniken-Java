package TCPConnection;

import Helper.ErrorDialog;
import Model.Order;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Team 10
 */
public class Client {
    public boolean login;
    ObjectOutputStream out;
    ObjectInputStream in;

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order order;
    private static Client instance=null;
    private Client() {
        Socket serverCon = Connect.getInstance().getSocket();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = serverCon.getOutputStream();
            inputStream = serverCon.getInputStream();
            out = new ObjectOutputStream(outputStream);
            in = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static Client getInstance(){
    if(instance == null) instance = new Client();
    return instance;
}

    public boolean buyRequest(String username, String password) {

        //start to send by creating a new socket with the server address and port
        try  {
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
/*          if(in!=null)inputStream.close();
          if(out!=null)outputStream.close();*/

        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (IOException e) {
            e.printStackTrace();
            ErrorDialog.error("IO Exc");
        }
        return login;
    }
}
/*package TCPConnection;

import Model.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
    public boolean login;
    public Order order;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Client(Order order, Socket socket) {
        this.order = order;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buyRequest(String username, String password) {
        //buyRequest what the client wrote in the Dialog then flush
        try {
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
            if (this.in != null) this.in = null;
            if (this.out != null) this.out = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

}
*/