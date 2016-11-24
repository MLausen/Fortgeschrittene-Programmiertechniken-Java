package Strategy;

import Helper.ErrorDialog;
import fpt.com.*;
import fpt.com.Product;

import java.io.*;

/**
 * Created by Team 10
 */
public class BinaryStrategy implements SerializableStrategy {
    private FileInputStream fis;
    private FileOutputStream fos;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public BinaryStrategy() {
    }

    @Override
    public Product readObject() {
        Product readObject = null;
        try {
            readObject = (fpt.com.Product) objectInputStream.readObject(); // Read Object
        } catch (EOFException e) {

        } catch (ClassNotFoundException | IOException e) {
            ErrorDialog.error("Unfortunately, the requested file was not found.");
            e.printStackTrace();
        }
        return readObject;
    }

    @Override
    public void writeObject(fpt.com.Product obj) throws IOException{
        try {
            objectOutputStream.writeObject(obj); // write Object
            objectOutputStream.flush();
        } catch (IOException e) {
            ErrorDialog.error("Unfortunately, the file could not be created.");
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (objectOutputStream != null) objectOutputStream.close();
        if (objectInputStream != null) objectInputStream.close();
        if (fis != null) fis.close();
        if (fos != null) fos.close();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (input != null) {
            objectInputStream = new ObjectInputStream(input);
            this.fis = (FileInputStream) input;
        }
        if (output != null) {
            objectOutputStream = new ObjectOutputStream(output);
            this.fos = (FileOutputStream) output;
        }
    }
}
