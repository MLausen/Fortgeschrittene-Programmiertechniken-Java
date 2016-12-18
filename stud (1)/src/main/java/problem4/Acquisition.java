package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
/**
 * Created by Tean 10
 */
// TODO change 100 to 1000
public class Acquisition implements Runnable {
    Lock lock;
    public Acquisition(Lock rLock){
        this.lock = rLock;
    }

    @Override
    public void run() {
        System.out.println("run() Acquisition");

        // TODO check if size correct while threading!!!!! --> synch
        while(CashpointService.getInstance().getHighestCustomerAmount() < 8) {
            lock.lock();
            Customer customer = new Customer();

            if(customer.isInterested()){
                CashpointService.getInstance().getCashpointWithLowestCustomerAmount().addCustomer(customer);
            }
            CashpointService.getInstance().checkForReopeningCashpoint();
            lock.unlock();

            try {
                int sleepTime = 100 * ThreadLocalRandom.current().nextInt(0, 3); // range 0, 1, 2
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
