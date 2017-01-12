package Database;

import fpt.com.Product;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Created by Team 10
* singleton pattern - DB connection/access with OpenJPA
* */
public class OpenJPA {
    private static OpenJPA instance;

    EntityManagerFactory factory;
    EntityManager entityManager;
    EntityTransaction transaction;

    public OpenJPA() {
        factory = getWithoutConfig();
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    public static OpenJPA getInstance() {
        if (OpenJPA.instance == null) {
            OpenJPA.instance = new OpenJPA();
        }
        return OpenJPA.instance;
    }

    // define persistance
    public EntityManagerFactory getWithoutConfig() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("openjpa.ConnectionURL",
                "jdbc:postgresql://java.is.uni-due.de/ws1011");
        map.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
        map.put("openjpa.ConnectionUserName", "ws1011");
        map.put("openjpa.ConnectionPassword", "ftpw10");
        map.put("openjpa.RuntimeUnenhancedClasses", "supported");
        map.put("openjpa.jdbc.SynchronizeMappings", "false");

        List<Class<?>> types = new ArrayList<Class<?>>();
        types.add(Model.Product.class);

        if (!types.isEmpty()) {
            StringBuffer buf = new StringBuffer();
            for (Class<?> c : types) {
                if (buf.length() > 0)
                    buf.append(";");
                buf.append(c.getName());
            }
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + Model.Product.class.getName() + ")");
        }
        return OpenJPAPersistence.getEntityManagerFactory(map);
    }

    public void open() {
        if (!factory.isOpen()) factory = getWithoutConfig();
        if (!entityManager.isOpen()) entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    public Product read(long id) {
        begin();

        Model.Product p = new Model.Product();
        javax.persistence.Query query = entityManager.createQuery("SELECT c FROM Product c WHERE  c.id = " + id);
        List<Model.Product> products = (List<Model.Product>) query.getResultList();
        if (products.size() > 0) {
            p.setName(products.get(0).getName());
            p.setId(products.get(0).getId());
            p.setQuantity(products.get(0).getQuantity());
            p.setPrice(products.get(0).getPrice());
            return p;
        }
        commit();
        return null;
    }

    public long insert(String name, double price, int quantity) {
        begin();

        Model.Product product = new Model.Product(name, price, quantity);
        entityManager.persist(product);

        commit();
        return product.getId();
    }

    public ArrayList<Model.Product> readList() {
        begin();
        ArrayList<Model.Product> productsList = new ArrayList<>();
        javax.persistence.Query query = entityManager.createQuery("SELECT p FROM Product p ORDER BY p.id DESC");
        query.setMaxResults(10);
        List<Model.Product> products = query.getResultList();
        for (int i = 9; i >= 0; i--) {
            Model.Product p = new Model.Product();
            p.setName(products.get(i).getName());
            p.setId(products.get(i).getId());
            p.setQuantity(products.get(i).getQuantity());
            p.setPrice(products.get(i).getPrice());
            productsList.add(p);

        }
        commit();
        return productsList;
    }

    public void begin() {
        if (!transaction.isActive()) transaction.begin();
    }

    public void commit() {
        if (transaction.isActive()) transaction.commit();
    }

    public void close() {
        if (entityManager.isOpen()) entityManager.close();
        if (factory.isOpen()) factory.close();
        commit();
    }
}
