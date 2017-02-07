package TCPConnection;

import Model.Order;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Team 10
 */
public class IncomingThread extends Thread{
    private ObjectInputStream in;

    public  Order newOrder;
    public boolean login;

    public IncomingThread(ObjectInputStream input){
        this.in = input;
        this.login = false;
    }

    public void run(){

        //read what has the client sent
        String username = null;
        String password = null;
        try {
            username = (String) in.readObject();
            password = (String) in.readObject();
            newOrder = (Order) in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //authorize admin admin and buyRequest feedback then flush
        if (username.equals(Connection.USERNAME) && password.equals(Connection.PASSWORT)) {
            login = true;
        }

    }
}
