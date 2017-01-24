package TCPConnection;

import Helper.ErrorDialog;
import Model.Order;
import View.ViewLogin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class Client {
    boolean login;
    Order order;

    public Client(Order order) {
        this.order = order;
    }


    public boolean buyRequest() throws ClassNotFoundException {
        ViewLogin viewLogin = new ViewLogin();
        if (!(viewLogin.getChoice() == 0)) { //stop operation if cancel or close
            return false;
        }

        try (Socket serverCon = new Socket("localhost", 6666);
             ObjectOutputStream out = new ObjectOutputStream(serverCon.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(serverCon.getInputStream())) {

            out.writeObject(viewLogin.getPass());
            out.writeObject(viewLogin.getName());
            out.flush();

            String loginFeedback = (String) in.readObject();

            if (loginFeedback.substring(0, 22).equals("Logged in successfully")) {
                login = true;
            } else {
                login = false;
            }

            System.out.println(loginFeedback);
            if (login) {
                out.writeObject(order);
                out.flush();

                String feedback = (String) in.readObject();
                System.out.println(feedback);
                serverCon.setSoTimeout(50000);
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }


}