package Model;

import Helper.IdGenerator;
import Helper.IdOverFlowException;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Team 10
 * Product class defines data structure of a Product available in store
 */
@Entity()
@Table(name = "products")
public class Product implements fpt.com.Product, Externalizable {
    private static final long serialVersionUID = 701L;

    // unique key
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "products_SEQ")
    private long idDB;

    public long getIdDB() {
        return this.idDB;
    }


    private SimpleLongProperty id = new SimpleLongProperty();
    // properties

    @Persistent
    @Strategy("fpt.com.db.StringPropertyValueHandler")
    private SimpleStringProperty name = new SimpleStringProperty();

    @Persistent
    @Strategy("fpt.com.db.DoublePropertyValueHandler")
    private SimpleDoubleProperty price = new SimpleDoubleProperty();

    @Persistent
    @Strategy("fpt.com.db.IntegerPropertyValueHandler")
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty();

    public Product() {
        this("undefined", 0, 0);
    }

    public Product(String name, double price, int quantity) {
        this.name.set(name);
        this.quantity.set(quantity);
        this.price.set(price);

        try {
            this.id.set(IdGenerator.generateID());
        } catch (IdOverFlowException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableValue<String> nameProperty() {
        return name;
    }

    public ObservableValue<Number> priceProperty() {
        return price;
    }

    public ObservableValue<Number> quantityProperty() {
        return quantity;
    }

    public ObservableValue<Number> idProperty() {
        return id;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(getId());
        out.writeObject(getQuantity());
        out.writeObject(getPrice());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        setId((Long) in.readObject());
        setQuantity((Integer) in.readObject());
        setPrice((double) in.readObject());
    }
}
