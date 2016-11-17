import fpt.com.*;
import fpt.com.Product;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Created by Sufian Vaio on 13.11.2016.
 */
public class Order extends ArrayDeque<Product> implements fpt.com.Order {

    @Override
    public boolean delete(Product p) {
        return this.remove(p);
    }

    @Override
    public Product findProductById(long id) {
        return null;
    }

    @Override
    public Product findProductByName(String name) {
        return null;
    }

    @Override
    public double getSum() {
        return 0;
    }

    @Override
    public int getQuantity() {
        return 0;
    }
}
