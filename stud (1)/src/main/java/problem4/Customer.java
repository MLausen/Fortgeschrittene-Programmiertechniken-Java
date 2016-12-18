package problem4;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Team 10
 */
public class Customer {
    private String id;// TODO?

    public Customer(){
        System.out.println("new customer created");
        this.id =  UUID.randomUUID().toString();
    }

    // acquistion not always successfull
    public boolean isInterested(){
        //int interest =  (int) Math.random();
        int interest = ThreadLocalRandom.current().nextInt(0, 2);
        System.out.println("isInterested: " + interest);

        return true;
        //return interest == 0 ? false : true;
    }
}
