package Model;

import fpt.com.*;
import fpt.com.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Team 10
 */
public class Order implements fpt.com.Order {
    // Product with quantity of order
    HashMap<fpt.com.Product, Integer> orderMap = new HashMap<fpt.com.Product, Integer>();

    @Override
    public boolean add(fpt.com.Product e) {
        if (e.getQuantity() > 0) { // if available
            int quantity = 1;
            for (fpt.com.Product p : orderMap.keySet()) {
                if (e.equals(p)) {
                    quantity = orderMap.get(p) + 1;
                }
            }

            // quantity of product x in order
            orderMap.put(e, quantity);

            // reduce availability of products in whole store
            e.setQuantity(e.getQuantity() - 1);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(fpt.com.Product e) {
        for (fpt.com.Product p : orderMap.keySet()) {
            if (e.getId() == p.getId()) {
                orderMap.remove(p);

                // add quantity of order to availability
                e.setQuantity(e.getQuantity() + orderMap.get(p));

                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return orderMap.size();
    }

    @Override
    public fpt.com.Product findProductById(long id) {
        for (fpt.com.Product p : orderMap.keySet()) {
            if (id == p.getId()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public fpt.com.Product findProductByName(String name) {
        for (fpt.com.Product p : orderMap.keySet()) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public double getSum() {
        double sum = 0;
        for (fpt.com.Product p : orderMap.keySet()) {
            for (int i = 0; i < orderMap.get(p); i++) {
                sum += p.getPrice();
            }
        }
        return sum;
    }

    @Override
    public int getQuantity() {
        int counter = 0;
        for (fpt.com.Product p : orderMap.keySet()) {
            counter += orderMap.get(p);
        }
        return counter;
    }

    @Override
    public Iterator<Product> iterator() {
        throw new NotImplementedException();
    }
}
