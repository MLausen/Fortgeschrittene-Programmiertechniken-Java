package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Tean 10
 */
public class Acquisition implements Runnable {

    public Acquisition(){}

    @Override
    public void run() {
        System.out.println("run() Acquisition");
        // TODO check if size correct while threading!!!!!
        while(WaitingQueue.getSizeOfQueue() < 10) {
            System.out.println("WaitingQueue.getSizeOfQueue(): " + WaitingQueue.getSizeOfQueue());
            Customer customer = new Customer();

            if(customer.isInterested()){
                WaitingQueue.queue.add(customer);
            }

            try {
                int sleepTime = 1000 * ThreadLocalRandom.current().nextInt(0, 3); // range 0, 1, 2
                System.out.println("Acquisition sleep: "  + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                ErrorDialog.error("Stop sleeping");
            }
        }
            this.endAcquisition();
    }

    public void endAcquisition(){
        System.out.println("Acquisition stopped. Enough customers");
    }
}
