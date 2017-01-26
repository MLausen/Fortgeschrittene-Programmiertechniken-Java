package Model;

import fpt.com.Product;
import javafx.collections.ModifiableObservableListBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sufian Vaio on 20.01.2017.
 */
public class Order extends ModifiableObservableListBase<Product> implements fpt.com.Order {
    List<Product> ordersList;

    public Order() {
        ordersList = new ArrayList<Product>();
    }

    public boolean add(Product product) {
        if(product != null) {
            for (Product p : ordersList) {
                if (product.getName().equals(p.getName())) {
                    p.setQuantity(p.getQuantity() + product.getQuantity());
                    p.setPrice(Math.round((p.getPrice() + product.getPrice()) * 100) / (double) 100);
                    return false;
                }
            }
        }
        super.add(product);
        return true;
    }

    @Override
    public boolean delete(Product p) {
        return false;
    }

    public Product findProductByName(String name) {
        for (Product p : ordersList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public double getSum() {
        double sum = 0;
        for (Product p : ordersList) {
            sum += p.getPrice();
        }
        sum = Math.round(sum * 100) / (double) 100;
        return sum;
    }


    @Override
    public int getQuantity() {
        int count =0;
        for(Product p : ordersList){
            count += p.getQuantity();
        }
        return count;
    }

    @Override
    public Product get(int index) {
        return ordersList.get(index);
    }

    @Override
    public int size() {
        return ordersList.size();
    }

    @Override
    public Product findProductById(long id) {
        return null;
    }

    @Override
    protected void doAdd(int index, Product element) {
        ordersList.add(index, element);
    }

    @Override
    protected Product doSet(int index, Product element) {
        return ordersList.set(index, element);
    }

    @Override
    protected Product doRemove(int index) {
        return ordersList.remove(index);
    }
}
