package problem4;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Team 10
 */
public class Customer {
    private String id;
    private double bill;

    public Customer(){
        this.id =  UUID.randomUUID().toString();
        this.bill = getRandomPrice();
        System.out.println("New customer with ID \"" + id + "\" will pay " + this.bill + "€.");
    }

    public double getBill(){
        return this.bill;
    }

    public String getId(){
        return this.id;
    }

    private double getRandomPrice(){
        Random r = new Random();

        double value = r.nextDouble() * 5000.0;
        value = (Math.round(value));
        value = value / 100;

        return value;
    }
}
