package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Team 10
 */
// TODO change 600 to 6000, 100 to 1000
public class Cashpoint implements Runnable{
    private WaitingQueue queue = new WaitingQueue();

    private boolean isOpen;
    private int id;
    //private double sales;

    public Cashpoint(int id){
        this.id = id;
        isOpen = false;
        //sales = 0;
    }

    @Override
    public void run() {
        isOpen = true;

        // TODO replace: cashpoint needs customers
        try {
            Thread.sleep(600);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("run() Cashpoint " + id);

        while(queue.size() > 0) {
            removeCustomer();

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
        System.out.println("---Cashpoint " + id + " closed---");
    }

    private void removeCustomer(){
        System.out.println("cashpoint " + id + " processed customer " + queue.get(0).getId());
        queue.remove(0);
        System.out.println(getQueueSize() + " customers at cashpoint " + id);
    }

    public void addCustomer(Customer c){
        queue.add(c);
        Balance.getInstance().addValue(this.id, c.getBill());
        System.out.println(getQueueSize() + " customers at cashpoint " + id);
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

   /* public double getSales(){
        return this.sales;
    }

    public void setSales(double payed){
        this.sales += payed;
    }*/
}
