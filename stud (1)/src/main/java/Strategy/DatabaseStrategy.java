package Strategy;

import Database.OpenJPA;
import fpt.com.Product;
import fpt.com.db.AbstractDatabaseStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Team 10
 */

public class DatabaseStrategy extends AbstractDatabaseStrategy {
    OpenJPA openJPA = OpenJPA.getInstance();
    int counter = 0;
    ArrayList<Model.Product> products;

    @Override
    public Product readObject() {
        if (counter < products.size()) return products.get(counter++);
        return null;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        if (obj.getId() == 0) {
            openJPA.insert(obj.getName(), obj.getPrice(), obj.getQuantity());
        }
    }

    @Override
    public void open() throws IOException {
        openJPA.open();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        open();
        if (output != null) {
            products = new ArrayList<>();
        }
        if (input != null) {
            products = openJPA.readList();
        }
    }

    @Override
    public void close() throws IOException {
        openJPA.close();
    }
}
