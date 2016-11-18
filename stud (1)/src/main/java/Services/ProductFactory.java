package Services;


import Model.Product;

/**
 * Created by Team 1/2Hobbyte
 */
public class ProductFactory {
    // TODO singleton pattern?
    public static void createProducts(){
        // TODO instance of ModelShop
        ProductList.getInstance().add(new Product("Apfel", 0.67, 20));
        ProductList.getInstance().add(new Product("Birne", 0.89, 15));
        ProductList.getInstance().add(new Product("Pflaume", 0.45, 50));
        ProductList.getInstance().add(new Product("Brot", 0.99, 10));
        ProductList.getInstance().add(new Product("Aubergine", 1.99, 7));
        ProductList.getInstance().add(new Product("Paprika", 0.79, 36));
        ProductList.getInstance().add(new Product("Pfirsich", 0.23, 50));
        ProductList.getInstance().add(new Product("Erdbeeren 500g", 2.99, 20));
        ProductList.getInstance().add(new Product("Himbeeren 200g", 1.99, 20));
        ProductList.getInstance().add(new Product("Joghurt", 0.99, 50));
        ProductList.getInstance().add(new Product("Milch", 1.49, 100));
        ProductList.getInstance().add(new Product("Orange", 0.45, 75));
    }
}
