package Model;

import Helper.ErrorDialog;
import Services.ProductList;
import fpt.com.Product;
import fpt.com.SerializableStrategy;
import javafx.collections.ModifiableObservableListBase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Team 10
 */
public class ModelShop extends ModifiableObservableListBase<Product> {
    List<Product> delegate;

    public ModelShop() {
        delegate = ProductList.getInstance().getProductlist();
    }

    //get Product Array List from  Productlist
    public List<Product> getList() {
        return delegate;
    }

    @Override
    public Product get(int index) {
        return ProductList.getInstance().getProductlist().get(index);
    }

    @Override
    public int size() {
        return ProductList.getInstance().getProductlist().size();
    }

    @Override
    public void add(int index, Product product) {
        if (product != null) {
            for (Product p : delegate) {
                if (product.getName().equals(p.getName())) {
                    p.setQuantity(p.getQuantity() + product.getQuantity());
                    System.out.println("UPDATES Product named: " + product.getName());
                    return;
                }
            }
        }
        super.add(index, product);
    }

    @Override
    protected void doAdd(int index, Product element) {
        delegate.add(index, element);
    }

    @Override
    protected Product doSet(int index, Product element) {
        return delegate.set(index, element);
    }

    @Override
    protected Product doRemove(int index) {
        return delegate.remove(index);
    }




    // start serialization with opening outputstream for the selected strategy  and iterate the productlist to write every object
    public void serialization(SerializableStrategy strategy, String path) throws IOException {
        try {
            strategy.open(null, new FileOutputStream(path));
            for (int i = 0; i < this.getList().size(); i++) {
                strategy.writeObject(this.getList().get(i));
            }
        } catch (IOException io) {
            ErrorDialog.error("Unfortunately, the file could not be created.");
            io.printStackTrace();
        } finally {
            strategy.close();
        }
    }

    //deserialization by removing all the products from the productlist and then open inputstream and iterates the loaded file from the path
    public void deserialization(SerializableStrategy strategy, String path) throws IOException {
        this.clear();
        strategy.open(new FileInputStream(path), null);
        Product p;
        try {
            while ((p = strategy.readObject()) != null) {
                this.add(p);
            }
        } catch (IOException io) {
            ErrorDialog.error("Unfortunately, the requested file was not found.");
            io.printStackTrace();
        } finally {
            strategy.close();
        }
    }
}
