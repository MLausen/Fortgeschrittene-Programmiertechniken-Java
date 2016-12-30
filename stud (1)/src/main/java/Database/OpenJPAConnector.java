package Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.apache.openjpa.persistence.OpenJPAPersistence;

import Model.Product;

public class OpenJPAConnector {
    private static OpenJPAConnector instance;
    EntityManagerFactory factory;
    EntityManager manager;
    EntityTransaction transaction;

    public OpenJPAConnector(){
        factory = getWithoutConfig();
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
    }

    public static OpenJPAConnector getInstance(){
        if (OpenJPAConnector.instance == null){
            OpenJPAConnector.instance = new OpenJPAConnector();
        }
        return OpenJPAConnector.instance;
    }

    public long insert(String name, double price, int quantity) {
        Product product = new Product(name, price, quantity);
        product.setId(0);
        manager.persist(product);
        System.out.print("Insert erfolgt");
        return product.getId();
    }

    public Product read(long id) {
        //TODO read method
        /*Query q = manager.createQuery("SELECT c FROM Product c WHERE  c.id = "+id);
        List<Product> products = (List<Product>) q.getResultList();

        if (products.size() > 0)
            return products.get(0);

        this.close();*/

        return null;
    }


    public static EntityManagerFactory getWithoutConfig() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL",
                "jdbc:postgresql://java.is.uni-due.de:5432/producer");
        map.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
        map.put("openjpa.ConnectionUserName", "ws1011");
        map.put("openjpa.ConnectionPassword", "ftpw10");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        // find all classes to registrate them
        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Product.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            // <class>Product</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()
                    + ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
}
