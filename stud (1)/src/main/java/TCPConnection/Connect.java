package TCPConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Sufian Vaio on 07.02.2017.
 */
public class Connect {
    Socket serverCon = null;
    ObjectOutputStream out;
    ObjectInputStream in;

    static Connect instance = null;

    private Connect() {

        try {
            serverCon = new Socket("localhost", 6666);
           /* out = new ObjectOutputStream(serverCon.getOutputStream());
            in = new ObjectInputStream(serverCon.getInputStream());*/
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

    public ObjectOutputStream getOutStream() {
      /*  ObjectOutputStream temp = out;
        out.reset();*/
        return this.out;
    }

    public ObjectInputStream getInStream() {
        return this.in;
    }

    public void closeConnection() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        if (!serverCon.isClosed()) serverCon.close();
    }
}
