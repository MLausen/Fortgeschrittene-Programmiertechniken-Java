package TCPConnection;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 07.02.2017.
 */
public class Connect {
    Socket serverCon = null;
    static Connect instance = null;

    private Connect() {

        try {
            serverCon = new Socket("localhost", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connect getInstance(){
        if(instance == null){
            instance = new Connect();
        }
        return instance;
    }

    public Socket getSocket() {
        return serverCon;
    }

    public void closeConnection() throws IOException {
        if (!serverCon.isClosed()) serverCon.close();
    }
}
