package Strategy;

import Helper.ErrorDialog;
import fpt.com.*;
import fpt.com.Product;

import java.io.*;

/**
 * Created by Team 10
 */
public class BinaryStrategy implements SerializableStrategy {
    // ML made private
    private FileInputStream fi;
    private FileOutputStream fo;

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
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (input != null) {
            objectInputStream = new ObjectInputStream(input);
        }
        if (output != null) {
            objectOutputStream = new ObjectOutputStream(output);
        }
    }
}

   /* public void save(){
        try (FileOutputStream fo = new FileOutputStream("binSer.ser");
             ObjectOutputStream os = new ObjectOutputStream(fo)) {
            for(int i = 0 ; i < productList.size() ; i++) {
                os.writeObject(productList.get(i)); // write Object
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        getList().clear();
        try (FileInputStream fi = new FileInputStream("binSer.ser");
             ObjectInputStream is = new ObjectInputStream(fi)) {
            int i =0;
            while(is.readObject() != null){
                doAdd(i++,(Product) is.readObject()); // Read Object
            }
        }catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/