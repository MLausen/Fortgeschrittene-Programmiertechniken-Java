package problem4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
/**
 * Created by Tean 10
 */
public class Acquisition implements Runnable {
    Lock lock;

    public Acquisition(Lock rLock){
        this.lock = rLock;
    }

    @Override
    public void run() {
        System.out.println("run() Acquisition");

        // get new customer while no of the cashpoints has a queue with 8 or more people
        while(CashpointService.getInstance().getHighestCustomerAmount() < 9) {
            // lock thread to not change cashpoint state while acquisition and assigning customer to cashpoint
            lock.lock();

            Customer customer = new Customer();
            CashpointService.getInstance().checkForCashpointToOpen();
            CashpointService.getInstance().getCashpointWithLowestCustomerAmount().addCustomer(customer);

            // unlock thread after assigning customer to cashpoint and checking for a new cashpoint to open
            lock.unlock();

            try {
                int sleepTime = 1000 * ThreadLocalRandom.current().nextInt(0, 3); // range 0, 1, 2
                System.out.println("Acquisition sleep: "  + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
            this.endAcquisition();
    }

    public void endAcquisition(){
        System.out.println("---Acquisition stopped. Enough customers.---");
    }
}
