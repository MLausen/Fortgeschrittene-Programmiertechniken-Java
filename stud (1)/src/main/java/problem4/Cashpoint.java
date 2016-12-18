package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Team 10
 */
public class Cashpoint implements  Runnable{

    public Cashpoint(){  }

    @Override
    public void run() {
        // TODO replace: cashpoint needs customers
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("run() Cashpoint");

        while(WaitingQueue.getSizeOfQueue() > 0) {
            WaitingQueue.queue.remove(0);

            try {
                int sleepTime = 1000 * ThreadLocalRandom.current().nextInt(6, 11); // range 6, 7, 8, 9, 10
                System.out.println("Cashpoint sleep: "  + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                ErrorDialog.error("Stop sleeping");
            }
        }
        this.closeCashpoint();
    }

    public void closeCashpoint(){
        System.out.println("Cashpoint closed!");
    }
}
