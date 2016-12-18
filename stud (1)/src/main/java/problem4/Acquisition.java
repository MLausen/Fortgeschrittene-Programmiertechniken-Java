package problem4;

import Helper.ErrorDialog;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Tean 10
 */
// TODO change 100 to 1000
public class Acquisition implements Runnable {

    public Acquisition(){}

    @Override
    public void run() {
        System.out.println("run() Acquisition");

        // TODO check if size correct while threading!!!!! --> synch
        while(CashpointService.getInstance().getHighestCustomerAmount() < 8) {
            Customer customer = new Customer();

            if(customer.isInterested()){
                CashpointService.getInstance().getCashpointWithLowestCustomerAmount().addCustomer(customer);
            }
            CashpointService.getInstance().checkForReopeningCashpoint();

            try {
                int sleepTime = 100 * ThreadLocalRandom.current().nextInt(0, 3); // range 0, 1, 2
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
