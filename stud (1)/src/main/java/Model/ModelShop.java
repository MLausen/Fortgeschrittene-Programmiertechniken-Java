package Model;

import Services.ProductList;
import fpt.com.Product;
import fpt.com.SerializableStrategy;
import javafx.collections.ModifiableObservableListBase;

import java.io.*;
import java.util.List;

/**
 * Created by Team 1/2Hobbyte
 */
public class ModelShop extends ModifiableObservableListBase<Product> {
    List<Product> delegate;

    public ModelShop(){
        delegate = ProductList.getInstance().getProductlist();
    }

    //get Product Array List from  Productlist
    public List<Product> getList() {
        return ProductList.getInstance().getProductlist();
    }

    @Override
    public Product get(int index) {
        return ProductList.getInstance().getProductlist().get(index);
    }

    @Override
    public int size() {
        return ProductList.getInstance().getProductlist().size();
    }

    // ML ???

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

    public void save(SerializableStrategy strategy, String path) throws IOException {
        try {
            strategy.open(null, new FileOutputStream(path));
            for (int i = 0; i < this.getList().size(); i++) {
                strategy.writeObject(this.getList().get(i));
            }
        }catch (Exception e){
            strategy.close();
        }
    }

    public void load(SerializableStrategy strategy, String path) throws IOException {
        this.clear();
        strategy.open(new FileInputStream(path), null);
        Product p;
        try {
            while ((p = strategy.readObject()) != null) {
                this.add(p);
            }
        }catch (EOFException e){
            strategy.close();
        }
    }
}
