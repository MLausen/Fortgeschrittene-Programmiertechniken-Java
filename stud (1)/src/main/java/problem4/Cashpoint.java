package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Team 10
 */
// TODO change 500 to 5000, 100 to 1000
public class Cashpoint implements Runnable{
    private WaitingQueue queue = new WaitingQueue();

    private boolean isOpen;
    private int id;

    public Cashpoint(int id){
        this.id = id;
        isOpen = false;
    }

    @Override
    public void run() {
        isOpen = true;

        // TODO replace: cashpoint needs customers
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("run() Cashpoint " + id);

        while(queue.size() > 0) {
            queue.remove(0);

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

    public void closeCashpoint(){
        isOpen = false;
        System.out.println("Cashpoint closed!");
    }

    public int getQueueSize(){
        return this.queue.size();
    }

    public boolean isOpen(){
        return this.isOpen;
    }

    public int getId(){
        return this.id;
    }

    public void addCustomer(Customer c){
        queue.add(c);
    }
}
