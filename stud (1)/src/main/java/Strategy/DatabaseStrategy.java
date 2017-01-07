package Strategy;

import Database.OpenJPA;
import fpt.com.Product;
import fpt.com.SerializableStrategy;
import fpt.com.db.AbstractDatabaseStrategy;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sufian Vaio on 07.01.2017.
 */
public class DatabaseStrategy extends AbstractDatabaseStrategy {
    OpenJPA openJPA = OpenJPA.getInstance();
    int counter =0;
    private FileOutputStream fos;
    private FileInputStream fis;

    ArrayList<Model.Product> products;





    @Override
    public Product readObject() {
            if (counter < products.size()) return products.get(counter++);
        return null;
    }



    @Override
    public void writeObject(Product obj) throws IOException {
        open();
        if(obj.getId() == 0){
            openJPA.insert(obj.getName(),obj.getPrice(),obj.getQuantity());
        }
        openJPA.close();
    }

    @Override
    public void close() throws IOException {
        if(fis!=null) fis.close();
        if(fos!=null)  fos.close();
        if(!openJPA.isClosed())openJPA.close();
    }
    @Override
    public void open() throws IOException {
        if(openJPA.isClosed())
        openJPA.open();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (output != null) {
            open();
            this.fos = (FileOutputStream) output;
            products = new ArrayList<>();
        }
        if (input != null) {
            this.fis = (FileInputStream) input;
            products = openJPA.readList();
        }
    }
}
