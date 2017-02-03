package TCPConnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Team 10
 */
public class TimeClientThread implements Runnable {
    DatagramPacket packet;
    DatagramSocket socket;

    public TimeClientThread(DatagramPacket packet, DatagramSocket socket) {
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    public void run() {
        // read data
        InetAddress address = packet.getAddress();
        int port = packet.getPort();

        // data to string
        String da = new String(packet.getData());

        // dividing commands by :
        try (Scanner sc = new Scanner(da).useDelimiter(":")) {
            // filtering first command
            String keyword = sc.next();

            if (keyword.equals("TIME")) {
                System.out.printf(
                        "Time-Request from %s  Port %d%n",
                        address, port);
                DateFormat df = new SimpleDateFormat("dd.MM.yy   HH:mm:ss");
                Date dateobj = new Date();
                byte[] myDate = df.format(dateobj).getBytes();

                // package with new date to the same Port and address
                packet = new DatagramPacket(myDate, myDate.length,
                        address, port);
                // sending package
                socket.send(packet);

            } else {
                byte[] myDate = null;
                myDate = new String("Command unknown").getBytes();

                // package for invalid keyword
                // preparing for response to the same Port and address
                packet = new DatagramPacket(myDate, myDate.length,
                        address, port);
                // sending package
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
