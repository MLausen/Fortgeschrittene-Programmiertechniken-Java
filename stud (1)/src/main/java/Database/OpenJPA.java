package Database;

import fpt.com.Product;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenJPA {
    //TODO close EntityManager + EntityManagerFactory
    EntityManagerFactory factory;
    static EntityManager entityManager ;
    EntityTransaction transaction ;
    private static OpenJPA instance;
    public OpenJPA(){
         //factory = Persistence.createEntityManagerFactory("openjpa", System.getProperties());
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

    public static EntityManagerFactory getWithoutConfig() {

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
            map.put("openjpa.MetaDataFactory" , "jpa(Types=" + Model.Product.class.getName() + ")") ;
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }

    public static Product read(long id) {
        Model.Product p = new Model.Product();
        javax.persistence.Query query = entityManager.createQuery("SELECT c FROM Product c WHERE  c.id = "+id);
         List<Model.Product> products = (List<Model.Product>)query.getResultList();
            if(products.size() >0) {
                p.setName(products.get(0).getName());
                p.setId(products.get(0).getId());
                p.setQuantity(products.get(0).getQuantity());
                p.setPrice(products.get(0).getPrice());
                return p;
            }
        return   null;
    }

    public long insert(String name, double price, int quantity){

        Model.Product product = new Model.Product(name,price,quantity);

            entityManager.persist(product);
            return product.getId();


    }





    public void open(){
        transaction.begin();
    }
    public void close(){
        if(transaction.isActive())
            transaction.commit();
    }

    public ArrayList<Model.Product> readList() {

        ArrayList<Model.Product> productsList=new ArrayList<>();
        javax.persistence.Query query = entityManager.createQuery("SELECT p FROM Product p ORDER BY p.id DESC");
        query.setMaxResults(10);
        List<Model.Product> products = query.getResultList();
       for(int i = 9 ;i >=0;i--) {
           Model.Product p = new Model.Product();
            p.setName(products.get(i).getName());
            p.setId(products.get(i).getId());
            p.setQuantity(products.get(i).getQuantity());
            p.setPrice(products.get(i).getPrice());
            productsList.add(p);

        }
            return productsList;

    }


    public boolean isClosed() {
        return !transaction.isActive();
    }
}
