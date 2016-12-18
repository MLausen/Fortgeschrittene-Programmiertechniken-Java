package problem4;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Team 10
 */
public class Customer {
    private String id;
    private double bill;

    public Customer(){
        this.id =  UUID.randomUUID().toString();
        this.bill = getRandomProce();
        System.out.println("New customer with ID \"" + id + "\" pays " + this.bill + "€.");
    }

    // acquistion not always successfull
    public boolean isInterested(){
        //int interest =  (int) Math.random();
        //int interest = ThreadLocalRandom.current().nextInt(0, 2);
        //System.out.println("isInterested: " + interest);

        return true;
        //return interest == 0 ? false : true;
    }

    public double getBill(){
        return this.bill;
    }

    public String getId(){
        return this.id;
    }

    private double getRandomProce(){
        Random r = new Random();

        double value = r.nextDouble() * 5000.0;
        value = (Math.round(value));
        value = value / 100;

        return value;
    }
}
