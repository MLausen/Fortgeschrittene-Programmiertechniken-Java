package Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.openjpa.persistence.OpenJPAPersistence;

import Model.Product;

public class OpenJPAConnector {
    EntityManagerFactory factory;
    EntityManager manager;
    EntityTransaction transaction;

    public OpenJPAConnector(){
        factory = getWithoutConfig();
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
        transaction.begin();
        manager.persist(Product.class);
        transaction.commit();
        transaction.begin();
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
            // <class>Producer</class>
            map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()
                    + ")");
        }

        return OpenJPAPersistence.getEntityManagerFactory(map);

    }
}
