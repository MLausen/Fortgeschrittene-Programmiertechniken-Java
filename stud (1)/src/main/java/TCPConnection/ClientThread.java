package TCPConnection;

import java.io.*;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 17.01.2017.
 */
public class ClientThread extends Thread {
    private int name;
    private Socket socket;

    public ClientThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }
    public void run() {
        System.out.printf("Login-Request from %s  Port %d%n", socket.getInetAddress(), socket.getLocalPort());
        System.out.println("Connection to " + name);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

                String username = in.readLine();
                String password = in.readLine();

                if (username.equals("admin") && password.equals("admin")) {
                    out.write(("Logged in successfully as " + username).getBytes());
                } else {
                    out.write("Invalid Username and/or password.".getBytes());
                }

                out.flush();

                System.out.println("Connecting with " + name + " timed out");
                socket.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}