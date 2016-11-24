package Services;

import fpt.com.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Team 10
 * Singleton pattern - class to offer instance of available products once
 */
public class ProductList implements fpt.com.ProductList {
    List<Product> productlist = new ArrayList<Product>();
    private static ProductList instance;

    private ProductList() {
    }

    // gets the only instance of ProductList
    public static ProductList getInstance() {
        if (ProductList.instance == null) {
            ProductList.instance = new ProductList();
            initalCreationOfProducts();
        }
        return ProductList.instance;
    }

    @Override
    public boolean add(Product product) {
        boolean success = false;
        if (product != null) {
            productlist.add(product);
            System.out.println("ADDED Product named: " + product.getName());
            success = true;
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
        return getProductlist().size();
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
        return productlist;
    }

    // product factory that creates a set of default products in store
    private static void initalCreationOfProducts(){
        ProductList.getInstance().add(new Model.Product("Apfel", 0.67, 20));
        ProductList.getInstance().add(new Model.Product("Birne", 0.89, 15));
        ProductList.getInstance().add(new Model.Product("Pflaume", 0.45, 50));
        ProductList.getInstance().add(new Model.Product("Brot", 0.99, 10));
        ProductList.getInstance().add(new Model.Product("Aubergine", 1.99, 7));
        ProductList.getInstance().add(new Model.Product("Paprika", 0.79, 36));
        ProductList.getInstance().add(new Model.Product("Pfirsich", 0.23, 50));
        ProductList.getInstance().add(new Model.Product("Erdbeeren 500g", 2.99, 20));
        ProductList.getInstance().add(new Model.Product("Himbeeren 200g", 1.99, 20));
        ProductList.getInstance().add(new Model.Product("Joghurt", 0.99, 50));
        ProductList.getInstance().add(new Model.Product("Milch", 1.49, 100));
        ProductList.getInstance().add(new Model.Product("Orange", 0.45, 75));
    }

    @Override
    public Iterator<Product> iterator() {
        int cursor = 0;
        final int end = size();

        return new ListIterator<Product>() {
            @Override
            public boolean hasNext() {
                return cursor + 1 < end;
            }

            @Override
            public Product next() {
                if (cursor + 1 < end) {
                    return ProductList.getInstance().getProductlist().get(cursor + 1);
                }
                return null;
            }

            @Override
            public boolean hasPrevious() {
                if (cursor > 0)
                    return true;
                return false;
            }

            @Override
            public Product previous() {
                if (cursor > 0) {
                    return ProductList.getInstance().getProductlist().get(cursor - 1);
                }
                return null;
            }

            @Override
            public int nextIndex() {
                return (cursor + 1);
            }

            @Override
            public int previousIndex() {
                return (cursor - 1);
            }

            //TODO
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(Product product) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(Product product) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
