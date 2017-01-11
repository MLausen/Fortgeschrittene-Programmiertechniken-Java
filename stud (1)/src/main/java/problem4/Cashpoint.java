package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;

/**
 * Created by Team 10
 */
// TODO replace 600 with 6000, 100 with 1000
public class Cashpoint implements Runnable{
    private WaitingQueue queue = new WaitingQueue();

    private boolean isOpen;
    private int id;

    Lock lock;

    public Cashpoint(int id, Lock lock){
        this.id = id;
        isOpen = false;

        this.lock = lock;
    }

    @Override
    public void run() {
        isOpen = true;

        try {
            Thread.sleep(600);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("run Cashpoint " + id);

        // IndexoutofBounds
        while(queue.size() > 0) {
            // lock to add payed price to revenue and remove customer
            lock.lock();
            removeCustomer();
            lock.unlock();

            try {
                int sleepTime = 100 * ThreadLocalRandom.current().nextInt(6, 11); // range 6, 7, 8, 9, 10
                System.out.println("Cashpoint " + id + " sleep: "  + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                ErrorDialog.error("Stop sleeping");
            }
        }
        this.closeCashpoint();
    }

    // sets value of isOpen to <isOpen value=false>
    public void closeCashpoint(){
        isOpen = false;
        System.out.println("---Cashpoint " + id + " closed---");
    }

    // processes queue by removing first customer
    private void removeCustomer(){
        System.out.println("cashpoint " + id + " processed customer " + queue.get(0).getId());
        System.out.println("customer " + queue.get(0).getId() + " pays " + queue.get(0).getBill() + "€");
        Balance.getInstance().addValue(this.id, queue.get(0).getBill());
        queue.remove(0);
        System.out.println(getQueueSize() + " customers at cashpoint " + id);
    }

    public void addCustomer(Customer c){
        queue.add(c);
        System.out.println(getQueueSize() + " customers at cashpoint " + id);
    }

    public int getQueueSize(){
        return this.queue.size();
    }

    public boolean isOpen(){
        return this.isOpen;
    }

    public void setCashpointOpen(){
        System.out.println("Cashpoint " + id + " just opened.");
        this.isOpen = true;
    }

    public int getId(){
        return this.id;
    }
}
