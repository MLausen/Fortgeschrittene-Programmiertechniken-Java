package TCPConnection;

import Helper.ErrorDialog;
import View.ViewLogin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class Client {
    boolean login;

    public Client() {

    }

    public boolean loginRequest() {
        // if we should keep the customer view running in parallel while making login authorization
        // then we have to make it as a queue thread and make a thread for the order which has to wait till the login thread ends.
        /*EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            }});*/
        //TODO ask in the exercise if it is okay to use I/o Streams with BufferdReader / DataOutputStream
        ViewLogin viewLogin = new ViewLogin();
        if (!(viewLogin.getChoice() == 0)) { //stop operation if cancel or close
            return false;
        }

        try (Socket serverCon = new Socket("localhost", 6666);
             DataOutputStream out = new DataOutputStream(serverCon.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(serverCon.getInputStream()))) {

            out.writeBytes(viewLogin.getName() + '\n');
            out.writeBytes(viewLogin.getPass() + '\n');

            out.flush();

            String loginFeedback = in.readLine();

            if (loginFeedback.substring(0, 22).equals("Logged in successfully")) {
                login = true;
            } else {
                login = false;
            }
            System.out.println(loginFeedback);

        } catch (ConnectException e) {
            e.printStackTrace();
            ErrorDialog.error("Sorry..Server is Down");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }

}