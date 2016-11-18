package Services;
import fpt.com.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Team 1/2Hobbyte
 */
public class ProductList implements fpt.com.ProductList {
    // TODO, wenn name schon vorhanden, sollte einfach die Quantity hochgesetzt werden
    List<Product> productlist = new ArrayList<Product>();
    private static ProductList instance;

    private ProductList() {
    }

    public static ProductList getInstance() {
        if (ProductList.instance == null) {
            ProductList.instance = new ProductList();
        }
        return ProductList.instance;
    }

    @Override
    public boolean add(Product product) {
        boolean success = false;
        if (product != null) {
            for (Product p : productlist) {
                if (product.getName().equals(p.getName())) {
                    p.setQuantity(p.getQuantity() + product.getQuantity());
                    System.out.println("UPDATES Product named: " + product.getName());
                    success = true;
                }
            }
            if (!success) {
                productlist.add(product);
                System.out.println("ADDED Product named: " + product.getName());
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean delete(Product product) {
        boolean success = false;
        if (product != null) {
            for (Product p : productlist) {
                if (product.getName().equals(p.getName())) {
                    productlist.remove(p);
                    System.out.println("DELETED Product named: " + product.getName());
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public int size() {
        int result = -1;
        return result;
    }

    @Override
    public Product findProductById(long id) {
        for (Product p : productlist) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    @Override
    public Product findProductByName(String name) {
        for (Product p : productlist) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getProductlist() {
        return this.productlist;
    }

    //TODO
    @Override
    public Iterator<Product> iterator() {
        int cursor = 0; // TODO give index
        final int end = productlist.size();

        return new ListIterator<Product>() {
            @Override
            public boolean hasNext() {
                return cursor < end;
            }

            @Override
            public Product next() {
                throw new NotImplementedException();
            }

            @Override
            public boolean hasPrevious() {
                throw new NotImplementedException();
            }

            @Override
            public Product previous() {
                throw new NotImplementedException();
            }

            @Override
            public int nextIndex() {
                throw new NotImplementedException();
            }

            @Override
            public int previousIndex() {
                throw new NotImplementedException();
            }

            @Override
            public void remove() {
                throw new NotImplementedException();
            }

            @Override
            public void set(Product product) {
                throw new NotImplementedException();
            }

            @Override
            public void add(Product product) {
                throw new NotImplementedException();
            }
        };
    }
}
