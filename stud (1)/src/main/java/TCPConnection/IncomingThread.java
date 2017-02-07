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
    public boolean signal;

    public IncomingThread(ObjectInputStream input){
        this.in = input;
        this.login = false;
        this.signal = false;
    }

    public void run(){

        //read what has the client sent
        String username = null;
        String password = null;
        try {
            if(in.readObject().equals("close")) {
                signal = true;
                sleep(10000);
                return;
            }else {
                username = (String) in.readObject();
                password = (String) in.readObject();
                newOrder = (Order) in.readObject();
                if (username.equals(Connection.USERNAME) && password.equals(Connection.PASSWORT)) login = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
